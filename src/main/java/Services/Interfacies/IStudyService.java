package Services.Interfacies;

import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;
import Entities.Subject;
import Services.ServiceException;

import java.util.Map;

/**
 * Created by Артем on 04.05.2016.
 */
public interface IStudyService {

    void GetLessonPupilMarksList(Lesson lesson) throws ServiceException;
    void GetPupilSubjectMarks(Subject subject, Pupil pupil) throws ServiceException;

    void UpdateLessonPupilsMarks(Map<Pupil, Mark> pupilMarkMap) throws ServiceException;
    void UpdatePupilSubjectMarks(Map<Lesson, Mark> lessonMarkMap) throws ServiceException;

    void AddLessonHomework(Lesson lesson) throws ServiceException;
    void UpdateLessonHomework(Lesson lesson) throws ServiceException;

}
