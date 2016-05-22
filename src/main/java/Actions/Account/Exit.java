package Actions.Account;

import Entities.User;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Артем on 21.05.2016.
 */
public class Exit extends ActionSupport implements SessionAware {
    private User user;
    private IUserService userService = ServiceFactory.getUserService();
    private Map session;
    private final String USER = "user";

    public String execute() throws Exception {
        try {
            return SUCCESS;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
