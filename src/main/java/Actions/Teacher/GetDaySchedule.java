package Actions.Teacher;

import ActionEntities.PupilDayLesson;
import ActionEntities.TeacherDayLesson;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetDaySchedule extends ActionSupport {
    public int teacherID;
    public Date date;

    public List<TeacherDayLesson> lessons;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            lessons = scheduleService.GetTeacherDayLessons(teacherID, date);
            return SUCCESS;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public List<TeacherDayLesson> getLessons() {
        return lessons;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

}
