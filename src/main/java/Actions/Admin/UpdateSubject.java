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
    public Subject subject = new Subject();
    private IScheduleService scheduleService = ServiceFactory.getScheduleService();

    public String execute() throws Exception {
        try {
            if (subject.getName() != null) {
                scheduleService.UpdateSubject(subject);
                return SUCCESS;
            }
            return ERROR;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }


    public void setSubjectID(int subjectID) {
        subject.setID(subjectID);
    }
    public void setTeacherID(int teacherID) {
        subject.setTeacherID(teacherID);
    }

    public void setLessonCount(int lessonCount){subject.setLessonCount(lessonCount);}

    public void setClassID(int classID) {
        subject.setClassID(classID);
    }

    public void setName(String name) {
        subject.setName(name);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
