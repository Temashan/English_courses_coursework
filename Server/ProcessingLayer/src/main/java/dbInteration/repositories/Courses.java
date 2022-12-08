package dbInteration.repositories;

import entities.Course;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Courses {
    private final Connection dbConnection;

    public Courses(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Course convertResultSetToSingleObj(ResultSet resultSet) throws SQLException {

        resultSet.beforeFirst();
        if (!resultSet.next()) return new Course();
        return convertResultSetToObj(resultSet);
    }

    private Course convertResultSetToObj(ResultSet resultSet) throws SQLException {

        var obj = new Course();
        obj.setId(resultSet.getInt("courses.id"));
        obj.setName(resultSet.getString("courses.name"));
        obj.setDescription(resultSet.getString("courses.description"));
        obj.setRate(resultSet.getInt("courses.rate"));
        obj.setCost(resultSet.getFloat("courses.cost"));
        obj.setDataPath(resultSet.getString("courses.dataPath"));
        return obj;
    }

    public List<Course> convertResultSetToList(ResultSet resultSet) throws SQLException {

        var list = new ArrayList<Course>();
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

    public int create(Course obj) throws SQLException {

        var insertStatement = dbConnection.prepareStatement(
                "INSERT INTO courses (name, description, rate, cost, dataPath) " +
                        "values (?, ?, ?, ?, ?)");

        insertStatement.setString(1, obj.getName());
        insertStatement.setString(2, obj.getDescription());
        insertStatement.setInt(3, obj.getRate());
        insertStatement.setFloat(4, obj.getCost());
        insertStatement.setString(5, obj.getDataPath());
        insertStatement.executeUpdate();
        return getMaxId();
    }

    public void update(Course obj) throws SQLException {

        var updateStatement = dbConnection.prepareStatement(
                "UPDATE courses SET name=?, description=?, rate=?, cost=?, dataPath=?  where id = ?");
        updateStatement.setString(1, obj.getName());
        updateStatement.setString(2, obj.getDescription());
        updateStatement.setInt(3, obj.getRate());
        updateStatement.setFloat(4, obj.getCost());
        updateStatement.setString(5, obj.getDataPath());
        updateStatement.setInt(6, obj.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {

        var deleteStatement = dbConnection.prepareStatement(
                "DELETE from courses where id=?");
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public Course getById(int id) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses where id = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public List<Course> getAll() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

    public List<Course> getUserCourses(int userId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM courses join user_courses uc on courses.id = uc.courseId where userId = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, userId);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }

}