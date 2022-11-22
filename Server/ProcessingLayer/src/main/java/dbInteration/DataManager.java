package dbInteration;

import dbInteration.repositories.Users;

import java.sql.Connection;

public class DataManager {

    public final Users users;

    public DataManager(Connection connection){
        users = new Users(connection);
    }


}
