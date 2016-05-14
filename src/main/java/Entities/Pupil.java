package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class Pupil implements IEntity {
    private  int ID;
    private String name;
    private String surname;
    private int userID;
    private int classID;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID = value;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
}
