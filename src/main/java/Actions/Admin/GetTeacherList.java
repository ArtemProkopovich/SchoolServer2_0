package Actions.Admin;

import ActionEntities.TeacherUser;
import Entities.Class;
import Entities.Teacher;
import Services.Interfacies.IAdminService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetTeacherList  extends ActionSupport {
    public List<TeacherUser> teacherList;
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            teacherList = adminService.GetTeacherList();
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setTeacherList(List<TeacherUser> teacherList) {
        this.teacherList = teacherList;
    }

    public List<TeacherUser> getTeacherList(){
        return teacherList;
    }
}
