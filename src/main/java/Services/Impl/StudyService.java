package Services.Impl;

import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;
import Entities.Subject;
import Services.Interfacies.IStudyService;
import Services.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Артем on 04.05.2016.
 */
public class StudyService implements IStudyService {

    private IUnitOfWork uof;

    public StudyService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public void GetLessonPupilMarksList(int lessonID) throws ServiceException {
        try {
            Lesson lesson = uof.getLessonDao().Select(lessonID);
            List<Pupil> pupilsList = uof.getSubjectDao().GetSubjectPupils(lesson.getSubjectID());
            List<Mark> markList = uof.getLessonDao().GetLessonMarks(lesson.getID());
            Map<Pupil, Mark> pupilMarkMap = new HashMap<Pupil, Mark>();
            for (Pupil p:pupilsList) {
                int i = 0;
                for (; i < markList.size(); i++) {
                    if (markList.get(i).getPupilID() == p.getID())
                        break;
                }
                if (i != markList.size())
                    pupilMarkMap.put(p, markList.get(i));
                else {
                    Mark defMark = new Mark();
                    defMark.setLessonID(lesson.getID());
                    defMark.setPupilID(p.getID());
                    defMark.setMark(0);
                    pupilMarkMap.put(p, defMark);
                }
            }
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void GetPupilSubjectMarks(int subjectID, int pupilID) throws ServiceException {
        try {
            List<Mark> markList = uof.getMarkDao().GetPupilMarksBySubjectID(subjectID, pupilID);
            List<Lesson> lessonList = uof.getLessonDao().GetSubjectLessons(subjectID);
            Map<Lesson, Mark> lessonMarkMap = new HashMap<Lesson, Mark>();
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
}
