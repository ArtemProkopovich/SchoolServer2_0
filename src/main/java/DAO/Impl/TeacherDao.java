package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.ITeacherDao;
import DAO.MySqlConnection;
import Entities.Subject;
import Entities.Teacher;
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
public class TeacherDao implements ITeacherDao {

    private final String SELECT_SUBJECTS_BY_TEACHER="SELECT * FROM subjects WHERE subjects.teacher_id = ?";
    private final String SELECT_USER_BY_TEACHER_ID = "SELECT * FROM users JOIN teachers ON users.user_id=teachers.user_id WHERE teacher_id = ?";
    private final String INSERT_TEACHER="INSERT INTO `teachers` (`surname`, `name`, `type`, `user_id`) VALUES (?, ?, ?, ?)";
    private final String SELECT_TEACHER="SELECT * FROM teachers WHERE teacher_id=?";
    private final String SELECT_ALL_TEACHERS = "SELECT * FROM teachers";
    private final String UPDATE_TEACHER="UPDATE INTO teachers SET surname=?, name=?, type=?, user_id=? WHERE teacher_id=?";
    private final String DELETE_TEACHER="DELETE FROM teachers WHERE teacher_id=?";

    private MySqlConnection connection;
    public TeacherDao(MySqlConnection connection){
        this.connection = connection;
    }

    public User GetUserByTeacherID(int teacherID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_USER_BY_TEACHER_ID);
            st.setInt(1,teacherID);
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

    public List<Teacher> GetTeacherList() throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_ALL_TEACHERS);
            ResultSet set = st.executeQuery();
            ArrayList<Teacher> result = new ArrayList<Teacher>();
            while (set.next()){
                Teacher teacher = new Teacher();
                teacher.setSurname(set.getString("surname"));
                teacher.setName(set.getString("name"));
                teacher.setUserID(set.getInt("user_id"));
                teacher.setID(set.getInt("teacher_id"));
                teacher.setType(set.getString("type"));
                result.add(teacher);
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

    public List<Subject> GetTeacherSubjects(int teacherID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_SUBJECTS_BY_TEACHER);
            st.setInt(1,teacherID);
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

    public int Insert(Teacher item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_TEACHER,PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1,item.getSurname());
            st.setString(2,item.getName());
            st.setString(3,item.getType());
            st.setInt(4,item.getUserID());
            st.executeQuery();
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

    public Teacher Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_TEACHER);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                Teacher teacher = new Teacher();
                teacher.setSurname(set.getString("surname"));
                teacher.setName(set.getString("name"));
                teacher.setUserID(set.getInt("user_id"));
                teacher.setID(set.getInt("teacher_id"));
                teacher.setType(set.getString("type"));
                return teacher;
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

    public void Update(Teacher item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_TEACHER);
            st.setString(1,item.getSurname());
            st.setString(2,item.getName());
            st.setString(3,item.getType());
            st.setInt(4,item.getUserID());
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
            PreparedStatement st = cn.prepareStatement(DELETE_TEACHER);
            st.setInt(1,id);
            st.executeQuery();
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
