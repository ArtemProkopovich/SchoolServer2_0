package Actions.Admin;

import Entities.Class;
import Entities.Pupil;
import Entities.Role;
import Entities.User;
import Services.Interfacies.IAdminService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class AddPupil extends ActionSupport {
    public Pupil pupil = new Pupil();
    public User user = new User();
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            if (pupil.getName()!=null && pupil.getSurname()!=null) {
                user.setRole(Role.PUPIL);
                adminService.AddPupil(pupil, user);
                return SUCCESS;
            }
            return ERROR;
        }catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setFirstname(String firstname){pupil.setName(firstname);}

    public void setLastname(String lastname){pupil.setSurname(lastname);}

    public void setClassID(int classID){pupil.setClassID(classID);}

    public void setEmail(String email){user.setEmail(email);}

    public void setLogin(String login){user.setLogin(login);}

    public void setPassword(String password){user.setPassword(password);}

    public Pupil getPupil() {
        return pupil;
    }
    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }
}
