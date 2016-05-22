package Actions.Admin;

import Entities.Pupil;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetPupilList  extends ActionSupport {
    public List<Pupil> pupilList;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            pupilList = userService.GetPupilList();
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setPupilList(List<Pupil> pupilList) {
        this.pupilList = pupilList;
    }

    public List<Pupil> getPupilList() {
        return pupilList;
    }
}
