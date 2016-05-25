package Actions.Admin;

import ActionEntities.FullSubject;
import Entities.Class;
import Entities.Subject;
import Entities.Teacher;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by Артем on 22.05.2016.
 */
public class GetTeacherSubjectList extends ActionSupport {
    public List<FullSubject> subjectList;

    public int teacherID;
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            subjectList = scheduleService.GetSubjectListForTeacher(teacherID);
            return SUCCESS;

        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public void setSubjectList(List<FullSubject> clsList) {
        this.subjectList = subjectList;
    }

    public List<FullSubject> getSubjectList() {
        return subjectList;
    }


    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
