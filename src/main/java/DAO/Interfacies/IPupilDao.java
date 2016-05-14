package DAO.Interfacies;

import DAO.DAOException;
import Entities.Class;
import Entities.Pupil;
import Entities.Subject;
import Entities.User;

import java.util.List;

/**
 * Created by Артем on 03.05.2016.
 */
public interface IPupilDao extends IDao<Pupil> {
    User GetUserByPupilID(int pupilID) throws DAOException;
    Class GetPupilClass(int pupilID) throws DAOException;
    List<Pupil> GetPupilList() throws DAOException;
    List<Subject> GetPupilSubjects(int pupilID) throws DAOException;
}
