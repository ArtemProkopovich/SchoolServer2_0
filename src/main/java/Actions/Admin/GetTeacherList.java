package Actions.Admin;

import Entities.Class;
import Entities.Teacher;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetTeacherList  extends ActionSupport {
    public List<Teacher> teacherList;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            teacherList = userService.GetTeacherList();
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Teacher> getTeacherList(){
        return teacherList;
    }
}
