package Actions.Admin;

import Entities.Pupil;
import Entities.Teacher;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class AddTeacher extends ActionSupport {
    public Teacher teacher;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            if (teacher.getName()!=null && teacher.getSurname()!=null) {
                teacher = userService.AddTeacher(teacher);
                return SUCCESS;
            }
            return ERROR;
        }catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
