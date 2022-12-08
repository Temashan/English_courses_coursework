package serverEndPoint.threads;

import commands.Command;
import commands.CommonCommand;
import dbInteration.DataManager;
import entities.*;
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
                    case USER, ADMIN -> {
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

    private void clientProcessing() throws IOException, ClassNotFoundException {

        while (true) {
            Command command = receiveObject();
            switch (command) {
                case EXIT -> {
                    return;
                }
                case GET_ALL_COURSES -> {
                    try {
                        var courses = dataManager.courses.getAll();
                        sendObject(new ArrayList<>(courses));
                    } catch (SQLException e) {
                        sendObject(new ArrayList<>());
                    }
                }
                case CREATE_COURSE -> {
                    Course course = receiveObject();
                    try {
                        dataManager.courses.create(course);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case EDIT_COURSE -> {
                    Course course = receiveObject();
                    try {
                        dataManager.courses.update(course);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case DELETE_COURSE -> {
                    int courseId = receiveObject();
                    try {
                        dataManager.courses.delete(courseId);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case GET_ALL_USERS_WHO_HAS_COURSE -> {
                    int courseId = receiveObject();
                    try {
                        var users = dataManager.users.getUsersHasCourse(courseId);
                        sendObject(new ArrayList<>(users));
                    } catch (SQLException e) {
                        sendObject(new ArrayList<>());
                    }
                }
                case GET_ALL_COURSE_TOPICS -> {
                    int courseId = receiveObject();
                    try {
                        var topics = dataManager.coursesTopics.getCourseTopics(courseId);
                        sendObject(new ArrayList<>(topics));
                    } catch (SQLException e) {
                        sendObject(new ArrayList<>());
                    }
                }
                case GET_USERS_WHO_PASSED_TOPIC -> {
                    int topicId = receiveObject();
                    try {
                        var users = dataManager.users.getUsersPassedTopic(topicId);
                        sendObject(new ArrayList<>(users));
                    } catch (SQLException e) {
                        sendObject(new ArrayList<>());
                    }
                }
                case CREATE_TOPIC -> {

                    CourseTopic topic = receiveObject();
                    try {
                        dataManager.coursesTopics.create(topic);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case EDIT_TOPIC -> {
                    CourseTopic topic = receiveObject();
                    try {
                        dataManager.coursesTopics.update(topic);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case DELETE_TOPIC -> {
                    int topicId = receiveObject();
                    try {
                        dataManager.coursesTopics.delete(topicId);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case GET_USER_PROFILE -> {
                    int userId = receiveObject();
                    try {
                        dataManager.profiles.getByUserId(userId);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case GET_ALL_USERS -> {
                    try {
                        var users = dataManager.users.getAll();
                        sendObject(new ArrayList<>(users));
                    } catch (SQLException e) {
                        sendObject(new ArrayList<>());
                    }
                }
                case UPDATE_USER_PROFILE -> {
                    Profile profile = receiveObject();
                    try {
                        dataManager.profiles.update(profile);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
                case CREATE_USER_PROFILE -> {
                    Profile profile = receiveObject();
                    try {
                        dataManager.profiles.create(profile);
                        sendObject(Response.SUCCESSFULLY);
                    } catch (SQLException e) {
                        sendObject(Response.ERROR);
                    }
                }
            }
        }
    }

}