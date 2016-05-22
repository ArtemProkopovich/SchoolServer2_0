package Actions.Admin;

import Entities.Subject;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class UpdateSubject extends ActionSupport {
    public Subject subject;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (subject.getID()!=0 && subject.getTeacherID()!=0 && subject.getClassID()!=0 && subject.getName() != null && subject.getClassID() != 0 && subject.getTeacherID() != 0) {
                subject = scheduleService.UpdateSubject(subject);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
