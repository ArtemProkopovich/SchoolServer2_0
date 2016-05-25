package ActionEntities;

import Entities.Teacher;
import Entities.User;

/**
 * Created by Артем on 25.05.2016.
 */
public class TeacherUser {
    private int teacherID;
    private String name;
    private String surname;
    private String type = "";
    private int userID;
    private String login;
    private String password;

    public TeacherUser(Teacher teacher, User user) {
        teacherID = teacher.getID();
        name = teacher.getName();
        surname = teacher.getSurname();
        type = teacher.getType();
        login = user.getLogin();
        password = user.getPassword();
        userID = user.getID();
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
