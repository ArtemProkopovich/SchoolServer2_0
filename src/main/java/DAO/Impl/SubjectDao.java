package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.ISubjectDao;
import DAO.MySqlConnection;
import Entities.Class;
import Entities.Pupil;
import Entities.Subject;
import Entities.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 01.05.2016.
 */
public class SubjectDao implements ISubjectDao {

    private final String SELECT_PUPILS_BY_SUBJECT ="SELECT * FROM subjects JOIN pupils ON pupils.class_id=subjects.class_id WHERE subject_id = ? ORDER BY surname";
    private final String SELECT_TEACHER_BY_SUBJECT ="SELECT * FROM subjects " +
            "JOIN teachers ON subjects.teacher_id=teachers.teacher_id " +
            "WHERE subject_id = ?";
    private final String SELECT_SUBJECT_BY_ID = "SELECT * FROM subjects WHERE subject_id = ?";
    private final String SELECT_SUBJECTS = "SELECT * FROM subjects";
    private final String INSERT_SUBJECT = "INSERT INTO subjects (name, lesson_count, class_id, teacher_id) VALUES (?, ?, ?, ?)";
    private final String DELETE_SUBJECT = "DELETE FROM subjects WHERE `subject_id`= ?";
    private final String UPDATE_SUBJECT = "UPDATE `subjects`" +
            "SET `name`= ? , `lesson_count`= ?, `class_id`= ?, `teacher_id`= ? WHERE `subject_id`= ?";
    private final String SELECT_CLASS_BY_SUBJECT = "SELECT * FROM classes " +
            "JOIN subjects ON classes.class_id = subjects.class_id " +
            "WHERE subject_id=?";


    private MySqlConnection connection;
    public SubjectDao(MySqlConnection connection){
        this.connection = connection;
    }


    public Teacher GetSubjectTeacher(int subjectID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_TEACHER_BY_SUBJECT);
            st.setInt(1,subjectID);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                return TeacherDao.ResultSetToTeacher(set);
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

    public Class GetSubjectClass(int subjectID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_CLASS_BY_SUBJECT);
            st.setInt(1,subjectID);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                return ClassDao.ResultSetToClass(set);
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

    public List<Subject> GetSubjectList() throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_SUBJECTS);
            ResultSet set = st.executeQuery();
            ArrayList<Subject> result = new ArrayList<Subject>();
            while (set.next()){
                result.add(ResultSetToSubject(set));
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

    public List<Pupil> GetSubjectPupils(int subjectID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPILS_BY_SUBJECT);
            st.setInt(1, subjectID);
            ResultSet set = st.executeQuery();
            ArrayList<Pupil> result = new ArrayList<Pupil>();
            while (set.next()){
                result.add(PupilDao.ResultSetToPupil(set));
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

    public int Insert(Subject item) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_SUBJECT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, item.getName());
            st.setInt(2, item.getLessonCount());
            st.setInt(3, item.getClassID());
            if (item.getTeacherID() > 0)
                st.setInt(4, item.getTeacherID());
            else
                st.setNull(4, Types.INTEGER);
            st.executeUpdate();
            ResultSet set = st.getGeneratedKeys();
            if (set.next()) {
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

    public Subject Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_SUBJECT_BY_ID);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()){
                return ResultSetToSubject(set);
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

    public void Update(Subject item) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_SUBJECT);
            st.setString(1,item.getName());
            st.setInt(2,item.getLessonCount());
            st.setInt(3,item.getClassID());
            if (item.getTeacherID() > 0)
                st.setInt(4, item.getTeacherID());
            else
                st.setNull(4, Types.INTEGER);
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
            PreparedStatement st = cn.prepareStatement(DELETE_SUBJECT);
            st.setInt(1,id);
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

    public static Subject ResultSetToSubject(ResultSet set) throws SQLException {
        Subject subject = new Subject();
        subject.setID(set.getInt("subject_id"));
        subject.setName(set.getString("subjects.name"));
        subject.setLessonCount(set.getInt("lesson_count"));
        subject.setClassID(set.getInt("class_id"));
        subject.setTeacherID(set.getInt("teacher_id"));
        return subject;
    }
}
