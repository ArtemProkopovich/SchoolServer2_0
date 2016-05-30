package Services.Impl;

import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Артем on 23.05.2016.
 */
public class PrintServiceTest {

    static MySqlConnection connection;
    static UnitOfWork uof;
    static PrintService service;
    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        uof = new UnitOfWork(connection);
        service = new PrintService(uof);
    }

    @Test
    public void printPDFAchivementStatistics() throws Exception {
        InputStream stream =  service.PrintPDFAchivementStatistics(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printPDFSubjectList() throws Exception {
        InputStream stream =  service.PrintPDFSubjectList(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printPDFTeacherWeekSchedule() throws Exception {
        InputStream stream =  service.PrintPDFTeacherWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printPDFPupilWeekSchedule() throws Exception {
        InputStream stream =  service.PrintPDFPupilWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printXLSAchivementStatistics() throws Exception {
        InputStream stream =  service.PrintXLSAchivementStatistics(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printXLSSubjectList() throws Exception {
        InputStream stream =  service.PrintXLSSubjectList(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printXLSTeacherWeekSchedule() throws Exception {
        InputStream stream =  service.PrintXLSTeacherWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printXLSPupilWeekSchedule() throws Exception {
        InputStream stream = service.PrintXLSPupilWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printCSVAchivementStatistics() throws Exception {
        InputStream stream =  service.PrintCSVAchivementStatistics(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printCSVSubjectList() throws Exception {
        InputStream stream =  service.PrintCSVSubjectList(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printCSVTeacherWeekSchedule() throws Exception {
        InputStream stream =  service.PrintCSVTeacherWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printCSVPupilWeekSchedule() throws Exception {
        InputStream stream =  service.PrintCSVPupilWeekSchedule(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printPDFPupilsRating() throws Exception {
        InputStream stream =  service.PrintPDFPupilsRating(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printXLSPupilsRating() throws Exception {
        InputStream stream =  service.PrintXLSPupilsRating(1);
        Assert.assertNotNull(stream);
    }

    @Test
    public void printCSVPupilsRating() throws Exception {
        InputStream stream =  service.PrintCSVPupilsRating(1);
        Assert.assertNotNull(stream);
    }
}