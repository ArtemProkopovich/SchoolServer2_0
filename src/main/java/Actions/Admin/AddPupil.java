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
public class AddPupil extends ActionSupport {
    public Pupil pupil;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            if (pupil.getName()!=null && pupil.getSurname()!=null && pupil.getClassID()!=0) {
                pupil = userService.AddPupil(pupil);
                return SUCCESS;
            }
            return ERROR;
        }catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }
    public Pupil getPupil() {
        return pupil;
    }
    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }
}
