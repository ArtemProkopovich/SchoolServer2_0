package Services.Interfacies;

import ActionEntities.FullSubject;
import ActionEntities.PupilDayLesson;
import ActionEntities.ScheduleClassLesson;
import ActionEntities.TeacherDayLesson;
import Entities.Class;
import Entities.*;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;
import Services.ServiceException;
import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 02.05.2016.
 */
public interface IScheduleService {

    void AddSubject(Subject subject) throws ServiceException;
    void UpdateSubject(Subject subject) throws ServiceException;
    void RemoveSubject(int id) throws ServiceException;
    List<FullSubject> GetSubjectList() throws ServiceException;
    List<FullSubject> GetSubjectListForClass(int classID) throws ServiceException;
    List<FullSubject> GetSubjectListForTeacher(int teacherID) throws ServiceException;

    void AddLesson (Lesson lesson, int dayOfWeek) throws ServiceException;
    void UpdateLesson(Lesson lesson, int dayOfWeek) throws ServiceException;
    void RemoveLesson(int lessonID) throws ServiceException;

    void CreateScheduleForDay(List<Lesson> lessonList, int dayOfWeek) throws ServiceException;
    List<ScheduleClassLesson> GetClassDayLessons(int classID, int dayOfWeek) throws ServiceException;

    List<PupilDayLesson> GetPupilDayLessons(int pupilID, Date date) throws ServiceException;
    List<TeacherDayLesson> GetTeacherDayLessons(int teacherID, Date date) throws ServiceException;
    //List<Lesson> GetNextLessons(int currentLessonID, int count) throws ServiceException;
}
