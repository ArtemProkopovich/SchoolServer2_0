package DAO.Impl;

import DAO.MySqlConnection;
import Entities.Lesson;
import Entities.Mark;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Артем on 22.05.2016.
 */
public class LessonDaoTest {

    static MySqlConnection connection;
    static LessonDao dao;

    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        dao = new LessonDao(connection);
    }

    @Test
    public void getPupilDayLessons() throws Exception {
        List<Lesson> lessons = dao.GetPupilDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessons);
        Assert.assertTrue(lessons.size() > 0);
    }

    @Test
    public void getClassDayLessons() throws Exception {
        List<Lesson> lessons = dao.GetClassDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessons);
        Assert.assertTrue(lessons.size() > 0);
    }

    @Test
    public void getTeacherDayLessons() throws Exception {
        List<Lesson> lessons = dao.GetTeacherDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessons);
        Assert.assertTrue(lessons.size() > 0);
    }

    @Test
    public void getSubjectLessons() throws Exception {
        List<Lesson> lessons = dao.GetSubjectLessons(1);
        Assert.assertNotNull(lessons);
        Assert.assertTrue(lessons.size() > 0);
    }

    @Test
    public void getLessonMarks() throws Exception {
        List<Mark> marks = dao.GetLessonMarks(1);
        Assert.assertNotNull(marks);
        Assert.assertTrue(marks.size() > 0);
    }
}