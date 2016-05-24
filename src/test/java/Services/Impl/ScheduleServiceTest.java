package Services.Impl;

import ActionEntities.PupilDayLesson;
import ActionEntities.TeacherDayLesson;
import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Entities.Lesson;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;
import org.junit.Assert;
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
    public void getPupilDayLessons() throws Exception {
        List<PupilDayLesson> lessonList = service.GetPupilDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessonList);
        Assert.assertTrue(lessonList.size() > 0 && lessonList.get(0).getSubject() != null && lessonList.get(0).getMark()
                != null && lessonList.get(0).getTeacher() != null);

    }

    @Test
    public void getTeacherDayLessons() throws Exception {
        List<TeacherDayLesson> lessonList = service.GetTeacherDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessonList);
        Assert.assertTrue(lessonList.size() > 0 && lessonList.get(0).getSubject() != null && lessonList.get(0).getClassLetter()
                != null && lessonList.get(0).getTimeRange() != null);
    }

    @Test
    public void createScheduleForDay() throws Exception {
        List<Lesson> lessonList = new ArrayList<Lesson>();
        Lesson lesson = new Lesson();
        lesson.setRoom(100);
        lesson.setScheduleNumber(1);
        lesson.setSubjectID(1);
        lessonList.add(lesson);
        //service.CreateScheduleForDay(lessonList,1);
    }

    @Test
    public void getClassDayLessons() throws Exception {
        List<SchedulePupilLesson> lessonList = service.GetClassDayLessons(1, new GregorianCalendar(2016, 04, 24).getTime());
        Assert.assertNotNull(lessonList);
        Assert.assertTrue(lessonList.size() > 0);
    }

    @Test
    public void getNextLessons() throws Exception {
        List<Lesson> lessonList = service.GetNextLessons(1, 10);
        Assert.assertNotNull(lessonList);
        Assert.assertTrue(lessonList.size() > 0);
    }

}