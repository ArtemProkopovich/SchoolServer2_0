package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.IPupilDao;
import DAO.MySqlConnection;
import Entities.Class;
import Entities.Pupil;
import Entities.Subject;
import Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 03.05.2016.
 */
public class PupilDao implements IPupilDao {

    private final String INSERT_PUPIL="INSERT INTO `pupils` (`surname`, `name`, `user_id`, `class_id`) VALUES (?, ?, ?, ?)";
    private final String SELECT_PUPIL = "SELECT * FROM pupils WHERE pupil_id=?";
    private final String SELECT_ALL_PUPILS = "SELECT * FROM pupils";
    private final String DELETE_PUPIL = "DELETE FROM pupils WHERE pupil_id=?";
    private final String UPDATE_PUPIL = "UPDATE NITO pupils SET surname=?, name = ?, user_id=?, class_id=? WHERE pupil_id=?";
    private final String SELECT_USER_BY_PUPIL_ID = "SELECT * FROM users JOIN pupils ON users.user_id=pupils.user_id WHERE pupil_id = ?";
    private final String SELECT_CLASS_BY_PUPIL = "SELECT * FROM classes JOIN pupils ON classes.class_id=pupils.class_id WHERE pupil_id=?";
    private final String SELECT_PUPIL_SUBJECTS = "SELECT * FROM pupils JOIN classes ON pupils.class_id = classes.class_id " +
            "JOIN subjects ON subjects.class_id=subjects.class_id WHERE pupil_id=?";

    private MySqlConnection connection;
    public PupilDao(MySqlConnection connection){
        this.connection = connection;
    }

    public User GetUserByPupilID(int pupilID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_USER_BY_PUPIL_ID);
            st.setInt(1,pupilID);
            ResultSet set = st.executeQuery();
            if (set.next()){
                User user = new User();
                user.setID(set.getInt("user_id"));
                user.setLogin(set.getString("login"));
                user.setPassword(set.getString("password"));
                user.setEmail(set.getString("email"));
                return user;
            }
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
        return null;
    }

    public Class GetPupilClass(int pupilID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_CLASS_BY_PUPIL);
            st.setInt(1,pupilID);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                Class cl = new Class();
                cl.setID(set.getInt("class_id"));
                cl.setGrade(set.getInt("grade"));
                cl.setLetter(set.getString("letter"));
                return cl;
            }
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
        return null;
    }

    public List<Pupil> GetPupilList() throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_ALL_PUPILS);
            ResultSet set = st.executeQuery();
            ArrayList<Pupil> result = new ArrayList<Pupil>();
            while (set.next()){
                Pupil pupil = new Pupil();
                pupil.setID(set.getInt("pupil_id"));
                pupil.setSurname(set.getString("surname"));
                pupil.setName(set.getString("name"));
                pupil.setClassID(set.getInt("class_id"));
                pupil.setUserID(set.getInt("user_id"));
                result.add(pupil);
            }
            return result;
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
    }

    public List<Subject> GetPupilSubjects(int pupilID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPIL_SUBJECTS);
            st.setInt(1, pupilID);
            ResultSet set = st.executeQuery();
            ArrayList<Subject> result = new ArrayList<Subject>();
            while (set.next()){
                Subject subject = new Subject();
                subject.setID(set.getInt("subject_id"));
                subject.setName(set.getString("name"));
                subject.setLessonCount(set.getInt("lesson_count"));
                subject.setClassID(set.getInt("class_id"));
                subject.setTeacherID(set.getInt("teacher_id"));
                result.add(subject);
            }
            return result;
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
    }


    public int Insert(Pupil item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_PUPIL,PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, item.getSurname());
            st.setString(2, item.getName());
            st.setInt(3, item.getUserID());
            st.setInt(4,item.getClassID());
            st.executeUpdate();
            ResultSet set = st.getGeneratedKeys();
            if (set.next()){
                return set.getInt(1);
            }
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
        return -1;
    }

    public Pupil Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPIL);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                Pupil pupil = new Pupil();
                pupil.setID(set.getInt("pupil_id"));
                pupil.setName(set.getString("name"));
                pupil.setSurname(set.getString("surname"));
                pupil.setUserID(set.getInt("user_id"));
                pupil.setClassID(set.getInt("class_id"));
                return pupil;
            }
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
        return null;
    }

    public void Update(Pupil item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_PUPIL);
            st.setString(1, item.getSurname());
            st.setString(2, item.getName());
            st.setInt(3, item.getUserID());
            st.setInt(4,item.getClassID());
            st.setInt(5,item.getID());
            st.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
    }

    public void Delete(int id) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(DELETE_PUPIL);
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DAOException(ex);
        }
        finally {
            if (cn!=null)
                connection.closeConnection();
        }
    }
}
