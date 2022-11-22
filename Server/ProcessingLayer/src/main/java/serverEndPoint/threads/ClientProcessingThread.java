package serverEndPoint.threads;

import commands.Command;
import commands.CommonCommand;
import dbInteration.DataManager;
import entities.User;
import entities.UserType;
import responses.Response;
import serverEndPoint.ConnectedClientInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//В этом потоке происходит взаимодействие с клиентом
public class ClientProcessingThread extends Thread {

    private final DataManager dataManager;

    private final ConnectedClientInfo clientInfo;

    private final ObjectOutputStream objectOutputStream;

    private final ObjectInputStream objectInputStream;

    public ClientProcessingThread(ConnectedClientInfo clientInfo, Connection dbConnection) throws IOException {
        this.clientInfo = clientInfo;
        var socket = clientInfo.getConnectionSocket();
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        dataManager = new DataManager(dbConnection);
    }

    private void sendObject(Serializable object) throws IOException {

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    private <T> T receiveObject() throws IOException, ClassNotFoundException {

        return (T) objectInputStream.readObject();
    }

    @Override
    public void run() {

        while (true) {
            try {
                switch (clientLobby()) {
                    case ADMIN -> {
                        adminProcessing();
                    }
                    case USER -> {
                        clientProcessing();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void interrupt() {
        try {
            clientInfo.getConnectionSocket().close();
        } catch (IOException e) { //Аналогично
            throw new RuntimeException(e);
        }
        super.interrupt();
    }

    public ConnectedClientInfo getClientInfo() {
        return clientInfo;
    }

    private UserType clientLobby() throws Exception {

        while (true) {

            CommonCommand command = receiveObject();
            switch (command) {
                case AUTHORIZE -> {

                    String login = receiveObject();
                    String password = receiveObject();
                    var user = dataManager.users.get(login, password);
                    clientInfo.setIdInDB(user.getId());
                    var userType = user.getUserType();
                    sendObject(userType);
                    if (userType == UserType.UNDEFINED) continue;
                    return userType;
                }
                case CHECK_IF_LOGIN_EXISTS -> {

                    String login = receiveObject();
                    var user = dataManager.users.get(login);
                    if (user.getId() == 0) {
                        sendObject(Response.NOT_FOUND);
                    } else {
                        sendObject(Response.SUCCESSFULLY);
                    }
                }
                case REGISTER -> {

                    String login = receiveObject();
                    String password = receiveObject();
                    try {
                        int id = dataManager.users.create(new User(0, login, password, UserType.USER));
                        clientInfo.setIdInDB(id);
                    } catch (Exception e) {
                        sendObject(Response.ERROR);
                        continue;
                    }
                    sendObject(Response.SUCCESSFULLY);
                    return UserType.USER;
                }
                default -> {
                    sendObject(Response.UNKNOWN_COMMAND);
                }
            }
        }
    }

    private void adminProcessing() throws IOException, ClassNotFoundException {

        while (true){
            Command command = receiveObject();
            switch (command) {
                case EXIT -> {
                    return;
                }
            }
        }
    }

    private void clientProcessing() throws IOException, ClassNotFoundException {

        while (true){
            Command command = receiveObject();
            switch (command) {
                case EXIT -> {
                    return;
                }
            }
        }
    }

}