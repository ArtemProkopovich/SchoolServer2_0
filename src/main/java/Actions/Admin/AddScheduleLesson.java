package Actions.Admin;

import Entities.Lesson;
import Entities.Teacher;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class AddScheduleLesson extends ActionSupport {
    public Lesson lesson = new Lesson();
    public int day;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (day >= 0 && day <= 6) {
                scheduleService.AddLesson(lesson, day);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setSubjectID(int subjectID){lesson.setSubjectID(subjectID);}

    public void setNumber(int number){lesson.setSubjectID(number);}

    public void setAuditorium(int auditorium){lesson.setSubjectID(auditorium);}

    public int getDayOfWeek() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
