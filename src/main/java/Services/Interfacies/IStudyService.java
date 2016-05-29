package Services.Interfacies;

import ActionEntities.ActionClass;
import ActionEntities.LessonJournal;
import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;
import Entities.Subject;
import Services.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by Артем on 04.05.2016.
 */
public interface IStudyService {

    LessonJournal GetLessonPupilMarksList(int lessonID) throws ServiceException;
    void GetPupilSubjectMarks(int subjectID, int pupilID) throws ServiceException;
    List<ActionClass> GetTeacherClasses(int teacherID) throws ServiceException;


    void UpdateLessonPupilsMarks(Map<Pupil, Mark> pupilMarkMap) throws ServiceException;
    void UpdatePupilSubjectMarks(Map<Lesson, Mark> lessonMarkMap) throws ServiceException;
    void UpdatePupilMark(int lessonID, int pupilID, int mark) throws ServiceException;

    void AddLessonHomework(Lesson lesson) throws ServiceException;
    void UpdateLessonHomework(Lesson lesson) throws ServiceException;
    void UpdateLessonHomework(int lessonID, String homework) throws ServiceException;

}
