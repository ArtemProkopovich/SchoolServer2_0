package Actions;

import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Евгений on 30.04.2016.
 */
public class Test implements ServletRequestAware {
    HttpServletRequest request;
    public UserData user = new UserData();

    public String execute() throws Exception {
        String param = getServletRequest().getParameter("value");

        if (param.contains("vasya")) {
            user.setRole("pupil");
        } else if (param.contains("roman")) {
            user.setRole("teacher");
        } else if (param.contains("petrovich")) {
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