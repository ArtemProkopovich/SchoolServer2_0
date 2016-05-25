package Actions.Admin;

import ActionEntities.FullSubject;
import Entities.Class;
import Entities.Subject;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetSubjectList  extends ActionSupport {
    public List<FullSubject> subjectList;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            subjectList = scheduleService.GetSubjectList();
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setSubjectList(List<FullSubject> clsList) {
        this.subjectList = subjectList;
    }

    public List<FullSubject> getSubjectList(){
        return subjectList;
    }
}
