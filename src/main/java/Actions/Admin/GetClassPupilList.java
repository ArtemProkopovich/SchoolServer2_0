package Actions.Admin;

import ActionEntities.PupilUser;
import Services.Interfacies.IAdminService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 25.05.2016.
 */
public class GetClassPupilList extends ActionSupport {

    public int classID;
    public List<PupilUser> pupilList;
    private IAdminService adminService = ServiceFactory.getAdminService();

    public String execute() throws Exception {
        try {
            pupilList = adminService.GetPupilListByClassID(classID);
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setPupilList(List<PupilUser> pupilList) {
        this.pupilList = pupilList;
    }

    public List<PupilUser> getPupilList() {
        return pupilList;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getClassID(){
        return classID;
    }
}
