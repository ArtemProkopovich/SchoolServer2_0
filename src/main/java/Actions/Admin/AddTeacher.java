package Actions.Admin;

import Entities.Pupil;
import Entities.Role;
import Entities.Teacher;
import Entities.User;
import Services.Interfacies.IAdminService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class AddTeacher extends ActionSupport {
    public Teacher teacher = new Teacher();
    public User user = new User();
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            if (teacher.getName()!=null && teacher.getSurname()!=null) {
                user.setRole(Role.TEACHER);
                adminService.AddTeacher(teacher, user);
                return SUCCESS;
            }
            return ERROR;
        }catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setName(String name){teacher.setName(name);}

    public void setSurname(String surname){teacher.setSurname(surname);}

    public void setType(String type){teacher.setType(type);}

    public void setLogin(String login){user.setLogin(login);}

    public void setEmail(String email){user.setEmail(email);}

    public void setPassword(String password){user.setPassword(password);}

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
