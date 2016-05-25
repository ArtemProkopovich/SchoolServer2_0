package Actions.Admin;

import ActionEntities.ScheduleClassLesson;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 25.05.2016.
 */
public class GetClassDaySchedule extends ActionSupport {
    public int classID;
    public int day;

    public List<ScheduleClassLesson> lessonList;

    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (day >= 0 && day <= 6) {
                lessonList = scheduleService.GetClassDayLessons(classID, day);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public List<ScheduleClassLesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<ScheduleClassLesson> lessonList) {
        this.lessonList = lessonList;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
