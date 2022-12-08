package dbInteration.repositories;

import entities.Course;
import entities.Profile;
import entities.UserStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Profiles {

    private final Connection dbConnection;

    public Profiles(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public static Profile convertResultSetToSingleObj(ResultSet resultSet) throws SQLException {

        resultSet.beforeFirst();
        if (!resultSet.next()) return new Profile();
        return convertResultSetToObj(resultSet);
    }

    private static Profile convertResultSetToObj(ResultSet resultSet) throws SQLException {

        var obj = new Profile();
        obj.setId(resultSet.getInt("profiles.id"));
        obj.setSurname(resultSet.getString("profiles.surname"));
        obj.setName(resultSet.getString("profiles.name"));
        obj.setPatronymic(resultSet.getString("profiles.patronymic"));
        obj.setCountry(resultSet.getString("profiles.country"));
        obj.setEmail(resultSet.getString("profiles.email"));
        switch (resultSet.getInt("profiles.status")) {
            case 0 -> {
                obj.setUserStatus(UserStatus.NOT_BANNED);
            }
            case 1 -> {
                obj.setUserStatus(UserStatus.BANNED);
            }
        }
        obj.setUser(Users.convertResultSetToSingleObj(resultSet));
        return obj;
    }

    public static List<Profile> convertResultSetToList(ResultSet resultSet) throws SQLException {

        var list = new ArrayList<Profile>();
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

    public int create(Profile obj) throws SQLException {

        var insertStatement = dbConnection.prepareStatement(
                "INSERT INTO profiles (surname, name, patronymic, country, email, status, userId) " +
                        "values (?, ?, ?, ?, ?, ?, ?)");

        insertStatement.setString(1, obj.getSurname());
        insertStatement.setString(2, obj.getName());
        insertStatement.setString(3, obj.getPatronymic());
        insertStatement.setString(4, obj.getCountry());
        insertStatement.setString(5, obj.getEmail());
        insertStatement.setInt(6, obj.getUserStatus().ordinal());
        insertStatement.setInt(7, obj.getUser().getId());
        insertStatement.executeUpdate();
        return getMaxId();
    }

    public void update(Profile obj) throws SQLException {

        var updateStatement = dbConnection.prepareStatement(
                "UPDATE profiles SET surname=?, name=?, patronymic=?, country=?, email=?, status=?, userId=?  where id = ?");
        updateStatement.setString(1, obj.getSurname());
        updateStatement.setString(2, obj.getName());
        updateStatement.setString(3, obj.getPatronymic());
        updateStatement.setString(4, obj.getCountry());
        updateStatement.setString(5, obj.getEmail());
        updateStatement.setInt(6, obj.getUserStatus().ordinal());
        updateStatement.setInt(7, obj.getUser().getId());
        updateStatement.setInt(8, obj.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {

        var deleteStatement = dbConnection.prepareStatement(
                "DELETE from profiles  where id=?");
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public Profile getById(int id) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM profiles join users u on u.id = profiles.userId where id = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public Profile getByUserId(int userId) throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM profiles join users u on u.id = profiles.userId where userId = ?;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, userId);
        statement.executeQuery();
        return convertResultSetToSingleObj(statement.getResultSet());
    }

    public List<Profile> getAll() throws SQLException {

        var statement = dbConnection.prepareStatement(
                "SELECT * FROM profiles join users u on u.id = profiles.userId;",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.executeQuery();
        return convertResultSetToList(statement.getResultSet());
    }
}
