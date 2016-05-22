package Actions;

import com.google.gson.Gson;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Евгений on 30.04.2016.
 */
public class Test implements ServletRequestAware {
    HttpServletRequest request;
    private UserData user;

    public String execute() throws Exception {
        String param = getServletRequest().getParameter("value");

        Gson gson = new Gson();
        UserJSON jsonuser = gson.fromJson(param, UserJSON.class);
        if (jsonuser.getLogin().equals("vasya")) {
            user.setRole("pupil");
        } else if (jsonuser.getLogin().equals("roman")) {
            user.setRole("teacher");
        } else if (jsonuser.getLogin().equals("petrovich")) {
            user.setRole("admin");
        } else {
            user.setRole("guest");
        }
        return "success";
    }

    public HttpServletRequest getServletRequest() {
        return this.request;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
class UserJSON {
    private String login;
    private String password;
    UserJSON() {

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
class UserData {
    private String role;

    UserData() {}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}