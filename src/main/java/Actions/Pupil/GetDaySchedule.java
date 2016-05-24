package Actions.Pupil;

import ActionEntities.PupilDayLesson;
import Entities.Pupil;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetDaySchedule extends ActionSupport {
    public int pupilID;
    public String date;

    public List<PupilDayLesson> lessons;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = format.parse(this.date);
            lessons = scheduleService.GetPupilDayLessons(pupilID, date);
            return SUCCESS;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PupilDayLesson> getLessons() {
        return lessons;
    }

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }
}
