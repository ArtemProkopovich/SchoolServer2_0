package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.ILessonDao;
import DAO.MySqlConnection;
import Entities.Lesson;
import Entities.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Артем on 01.05.2016.
 */
public class LessonDao implements ILessonDao {

    private final String SELECT_LESSON_MARKS = "SELECT * FROM `marks` WHERE `lesson_id` = ?";
    private final String SELECT_SUBJECT_LESSONS = "SELECT * FROM `lessons` WHERE `subject_id`=?";

    private final String SELECT_LESSON_BY_ID="SELECT * FROM `lessons` WHERE `lesson_id` = ?";
    private final String INSERT_LESSON="INSERT INTO `lessons` (`date`, `schedule_number`, `homework`,`room`, `subject_id`) " +
            "VALUES (?, ?, ?, ?, ?);";
    private final String UPDATE_LESSON="UPDATE `lessons` SET `date` = ?, `schedule_number` = ?, `homework`= ?, `room`=?, `subject_id` = ? " +
            "WHERE lesson_id=?";
    private final String DELETE_LESSON="DELETE FROM `lessons` WHERE `lesson_id`= ?";

    private final String SELECT_PUPIL_DAY_LESSONS="SELECT * FROM pupils " +
            "JOIN subjects ON subjects.class_id=pupils.class_id " +
            "JOIN lessons ON lessons.subject_id=subjects.subject_id " +
            "WHERE pupils.pupil_id=? && lessons.date=? ORDER BY schedule_number";
    private final String SELECT_TEACHER_DAY_LESSONS="SELECT * FROM teachers " +
            "JOIN subjects ON subjects.teacher_id=teachers.teacher_id " +
            "JOIN lessons ON lessons.subject_id=subjects.subject_id " +
            "WHERE teachers.teacher_id=? && lessons.date=? ORDER BY schedule_number";
    private final String SELECT_CLASS_DAY_LESSONS = "SELECT * FROM subjects " +
            "JOIN lessons ON lessons.subject_id=subjects.subject_id " +
            "WHERE subjects.class_id=? && lessons.date=? ORDER BY schedule_number";

    private MySqlConnection connection;
    public LessonDao(MySqlConnection connection){
        this.connection = connection;
    }

    public List<Lesson> GetPupilDayLessons(int pupilID, Date date) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPIL_DAY_LESSONS);
            st.setInt(1, pupilID);
            st.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet set = st.executeQuery();
            ArrayList<Lesson> result = new ArrayList<Lesson>();
            while (set.next()) {
                result.add(ResultSetToLesson(set));
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (cn != null)
                connection.closeConnection();
        }
    }

    public List<Lesson> GetClassDayLessons(int classID, Date date) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_CLASS_DAY_LESSONS);
            st.setInt(1, classID);
            st.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet set = st.executeQuery();
            ArrayList<Lesson> result = new ArrayList<Lesson>();
            while (set.next()) {
                result.add(ResultSetToLesson(set));
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (cn != null)
                connection.closeConnection();
        }
    }

    public List<Lesson> GetTeacherDayLessons(int teacherID, Date date) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_TEACHER_DAY_LESSONS);
            st.setInt(1, teacherID);
            st.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet set = st.executeQuery();
            ArrayList<Lesson> result = new ArrayList<Lesson>();
            while (set.next()) {
                result.add(ResultSetToLesson(set));
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (cn != null)
                connection.closeConnection();
        }
    }

    public List<Lesson> GetSubjectLessons(int subjectID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_SUBJECT_LESSONS);
            st.setInt(1, subjectID);
            ResultSet set = st.executeQuery();
            ArrayList<Lesson> result = new ArrayList<Lesson>();
            while (set.next()){
                result.add(ResultSetToLesson(set));
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

    public List<Mark> GetLessonMarks(int lessonID) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_LESSON_MARKS);
            st.setInt(1, lessonID);
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

    public int Insert(Lesson item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_LESSON,PreparedStatement.RETURN_GENERATED_KEYS);
            st.setDate(1, new java.sql.Date(item.getDate().getTime()));
            st.setInt(2,item.getScheduleNumber());
            st.setString(3,item.getHomework());
            st.setInt(4,item.getRoom());
            st.setInt(5,item.getSubjectID());
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

    public Lesson Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_LESSON_BY_ID);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()){
                return ResultSetToLesson(set);
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

    public void Update(Lesson item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_LESSON);
            st.setDate(1, new java.sql.Date(item.getDate().getTime()));
            st.setInt(2,item.getScheduleNumber());
            st.setString(3,item.getHomework());
            st.setInt(4,item.getRoom());
            st.setInt(5,item.getSubjectID());
            st.setInt(6,item.getID());
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
            PreparedStatement st = cn.prepareStatement(DELETE_LESSON);
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

    public static Lesson ResultSetToLesson(ResultSet set) throws SQLException{
        Lesson lesson = new Lesson();
        lesson.setID(set.getInt("lesson_id"));
        lesson.setDate(set.getDate("date"));
        lesson.setScheduleNumber(set.getInt("schedule_number"));
        lesson.setHomework(set.getString("homework"));
        lesson.setRoom(set.getInt("room"));
        lesson.setSubjectID(set.getInt("subject_id"));
        return lesson;
    }
}
