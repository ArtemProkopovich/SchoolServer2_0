package Actions.Admin;

import Entities.Class;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetClassList extends ActionSupport {
    public List<Class> clsList;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
                clsList = userService.GetClassList();
                return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setClsList(List<Class> clsList) {
        this.clsList = clsList;
    }

    public List<Class> getClsList(){
        return clsList;
    }
}
