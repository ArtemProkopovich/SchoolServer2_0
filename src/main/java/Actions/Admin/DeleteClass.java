package Actions.Admin;

import Entities.Class;
import Entities.Pupil;
import Services.Interfacies.IAdminService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class DeleteClass extends ActionSupport {
    public int id;
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            if (id!=0) {
                adminService.RemoveClass(id);
                return SUCCESS;
            }
            return ERROR;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setClassID(int classID) {
        this.id = classID;
    }

    public int getClassID(){
        return id;
    }
}
