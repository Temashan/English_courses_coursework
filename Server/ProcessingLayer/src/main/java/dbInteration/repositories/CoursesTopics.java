package dbInteration.repositories;

import entities.Course;
import entities.CourseTopic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesTopics {

    private final Connection dbConnection;

    public CoursesTopics(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public CourseTopic convertResultSetToSingleObj(ResultSet resultSet) throws SQLException {

        resultSet.beforeFirst();
        if (!resultSet.next()) return new CourseTopic();
        return convertResultSetToObj(resultSet);
    }

    private CourseTopic convertResultSetToObj(ResultSet resultSet) throws SQLException {

        var obj = new CourseTopic();
        obj.setId(resultSet.getInt("courses_topics.id"));
        obj.setName(resultSet.getString("courses_topics.name"));
        obj.setOrdinalNumber(resultSet.getInt("courses_topics.ordinalNumber"));
        return obj;
    }

    public List<CourseTopic> convertResultSetToList(ResultSet resultSet) throws SQLException {

        var list = new ArrayList<CourseTopic>();
        resultSet.beforeFirst();
        while (resultSet.next()) {

            list.add(convertResultSetToObj(resultSet));
        }
        return list;
    }

    private int getMaxId() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT MAX(id) from courses;");
        var resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int create(CourseTopic obj) throws SQLException {

        var insertStatement = dbConnection.prepareStatement(
                "INSERT INTO courses_topics (courseId, ordinalNumber, name) " +
                        "values (?, ?, ?)");

        insertStatement.setInt(1, obj.getCourse().getId());
        insertStatement.setInt(2, obj.getOrdinalNumber());
        insertStatement.setString(3, obj.getName());
        insertStatement.executeUpdate();
        return getMaxId();
    }

    public void update(CourseTopic obj) throws SQLException {

        var updateStatement = dbConnection.prepareStatement(
                "UPDATE courses_topics SET name=?, courseId=?, ordinalNumber=?  where id = ?");
        updateStatement.setString(1, obj.getName());
        updateStatement.setInt(2, obj.getCourse().getId());
        updateStatement.setInt(3, obj.getOrdinalNumber());
        updateStatement.setInt(4, obj.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {

        var deleteStatement = dbConnection.prepareStatement(
                "DELETE from courses_topics where id=?");
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public CourseTopic getById(int id) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses_topics" +
                        " inner join courses c on courses_topics.courseId = c.id" +
                        " where courses_topics.id = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public List<CourseTopic> getAll() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses_topics inner join courses c on courses_topics.courseId = c.id;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

    public List<CourseTopic> getCourseTopics(int courseId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses_topics where courseId = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, courseId);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

    public List<CourseTopic> getUserPassedTopics(int userId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM user_passed_topics " +
                        "join users on users.id = user_passed_topics.userId " +
                        "join courses_topics on courses_topics.id = user_passed_topics.topicId " +
                        "where  users.id = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, userId);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

}
