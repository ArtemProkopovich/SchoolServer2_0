package Services.Impl;

import ActionEntities.FullSubject;
import ActionEntities.PupilDayLesson;
import ActionEntities.ScheduleClassLesson;
import ActionEntities.TeacherDayLesson;
import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.Class;
import Entities.*;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;
import Services.Interfacies.IScheduleService;
import Services.ServiceException;

import java.util.*;

/**
 * Created by Артем on 02.05.2016.
 */
public class ScheduleService implements IScheduleService {

    private IUnitOfWork uof;

    public ScheduleService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public void AddSubject(Subject subject) throws ServiceException {
        try{
            int subject_id = uof.getSubjectDao().Insert(subject);
            subject.setID(subject_id);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void UpdateSubject(Subject subject) throws ServiceException {
        try {
            uof.getSubjectDao().Update(subject);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }


    public void RemoveSubject(int id) throws ServiceException {
        try{
            uof.getSubjectDao().Delete(id);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public List<FullSubject> GetSubjectList() throws ServiceException {
        try {
            List<Subject> subjects =  uof.getSubjectDao().GetSubjectList();
            List<FullSubject> result = new ArrayList<FullSubject>();
            for(Subject subject: subjects) {
                Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                Class cls = uof.getClassDao().Select(subject.getClassID());
                result.add(new FullSubject(subject,teacher,cls));
            }
            return result;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<FullSubject> GetSubjectListForClass(int classID) throws ServiceException {
        try {
            List<Subject> subjects =  uof.getClassDao().GetClassSubjects(classID);
            List<FullSubject> result = new ArrayList<FullSubject>();
            for(Subject subject: subjects) {
                Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                Class cls = uof.getClassDao().Select(subject.getClassID());
                result.add(new FullSubject(subject,teacher,cls));
            }
            return result;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<FullSubject> GetSubjectListForTeacher(int teacherID) throws ServiceException {
        try {
            List<Subject> subjects =  uof.getTeacherDao().GetTeacherSubjects(teacherID);
            List<FullSubject> result = new ArrayList<FullSubject>();
            for(Subject subject: subjects) {
                Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                Class cls = uof.getClassDao().Select(subject.getClassID());
                result.add(new FullSubject(subject,teacher,cls));
            }
            return result;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void AddLesson(Lesson lesson, int dayOfWeek) throws ServiceException {
        try {
            Calendar startDate = new GregorianCalendar(2016, 04, 01);
            startDate.setFirstDayOfWeek(Calendar.MONDAY);
            while (startDate.get(Calendar.DAY_OF_WEEK) != DayOfWeekToCalendar(dayOfWeek))
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            Calendar endDate = new GregorianCalendar(2017, 03, 30);
            while (startDate.before(endDate)) {
                lesson.setDate(startDate.getTime());
                lesson.setHomework("");
                uof.getLessonDao().Insert(lesson);
                startDate.add(Calendar.WEEK_OF_YEAR, 1);
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void UpdateLesson(Lesson lesson, int dayOfWeek) throws ServiceException {
        try {
            Calendar startDate = new GregorianCalendar(2016, 04, 01);
            startDate.setFirstDayOfWeek(Calendar.MONDAY);
            while (startDate.get(Calendar.DAY_OF_WEEK) != DayOfWeekToCalendar(dayOfWeek))
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            Calendar endDate = new GregorianCalendar(2017, 03, 30);
            while (startDate.before(endDate)) {
                lesson.setDate(startDate.getTime());
                lesson.setHomework("");
                uof.getLessonDao().Update(lesson);
                startDate.add(Calendar.WEEK_OF_YEAR, 1);
            }
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void RemoveLesson(int lessonID) throws ServiceException {
        try {
            uof.getLessonDao().Delete(lessonID);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void CreateScheduleForDay(List<Lesson> lessonList, int dayOfWeek) throws ServiceException {
        for (Lesson lesson : lessonList) {
            AddLesson(lesson, dayOfWeek);
        }
    }

    public List<PupilDayLesson> GetPupilDayLessons(int pupilID, Date date) throws ServiceException {
        try {
            List<Lesson> lessonList = uof.getLessonDao().GetPupilDayLessons(pupilID,date);
            List<PupilDayLesson> resultList = new ArrayList<PupilDayLesson>();
            for(Lesson l : lessonList) {
                SchedulePupilLesson spl = new SchedulePupilLesson();
                spl.setLesson(l);
                spl.setSubject(uof.getSubjectDao().Select(l.getSubjectID()));
                spl.setTeacher(uof.getTeacherDao().Select(spl.getSubject().getTeacherID()));
                Mark mark = uof.getMarkDao().GetPupilLessonMark(pupilID, l.getID());
                resultList.add(new PupilDayLesson(spl, mark));
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<TeacherDayLesson> GetTeacherDayLessons(int teacherID, Date date) throws ServiceException {
        try {
            List<Lesson> lessonList =  uof.getLessonDao().GetTeacherDayLessons(teacherID,date);
            List<TeacherDayLesson> resultList = new ArrayList<TeacherDayLesson>();
            for(Lesson l : lessonList){
                ScheduleTeacherLesson stl = new ScheduleTeacherLesson();
                stl.setLesson(l);
                stl.setSubject(uof.getSubjectDao().Select(l.getSubjectID()));
                stl.setCls(uof.getClassDao().Select(stl.getSubject().getClassID()));
                resultList.add(new TeacherDayLesson(stl));
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<ScheduleClassLesson> GetClassDayLessons(int classID, int dayOfWeek) throws ServiceException {
        try {
            Calendar startDate = new GregorianCalendar(2016, 04, 01);
            startDate.setFirstDayOfWeek(Calendar.MONDAY);
            while (startDate.get(Calendar.DAY_OF_WEEK) != DayOfWeekToCalendar(dayOfWeek))
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            List<Lesson> lessonList =  uof.getLessonDao().GetClassDayLessons(classID, startDate.getTime());
            List<ScheduleClassLesson> resultList = new ArrayList<ScheduleClassLesson>();
            for(Lesson lesson : lessonList) {
                Subject subject = uof.getSubjectDao().Select(lesson.getSubjectID());
                Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                resultList.add(new ScheduleClassLesson(lesson, subject, teacher));
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /*public List<Lesson> GetNextLessons(int currentLessonID, int count) throws ServiceException {
        try {
            List<Lesson> lessonList = uof.getLessonDao().GetSubjectLessons(currentLessonID);
            Lesson currentLesson = uof.getLessonDao().Select(currentLessonID);
            int index = lessonList.indexOf(currentLesson);
            List<Lesson> result = new ArrayList<Lesson>();
            for (int i = index + 1; i < lessonList.size(); i++) {
                result.add(lessonList.get(i));
            }
            return result;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }*/

    private int DayOfWeekToCalendar(int dayOfWeek) {
        if (dayOfWeek >= 0 && dayOfWeek < 6)
            return dayOfWeek + 2;
        else
            return 1;
    }
}
