package Actions.Account;

import ActionEntities.UserData;
import Entities.Role;
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
    public UserData userData = new UserData();
    private IUserService userService = ServiceFactory.getUserService();
    private Map session;
    private final String USER = "user";

    @Override
    public String execute() throws Exception {
        try {
            if (userData.getLogin()!=null && userData.getPassword()!=null && (userData = userService.Login(userData.getLogin(), userData.getPassword()))!=null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(USER, userData);
                setSession(map);
                return SUCCESS;
            }
            else {
                userData.setRole(Role.GUEST);
                return SUCCESS;
            }
        }
        catch (ServiceException ex) {
            return ERROR;
        }
        catch(Exception ex) {
            return ERROR;
        }
    }

    public String getLogin() {
        return userData.getLogin();
    }

    public void setLogin(String login) {
        userData.setLogin(login);
    }

    public String getPassword() {
        return userData.getPassword();
    }

    public void setPassword(String password) {
       userData.setPassword(password);
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData user) {
        this.userData = userData;
    }

    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
