package DAO.Interfacies;

/**
 * Created by Артем on 03.05.2016.
 */
import DAO.DAOException;
import Entities.Class;
import Entities.Pupil;
import Entities.Subject;

import java.util.List;

public interface IClassDao extends IDao<Class> {
    List<Pupil> GetClassPupilList(int classID) throws DAOException;

    List<Class> GetClassList() throws DAOException;

    List<Subject> GetClassSubjects(int classID) throws DAOException;
}
