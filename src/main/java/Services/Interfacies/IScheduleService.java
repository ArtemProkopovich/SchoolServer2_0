package Services.Interfacies;

import Entities.Class;
import Entities.*;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;
import Services.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 02.05.2016.
 */
public interface IScheduleService {

    Subject AddSubject(Subject subject) throws ServiceException;
    Subject UpdateSubject(Subject subject) throws ServiceException;
    void RemoveSubject(int id) throws ServiceException;
    List<Subject> GetSubjectList() throws ServiceException;
    List<Subject> GetSubjectListForClass(int classID) throws ServiceException;
    List<Subject> GetSubjectListForTeacher(int teacherID) throws ServiceException;

    void CreateScheduleForDay(List<Lesson> lessonList, int dayOfWeek) throws ServiceException;

    List<SchedulePupilLesson> GetPupilDayLessons(int pupilID, Date date) throws ServiceException;
    List<ScheduleTeacherLesson> GetTeacherDayLessons(int teacherID, Date date) throws ServiceException;
    List<SchedulePupilLesson> GetClassDayLessons(int classID, Date date) throws ServiceException;
    List<Lesson> GetNextLessons(int currentLessonID, int count) throws ServiceException;

}
