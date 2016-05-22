package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.IMarkDao;
import DAO.MySqlConnection;
import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 03.05.2016.
 */
public class MarkDao implements IMarkDao {

    private final String SELECT_MARK_BY_ID = "SELECT * FROM `marks` WHERE mark_id = ?";
    private final String INSERT_MARK="INSERT INTO `marks` (`mark`, `pupil_id`, `lesson_id`) VALUES (?, ?, ?)";
    private final String DELETE_MARK = "DELETE FROM `marks` WHERE `mark_id`= ?";
    private final String UPDATE_MARK = "UPDATE `marks` SET `mark`=?, `pupil_id`=?, `lesson_id`=? WHERE `mark_id`=?";
    private final String SELECT_PUPIL_BY_MARK = "SELECT * FROM pupils WHERE pupil_id=?";
    private final String SELECT_LESSON_BY_MARK = "SELECT * FROM lessons WHERE lesson_id=?";
    private final String SELECT_PUPILS_MARKS_BY_SUBJECT = "SELECT * FROM marks " +
            "JOIN lessons ON marks.lesson_id=lessons.lesson_id " +
            "JOIN subjects ON subjects.subject_id=lessons.subject_id " +
            "WHERE subjects.subject_id=? && marks.pupil_id=?";

    private MySqlConnection connection;
    public MarkDao(MySqlConnection connection){
        this.connection = connection;
    }

    public int Insert(Mark mark) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_MARK,PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, mark.getMark());
            st.setInt(2, mark.getPupilID());
            st.setInt(3, mark.getLessonID());
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

    public Mark Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_MARK_BY_ID);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()){
                Mark mark = new Mark();
                mark.setID(set.getInt("mark_id"));
                mark.setMark(set.getInt("mark"));
                mark.setPupilID(set.getInt("pupil_id"));
                mark.setLessonID(set.getInt("lesson_id"));
                return mark;
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

    public void Update(Mark mark) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_MARK);
            st.setInt(1, mark.getMark());
            st.setInt(2, mark.getPupilID());
            st.setInt(3, mark.getLessonID());
            st.setInt(4,mark.getID());
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
            PreparedStatement st = cn.prepareStatement(DELETE_MARK);
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

    public Pupil GetPupilByMarkID(int markID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPIL_BY_MARK);
            st.setInt(1,markID);
            ResultSet set = st.executeQuery();
            if (set.next()){
                Pupil pupil = new Pupil();
                pupil.setID(set.getInt("pupil_id"));
                pupil.setName(set.getString("name"));
                pupil.setSurname(set.getString("surname"));;
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

    public Lesson GetLessonByMarkID(int markID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_LESSON_BY_MARK);
            st.setInt(1,markID);
            ResultSet set = st.executeQuery();
            if (set.next()){
                Lesson lesson = new Lesson();
                lesson.setID(set.getInt("lesson_id"));
                lesson.setDate(set.getDate("date"));
                lesson.setScheduleNumber(set.getInt("schedule_number"));
                lesson.setSubjectID(set.getInt("subject_id"));
                return lesson;
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

    public List<Mark> GetPupilMarksBySubjectID(int subjectID, int pupilID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPILS_MARKS_BY_SUBJECT);
            st.setInt(1, subjectID);
            st.setInt(2, pupilID);
            ResultSet set = st.executeQuery();
            ArrayList<Mark> result = new ArrayList<Mark>();
            while (set.next()){
                Mark mark = new Mark();
                mark.setID(set.getInt("mark_id"));
                mark.setMark(set.getInt("mark"));
                mark.setLessonID(set.getInt("lesson_id"));
                mark.setPupilID(set.getInt("pupil_id"));
                result.add(mark);
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
}
