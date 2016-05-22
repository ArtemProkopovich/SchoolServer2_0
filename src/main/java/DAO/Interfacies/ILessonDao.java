package DAO.Interfacies;

import DAO.DAOException;
import Entities.Lesson;
import Entities.Mark;

import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 01.05.2016.
 */
public interface ILessonDao extends IDao<Lesson> {

    List<Lesson> GetPupilDayLessons(int pupilID, Date date) throws DAOException;
    List<Lesson> GetClassDayLessons(int classID, Date date) throws DAOException;
    List<Lesson> GetTeacherDayLessons(int teacherID, Date date) throws DAOException;
    List<Lesson> GetSubjectLessons(int subjectID) throws DAOException;
    List<Mark> GetLessonMarks(int lessonID) throws DAOException;
}
