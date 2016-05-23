package Actions.Pupil;

import ActionEntities.PupilDayLesson;
import Entities.Pupil;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetDaySchedule extends ActionSupport {
    public int pupilID;
    public Date date;

    public List<PupilDayLesson> lessons;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            lessons = scheduleService.GetPupilDayLessons(pupilID, date);
            return SUCCESS;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public List<PupilDayLesson> getLessons() {
        return lessons;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }
}
