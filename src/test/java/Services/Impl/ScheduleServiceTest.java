package Services.Impl;

import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Entities.Lesson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Артем on 22.05.2016.
 */
public class ScheduleServiceTest {

    static MySqlConnection connection;
    static UnitOfWork uof;
    static ScheduleService service;
    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        uof = new UnitOfWork(connection);
        service = new ScheduleService(uof);
    }

    @Test
    public void createScheduleForDay() throws Exception {
        List<Lesson> lessonList = new ArrayList<Lesson>();
        Lesson lesson = new Lesson();
        lesson.setRoom(100);
        lesson.setScheduleNumber(1);
        lessonList.add(lesson);
        service.CreateScheduleForDay(lessonList,1);
    }

    @Test
    public void getPupilDayLessons() throws Exception {

    }

    @Test
    public void getTeacherDayLessons() throws Exception {

    }

    @Test
    public void getClassDayLessons() throws Exception {

    }

    @Test
    public void getNextLessons() throws Exception {

    }

}