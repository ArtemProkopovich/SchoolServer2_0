package Actions.Admin;

import Entities.Lesson;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 25.05.2016.
 */
public class UpdateScheduleLesson extends ActionSupport {
    public Lesson lesson = new Lesson();
    public int dayOfWeek;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (dayOfWeek >= 0 && dayOfWeek <= 6) {
                scheduleService.UpdateLesson(lesson, dayOfWeek);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setLessonID(int lessonID){lesson.setID(lessonID);}

    public void setSubjectID(int subjectID){lesson.setSubjectID(subjectID);}

    public void setNumber(int number){lesson.setScheduleNumber(number);}

    public void setAuditorium(int auditorium){lesson.setRoom(auditorium);}

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
