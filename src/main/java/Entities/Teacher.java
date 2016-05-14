package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class Teacher implements IEntity {
    private int ID;
    private String name;
    private String surname;
    private int userID;
    private String type="";

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
