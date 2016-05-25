package ActionEntities;

import Entities.Pupil;
import Entities.Teacher;
import Entities.User;

/**
 * Created by Артем on 25.05.2016.
 */
public class PupilUser {
    private int pupilID;
    private String name;
    private String surname;
    private int userID;
    private String login;
    private String password;


    public PupilUser(Pupil pupil, User user) {
        pupilID = pupil.getID();
        name = pupil.getName();
        surname = pupil.getSurname();
        login = user.getLogin();
        password = user.getPassword();
        userID = user.getID();
    }

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
}
