package Services.Impl;

import ActionEntities.LessonJournal;
import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Entities.Lesson;
import Entities.Mark;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Артем on 24.05.2016.
 */
public class StudyServiceTest {

    static MySqlConnection connection;
    static UnitOfWork uof;
    static StudyService service;
    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        uof = new UnitOfWork(connection);
        service = new StudyService(uof);
    }

    @Test
    public void getLessonPupilMarksList() throws Exception {
        LessonJournal journal = service.GetLessonPupilMarksList(1);
        Assert.assertNotNull(journal);
        Assert.assertTrue(journal.getHomework()!=null && journal.getClassLetter()!=null && journal.getPupils().size()>0
        && journal.getPupils().get(0).getMark()!=null);
    }

    @Test
    public void updatePupilMark() throws Exception {
        service.UpdatePupilMark(1, 1, 6);
        Mark mark = uof.getMarkDao().GetPupilLessonMark(1, 1);
        Assert.assertTrue(mark != null && mark.getMark() == 6);
    }

    @Test
    public void updateLessonHomework() throws Exception {
        service.UpdateLessonHomework(1, "update");
        Lesson lesson = uof.getLessonDao().Select(1);
        Assert.assertTrue(lesson != null && lesson.getHomework().equals("update"));
    }

}