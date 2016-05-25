package Actions.Admin;

import ActionEntities.FullSubject;
import Entities.Subject;
import Entities.Class;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 22.05.2016.
 */
public class GetClassSubjectList extends ActionSupport {
    public List<FullSubject> subjectList;
    public int classID;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            subjectList = scheduleService.GetSubjectListForClass(classID);
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setSubjectList(List<FullSubject> clsList) {
        this.subjectList = subjectList;
    }

    public List<FullSubject> getSubjectList() {
        return subjectList;
    }

}
