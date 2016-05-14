package DAO.Interfacies;

/**
 * Created by Артем on 03.05.2016.
 */
import DAO.DAOException;
import Entities.Class;
import Entities.Subject;

import java.util.List;

public interface IClassDao extends IDao<Class> {
    List<Class> GetClassList() throws DAOException;
    List<Subject>GetClassSubjects(int classID) throws DAOException;
}
