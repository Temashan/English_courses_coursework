package dbInteration;

import dbInteration.repositories.Courses;
import dbInteration.repositories.CoursesTopics;
import dbInteration.repositories.Profiles;
import dbInteration.repositories.Users;
import entities.Course;

import java.sql.Connection;

public class DataManager {

    public final Users users;
    public final Courses courses;
    public final CoursesTopics coursesTopics;
    public final Profiles profiles;

    public DataManager(Connection connection){
        users = new Users(connection);
        courses = new Courses(connection);
        coursesTopics = new CoursesTopics(connection);
        profiles = new Profiles(connection);
    }


}
