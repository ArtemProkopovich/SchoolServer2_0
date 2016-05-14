package DAO.Interfacies;

import DAO.DAOException;
import Entities.Pupil;
import Entities.Teacher;
import Entities.User;

import java.util.List;

/**
 * Created by Артем on 01.05.2016.
 */
public interface IUserDao extends IDao<User> {
    User GetByLoginAndPassword(String login, String password) throws DAOException;
    List<User> GetUserList() throws DAOException;
    Pupil GetPupilByUserId(int id) throws DAOException;
    Teacher GetTeacherByUserId(int id) throws DAOException;
}
