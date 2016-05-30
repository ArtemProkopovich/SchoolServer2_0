package Services.Impl;

import ActionEntities.PupilDayLesson;
import ActionEntities.ScheduleClassLesson;
import ActionEntities.TeacherDayLesson;
import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Entities.Lesson;
import Entities.Subject;
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
    public void getClassDayLessons() throws Exception {
        List<ScheduleClassLesson> lessonList = service.GetClassDayLessons(1, 0);
        Assert.assertNotNull(lessonList);
        Assert.assertTrue(lessonList.size() > 0);
    }

    @Test
    public void addLesson() throws Exception {
        Lesson l = new Lesson();
        l.setSubjectID(4);
        l.setRoom(100);
        l.setScheduleNumber(3);
        service.AddLesson(l, 1);
    }

    @Test
    public void removeLesson() throws Exception {
        service.RemoveLesson(7);
    }

    private Subject InitSubject(){
        Subject s = new Subject();
        s.setClassID(1);
        s.setName("Subject");
        s.setTeacherID(2);
        return s;
    }
}
