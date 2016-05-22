package Actions.Admin;

import Entities.Class;
import Entities.Pupil;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class DeleteClass extends ActionSupport {
    public int id;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            if (id!=0) {
                userService.RemoveClass(id);
                return SUCCESS;
            }
            return ERROR;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
