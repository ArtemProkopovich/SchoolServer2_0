package Services.Impl;

import ActionEntities.PupilUser;
import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Артем on 25.05.2016.
 */
public class AdminServiceTest {

    static MySqlConnection connection;
    static UnitOfWork uof;
    static AdminService service;
    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        uof = new UnitOfWork(connection);
        service = new AdminService(uof);
    }

    @Test
    public void getPupilListByClassID() throws Exception {
        List<PupilUser> result = service.GetPupilListByClassID(-1);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }
}