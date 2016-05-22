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
 * Created by Артем on 08.05.2016.
 */
public class Login extends ActionSupport implements SessionAware {
    public User user;
    private IUserService userService = ServiceFactory.getUserService();
    private Map session;
    private final String USER = "user";

    public String execute() throws Exception {
        try {
            if ((user = userService.Login(user.getLogin(), user.getPassword()))!=null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(USER, user);
                setSession(map);
                return SUCCESS;
            }
            else
                return ERROR;
        }
        catch (ServiceException ex) {
            return ERROR;
        }
        catch(Exception ex) {
            return ERROR;
        }
    }

    public String getLogin() {
        return user.getLogin();
    }

    public void setLogin(String login) {
        user.setLogin(login);
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password) {
       user.setPassword(password);
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
