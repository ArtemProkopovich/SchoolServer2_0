package Actions.Admin;

import Services.Interfacies.IAdminService;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 25.05.2016.
 */
public class DeleteScheduleLesson extends ActionSupport {
    public int id;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (id!=0) {
                scheduleService.RemoveLesson(id);
                return SUCCESS;
            }
            return ERROR;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setLessonID(int lessonID) {
        this.id = lessonID;
    }

    public int getLessonID(){
        return id;
    }
}
