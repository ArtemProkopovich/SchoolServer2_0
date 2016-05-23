package Actions.Teacher;

import Services.Interfacies.IStudyService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 23.05.2016.
 */
public class UpdateMark extends ActionSupport {
    public int lessonID;
    public int pupilID;
    public int mark;
    public IStudyService studyService = ServiceFactory.getStudyService();

    public String execute() throws Exception {
        try {
            studyService.UpdatePupilMark(lessonID, pupilID, mark);
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

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
