package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class User implements IEntity {

    private int ID = 0;
    private String login;
    private String password;
    private String email = "";
    private Role role;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID = value;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
