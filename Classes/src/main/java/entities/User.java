package entities;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String login;
    private String password;
    private UserType userType;

    public User() {
        login = "";
        password = "";
        userType = UserType.UNDEFINED;
    }

    public User(int id, String login, String password, UserType userType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return userType == user.userType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + userType.hashCode();
        return result;
    }
}
