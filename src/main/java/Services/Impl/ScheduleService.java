package Services.Impl;

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

    public Subject AddSubject(Subject subject) throws ServiceException {
        try{
            int subject_id = uof.getSubjectDao().Insert(subject);
            subject.setID(subject_id);
            return subject;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Subject UpdateSubject(Subject subject) throws ServiceException {
        try {
            Subject dbSubject = uof.getSubjectDao().Select(subject.getID());
            Class dbClass = uof.getClassDao().Select(subject.getClassID());
            Teacher dbTeacher = uof.getTeacherDao().Select(subject.getTeacherID());
            if (dbSubject!=null && dbClass != null && dbTeacher != null) {
                    uof.getSubjectDao().Update(subject);
            }
            else
                throw new ServiceException();
            return subject;
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

    public List<Subject> GetSubjectList() throws ServiceException {
        try {
            return uof.getSubjectDao().GetSubjectList();
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Subject> GetSubjectListForClass(int classID) throws ServiceException {
        try {
            return uof.getClassDao().GetClassSubjects(classID);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Subject> GetSubjectListForTeacher(int teacherID) throws ServiceException {
        try {
            return uof.getTeacherDao().GetTeacherSubjects(teacherID);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void CreateScheduleForDay(List<Lesson> lessonList, int dayOfWeek) throws ServiceException {
        try {
            Calendar startDate = new GregorianCalendar(2015, 8, 01);
            startDate.setFirstDayOfWeek(Calendar.MONDAY);
            while (startDate.get(Calendar.DAY_OF_WEEK) != DayOfWeekToCalendar(dayOfWeek))
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            Calendar endDate = new GregorianCalendar(2016, 6, 1);
            while (startDate.before(endDate)) {
                for (Lesson l : lessonList) {
                    l.setDate(startDate.getTime());
                    l.setHomework("");
                    uof.getLessonDao().Insert(l);
                }
                startDate.add(Calendar.WEEK_OF_YEAR, 1);
            }
        }
        catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public List<SchedulePupilLesson> GetPupilDayLessons(int pupilID, Date date) throws ServiceException {
        try {
            List<Lesson> lessonList = uof.getLessonDao().GetPupilDayLessons(pupilID,date);
            List<SchedulePupilLesson> resultList = new ArrayList<SchedulePupilLesson>();
            for(Lesson l : lessonList){
                SchedulePupilLesson spl = new SchedulePupilLesson();
                spl.setLesson(l);
                spl.setSubject(uof.getSubjectDao().Select(l.getSubjectID()));
                spl.setTeacher(uof.getTeacherDao().Select(spl.getSubject().getTeacherID()));
                resultList.add(spl);
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<ScheduleTeacherLesson> GetTeacherDayLessons(int teacherID, Date date) throws ServiceException {
        try {
            List<Lesson> lessonList =  uof.getLessonDao().GetTeacherDayLessons(teacherID,date);
            List<ScheduleTeacherLesson> resultList = new ArrayList<ScheduleTeacherLesson>();
            for(Lesson l : lessonList){
                ScheduleTeacherLesson stl = new ScheduleTeacherLesson();
                stl.setLesson(l);
                stl.setSubject(uof.getSubjectDao().Select(l.getSubjectID()));
                stl.setCls(uof.getClassDao().Select(stl.getSubject().getClassID()));
                resultList.add(stl);
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<SchedulePupilLesson> GetClassDayLessons(int classID, Date date) throws ServiceException {
        try {
            List<Lesson> lessonList =  uof.getLessonDao().GetClassDayLessons(classID,date);
            List<SchedulePupilLesson> resultList = new ArrayList<SchedulePupilLesson>();
            for(Lesson l : lessonList){
                SchedulePupilLesson spl = new SchedulePupilLesson();
                spl.setLesson(l);
                spl.setSubject(uof.getSubjectDao().Select(l.getSubjectID()));
                spl.setTeacher(uof.getTeacherDao().Select(spl.getSubject().getTeacherID()));
                resultList.add(spl);
            }
            return resultList;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Lesson> GetNextLessons(int currentLessonID, int count) throws ServiceException {
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
    }

    private int DayOfWeekToCalendar(int dayOfWeek) {
        if (dayOfWeek > 0 && dayOfWeek < 7)
            return dayOfWeek + 1;
        else
            return 1;
    }
}
