package Actions.Admin;

import Services.Interfacies.IAdminService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class DeleteTeacher extends ActionSupport {
    public int id;
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            if (id!=0) {
                adminService.RemoveTeacher(id);
                return SUCCESS;
            }
            return ERROR;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setTeacherID(int teacherID) {
        this.id = teacherID;
    }

    public int getTeacherID(){
        return id;
    }
}
