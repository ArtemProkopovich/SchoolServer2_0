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
    void RemoveSubject(Subject subject) throws ServiceException;
    List<Subject> GetSubjectListForClass(Class cls) throws ServiceException;
    List<Subject> GetSubjectListForTeacher(Teacher teacher) throws ServiceException;

    void CreateScheduleForDay(List<Lesson> lessonList, int dayOfWeek) throws ServiceException;

    List<SchedulePupilLesson> GetPupilDayLessons(Pupil pupil, Date date) throws ServiceException;
    List<ScheduleTeacherLesson> GetTeacherDayLessons(Teacher teacher, Date date) throws ServiceException;
    List<SchedulePupilLesson> GetClassDayLessons(Class cls, Date date) throws ServiceException;
    List<Lesson> GetNextLessons(Lesson currentLesson, int count) throws ServiceException;

}
