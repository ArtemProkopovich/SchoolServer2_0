package Actions.Teacher;

import ActionEntities.LessonJournal;
import ActionEntities.TeacherDayLesson;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IStudyService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 08.05.2016.
 */
public class GetLessonJournal extends ActionSupport {
    public int lessonID;
    public LessonJournal journal;
    public IStudyService studyService = ServiceFactory.getStudyService();

    public String execute() throws Exception {
        try {
            journal = studyService.GetLessonPupilMarksList(lessonID);
            return SUCCESS;
        } catch (ServiceException ex) {
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public LessonJournal getJournal() {
        return journal;
    }

    public void setJournal(LessonJournal journal) {
        this.journal = journal;
    }

    public IStudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(IStudyService studyService) {
        this.studyService = studyService;
    }

}
