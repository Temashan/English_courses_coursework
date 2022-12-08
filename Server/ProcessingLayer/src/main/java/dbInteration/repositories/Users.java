package dbInteration.repositories;

import entities.User;
import entities.UserType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Users {

    private final Connection dbConnection;

    public Users(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public static User convertResultSetToSingleObj(ResultSet resultSet) throws SQLException {

        resultSet.beforeFirst();
        if (!resultSet.next()) return new User();
        return convertResultSetToObj(resultSet);
    }

    private static User convertResultSetToObj(ResultSet resultSet) throws SQLException {

        var obj = new User();
        if (!resultSet.isAfterLast()) {

            obj.setId(resultSet.getInt("id"));
            obj.setLogin(resultSet.getString("login"));
            obj.setPassword(resultSet.getString("password"));
            switch (resultSet.getInt("userType")) {
                case 1 -> obj.setUserType(UserType.ADMIN);
                case 2 -> obj.setUserType(UserType.USER);
                default -> obj.setUserType(UserType.UNDEFINED);
            }
        }
        return obj;
    }

    public static List<User> convertResultSetToList(ResultSet resultSet) throws SQLException {

        var list = new ArrayList<User>();
        resultSet.beforeFirst();
        while (resultSet.next()) {

            list.add(convertResultSetToObj(resultSet));
        }
        return list;
    }

    private int getMaxId() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT MAX(id) from users;");
        var resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int create(User obj) throws SQLException {

        var insertStatement = dbConnection.prepareStatement(
                "INSERT INTO users (login, password, userType) " +
                        "values (?, ?, ?)");

        insertStatement.setString(1, obj.getLogin());
        insertStatement.setString(2, obj.getPassword());
        insertStatement.setInt(3, obj.getUserType().ordinal());
        insertStatement.executeUpdate();
        return getMaxId();
    }

    public void update(User obj) throws SQLException {

        var updateStatement = dbConnection.prepareStatement(
                "UPDATE users SET login=?, password=?, userType=?  where id = ?");
        updateStatement.setString(1, obj.getLogin());
        updateStatement.setString(2, obj.getPassword());
        updateStatement.setInt(3, obj.getUserType().ordinal());
        updateStatement.setInt(4, obj.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {

        var deleteStatement = dbConnection.prepareStatement(
                "DELETE from users where id=?");
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public User getById(int id) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM users where id = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }
    public User get(String login, String password) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM users where login = ? AND password = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public User get(String login) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM users where login = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setString(1, login);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public List<User> getAll() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM users;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

    public List<User> getUsersPassedTopic(int topicId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM users join user_passed_topics upt on users.id = upt.userId where topicId = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, topicId);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

    public List<User> getUsersHasCourse(int courseId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses join user_courses uc on courses.id = uc.courseId" +
                        " join users u on u.id = uc.userId where courseId = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, courseId);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

}
