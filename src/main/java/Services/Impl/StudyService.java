package Services.Impl;

import ActionEntities.LessonJournal;
import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.*;
import Entities.Class;
import Services.Interfacies.IStudyService;
import Services.ServiceException;

import java.util.*;

/**
 * Created by Артем on 04.05.2016.
 */
public class StudyService implements IStudyService {

    private IUnitOfWork uof;

    public StudyService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public LessonJournal GetLessonPupilMarksList(int lessonID) throws ServiceException {
        try {
            Lesson lesson = uof.getLessonDao().Select(lessonID);
            Subject subject = uof.getSubjectDao().Select(lesson.getSubjectID());
            Class cls = uof.getClassDao().Select(subject.getClassID());
            List<Pupil> pupilsList =  uof.getClassDao().GetClassPupilList(cls.getID());;
            Map<Pupil, Mark> pupilMarkMap = new LinkedHashMap<Pupil, Mark>();
            for (Pupil p : pupilsList) {
                Mark mark = uof.getMarkDao().GetPupilLessonMark(lesson.getID(), p.getID());
                if (mark != null)
                    pupilMarkMap.put(p, mark);
                else {
                    Mark defMark = new Mark();
                    defMark.setLessonID(lesson.getID());
                    defMark.setPupilID(p.getID());
                    defMark.setMark(0);
                    pupilMarkMap.put(p, defMark);
                }
            }
            return new LessonJournal(pupilMarkMap, lesson, subject, cls);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void GetPupilSubjectMarks(int subjectID, int pupilID) throws ServiceException {
        try {
            List<Mark> markList = uof.getMarkDao().GetPupilMarksBySubjectID(subjectID, pupilID);
            List<Lesson> lessonList = uof.getLessonDao().GetSubjectLessons(subjectID);
            Map<Lesson, Mark> lessonMarkMap = new LinkedHashMap<Lesson, Mark>();
            for (Lesson l:lessonList) {
                int i = 0;
                for (; i < markList.size(); i++) {
                    if (markList.get(i).getLessonID() == l.getID())
                        break;
                }
                if (i != markList.size())
                    lessonMarkMap.put(l, markList.get(i));
                else {
                    Mark defMark = new Mark();
                    defMark.setLessonID(l.getID());
                    defMark.setPupilID(pupilID);
                    defMark.setMark(0);
                    lessonMarkMap.put(l, defMark);
                }
            }

        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void UpdateLessonPupilsMarks(Map<Pupil, Mark> pupilMarkMap) throws ServiceException {
        try {
            Set<Map.Entry<Pupil, Mark>> set = pupilMarkMap.entrySet();
            for (Map.Entry<Pupil, Mark> s : set) {
                int mark = s.getValue().getMark();
                if (mark > 0 && mark <= 10 || mark == -1) {
                    if (s.getValue().getID() != 0)
                        uof.getMarkDao().Update(s.getValue());
                    else
                        uof.getMarkDao().Insert(s.getValue());
                }
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void UpdatePupilSubjectMarks(Map<Lesson, Mark> lessonMarkMap) throws ServiceException {
        try {
            Set<Map.Entry<Lesson, Mark>> set = lessonMarkMap.entrySet();
            for (Map.Entry<Lesson, Mark> s : set) {
                int mark = s.getValue().getMark();
                if (mark > 0 && mark <= 10 || mark == -1) {
                    if (s.getValue().getID() != 0)
                        uof.getMarkDao().Update(s.getValue());
                    else
                        uof.getMarkDao().Insert(s.getValue());
                }
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }

    }

    public void UpdatePupilMark(int lessonID, int pupilID, int mark) throws ServiceException {
        try {
            Mark dbMark = uof.getMarkDao().GetPupilLessonMark(lessonID, pupilID);
            if (dbMark != null) {
                if (mark != 0) {
                    dbMark.setMark(mark);
                    uof.getMarkDao().Update(dbMark);
                } else
                    uof.getMarkDao().Delete(dbMark.getID());
            } else if (mark != 0) {
                dbMark = new Mark();
                dbMark.setMark(mark);
                dbMark.setLessonID(lessonID);
                dbMark.setPupilID(pupilID);
                uof.getMarkDao().Insert(dbMark);
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void AddLessonHomework(Lesson lesson) throws ServiceException {
        try {
            uof.getLessonDao().Update(lesson);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void UpdateLessonHomework(Lesson lesson) throws ServiceException {
        try {
            uof.getLessonDao().Update(lesson);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }

    }

    public void UpdateLessonHomework(int lessonID, String homework) throws ServiceException {
        try {
            Lesson dbLesson = uof.getLessonDao().Select(lessonID);
            if (dbLesson!=null) {
                dbLesson.setHomework(homework);
                uof.getLessonDao().Update(dbLesson);
            }
            else
                throw new ServiceException("Lesson not found.");
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }
}
