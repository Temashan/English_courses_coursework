package connectionModule;

import commands.Command;
import commands.CommonCommand;
import entities.UserType;
import responses.Response;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ConnectionModule {

    private static Socket connectionSocket;
    private static final String serverIp;
    private static final int serverPort;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    private static Properties getPropertiesFromConfig() throws IOException {

        var properties = new Properties();
        String propFileName = "Client/ConnectionModule/src/main/resources/config.properties";
        var inputStream = new FileInputStream(propFileName);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        properties.load(inputStream);
        return properties;
    }

    static {
        try {
            var properties = getPropertiesFromConfig();
            serverIp = properties.getProperty("serverIp");
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean connectToServer() throws IOException {

        connectionSocket = new Socket(serverIp, serverPort);
        //connectionSocket.setSoTimeout(3000);
        if (!connectionSocket.isConnected()) return false;
        objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        return true;
    }

    private static void sendObject(Serializable object) throws IOException {

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    private static  <T> T receiveObject() throws Exception {

        return (T) objectInputStream.readObject();
    }

    public static UserType singUp(String login, String password) throws Exception {

        sendObject(CommonCommand.AUTHORIZE);
        sendObject(login);
        sendObject(password);
        return receiveObject();
    }

    public static Response registration(String login, String password, String fullName, String organization) throws Exception {

        sendObject(CommonCommand.REGISTER);
        sendObject(login);
        sendObject(password);
        return receiveObject();
    }

    //ТОЛЬКО ПРИ РЕГИСТРАЦИИ
    public static boolean checkIfLoginExists(String login) throws Exception {

        sendObject(CommonCommand.CHECK_IF_LOGIN_EXISTS);
        sendObject(login);
        Response response = receiveObject();
        return response == Response.SUCCESSFULLY;
    }

    public static void exit() throws IOException {
        sendObject(Command.EXIT);
    }

}