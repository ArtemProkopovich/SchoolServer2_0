package DAO.Interfacies;

import DAO.DAOException;
import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;

import java.util.List;

/**
 * Created by Артем on 03.05.2016.
 */
public interface IMarkDao extends IDao<Mark> {
    Pupil GetPupilByMarkID(int markID) throws DAOException;
    Lesson GetLessonByMarkID(int markID) throws DAOException;
    Mark GetPupilLessonMark(int lessonID, int pupilID) throws DAOException;
    List<Mark> GetPupilMarksBySubjectID(int subjectID, int pupilID) throws DAOException;
}
