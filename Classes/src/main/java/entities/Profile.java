package entities;

import java.io.Serializable;

public class Profile implements Serializable {

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String country;
    private String email;
    private UserStatus userStatus;
    private User user;

    public Profile() {
        surname = "";
        name = "";
        patronymic = "";
        country="";
        email="";
        userStatus = UserStatus.NOT_BANNED;
        user = new User();
    }

    public Profile(int id, String surname, String name,
                   String patronymic, String country,
                   String email, UserStatus userStatus, User user) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.country = country;
        this.email = email;
        this.userStatus = userStatus;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (id != profile.id) return false;
        if (!surname.equals(profile.surname)) return false;
        if (!name.equals(profile.name)) return false;
        if (!patronymic.equals(profile.patronymic)) return false;
        if (!country.equals(profile.country)) return false;
        if (!email.equals(profile.email)) return false;
        if (userStatus != profile.userStatus) return false;
        return user.equals(profile.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + surname.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + userStatus.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
