package Actions.Admin;

import Entities.Pupil;
import Entities.Role;
import Entities.User;
import Services.Interfacies.IAdminService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 25.05.2016.
 */
public class UpdatePupil extends ActionSupport {
    public Pupil pupil = new Pupil();
    public User user = new User();
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            if (pupil.getName() != null && pupil.getSurname() != null) {
                user.setRole(Role.PUPIL);
                adminService.UpdatePupil(pupil, user);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setPupilID(int pupilID) {
        pupil.setID(pupilID);
    }

    public void setName(String name){pupil.setName(name);}

    public void setSurname(String surname){pupil.setSurname(surname);}

    public void setClassID(int classID) {
        pupil.setClassID(classID);
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public void setLogin(String login) {
        user.setLogin(login);
    }

    public void setPassword(String password) {
        user.setPassword(password);
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }
}
