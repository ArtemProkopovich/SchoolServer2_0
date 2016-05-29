package DAO.Interfacies;

import DAO.DAOException;
import Entities.Subject;
import Entities.Teacher;
import Entities.User;
import Entities.Class;

import java.util.List;

/**
 * Created by Артем on 03.05.2016.
 */
public interface ITeacherDao extends IDao<Teacher> {
    User GetUserByTeacherID(int teacherID) throws DAOException;
    List<Teacher> GetTeacherList() throws DAOException;
    List<Subject> GetTeacherSubjects(int teacherID) throws DAOException;
    List<Class> getTeacherClasses(int teacherID) throws DAOException;
}
