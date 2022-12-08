package connectionModule;

import commands.Command;
import commands.CommonCommand;
import entities.*;
import responses.Response;

import java.io.*;
import java.net.Socket;
import java.util.List;
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

    private static <T> T receiveObject() throws Exception {

        return (T) objectInputStream.readObject();
    }

    public static UserType singUp(String login, String password) throws Exception {

        sendObject(CommonCommand.AUTHORIZE);
        sendObject(login);
        sendObject(password);
        return receiveObject();
    }

    public static Response registration(User user) throws Exception {

        sendObject(CommonCommand.REGISTER);
        sendObject(user);
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

    public static List<Course> getAllCourses() throws Exception {
        sendObject(Command.GET_ALL_COURSES);
        return receiveObject();
    }

    public static Response createCourse(Course course) throws Exception {
        sendObject(Command.CREATE_COURSE);
        sendObject(course);
        return receiveObject();
    }

    public static Response editCourse(Course course) throws Exception {
        sendObject(Command.EDIT_COURSE);
        sendObject(course);
        return receiveObject();
    }

    public static Response deleteCourse(int courseId) throws Exception {
        sendObject(Command.DELETE_COURSE);
        sendObject(courseId);
        return receiveObject();
    }

    public static List<User> getAllUsersWhoHasCourse(int courseId) throws Exception {
        sendObject(Command.GET_ALL_USERS_WHO_HAS_COURSE);
        sendObject(courseId);
        return receiveObject();
    }

    public static List<CourseTopic> getAllCourseTopics(int courseId) throws Exception {
        sendObject(Command.GET_ALL_COURSE_TOPICS);
        sendObject(courseId);
        return receiveObject();
    }

    public static List<User> getAllUsersWhoPassedTopic(int topicIdId) throws Exception {
        sendObject(Command.GET_USERS_WHO_PASSED_TOPIC);
        sendObject(topicIdId);
        return receiveObject();
    }

    public static Response createTopic(CourseTopic topic) throws Exception {
        sendObject(Command.CREATE_TOPIC);
        sendObject(topic);
        return receiveObject();
    }

    public static Response editTopic(CourseTopic topic) throws Exception {
        sendObject(Command.EDIT_TOPIC);
        sendObject(topic);
        return receiveObject();
    }

    public static Response deleteTopic(int topicId) throws Exception {
        sendObject(Command.DELETE_TOPIC);
        sendObject(topicId);
        return receiveObject();
    }

    public static Profile getUserProfile(int userId) throws Exception {
        sendObject(Command.GET_USER_PROFILE);
        sendObject(userId);
        return receiveObject();
    }

    public static List<User> getAllUsers() throws Exception {
        sendObject(Command.GET_ALL_USERS);
        return receiveObject();
    }

    public static Response createUserProfile(Profile profile) throws Exception {
        sendObject(Command.CREATE_USER_PROFILE);
        sendObject(profile);
        return receiveObject();
    }

    public static Response updateUserProfile(Profile profile) throws Exception {
        sendObject(Command.UPDATE_USER_PROFILE);
        sendObject(profile);
        return receiveObject();
    }

}