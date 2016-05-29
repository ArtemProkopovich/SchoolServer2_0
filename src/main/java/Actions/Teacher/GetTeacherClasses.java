package Actions.Teacher;

import ActionEntities.ActionClass;
import ActionEntities.TeacherDayLesson;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IStudyService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 29.05.2016.
 */
public class GetTeacherClasses  extends ActionSupport {
    public int teacherID;

    public List<ActionClass> classList;
    private IStudyService studyService = ServiceFactory.getStudyService();

    public String execute() throws Exception {
        try {
            classList = studyService.GetTeacherClasses(teacherID);
            return SUCCESS;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public List<ActionClass> getClassList() {
        return classList;
    }


    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}

