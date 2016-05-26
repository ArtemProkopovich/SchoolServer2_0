package Services.Impl;

import ActionEntities.PupilUser;
import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Entities.Pupil;
import Entities.Role;
import Entities.User;
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

    @Test
    public void addPupil() throws Exception {
        Pupil p = GetTestPupil();
        User u = GetTestUser();
        service.AddPupil(p, u);
        User dbUser = uof.getUserDao().GetByLoginAndPassword(u.getLogin(), u.getPassword());
        Assert.assertNotNull(dbUser);
        Assert.assertTrue(dbUser.getRole().equals(Role.PUPIL));
        Pupil dbPupil = uof.getUserDao().GetPupilByUserId(dbUser.getID());
        Assert.assertNotNull(dbPupil);
        Assert.assertTrue(dbPupil.getName().equals(p.getName()) && dbPupil.getSurname().equals(p.getSurname()));
        uof.getPupilDao().Delete(dbPupil.getID());
        uof.getUserDao().Delete(dbUser.getID());
    }

    @Test
    public void removePupil() throws Exception {
        Pupil p = GetTestPupil();
        User u = GetTestUser();
        service.AddPupil(p, u);
        User dbUser = uof.getUserDao().GetByLoginAndPassword(u.getLogin(), u.getPassword());
        Pupil dbPupil = uof.getUserDao().GetPupilByUserId(dbUser.getID());
        service.RemovePupil(dbPupil.getID());
    }

    @Test
    public void updatePupil() throws Exception {
        Pupil p = GetTestPupil();
        User u = GetTestUser();
        service.AddPupil(p, u);
        User dbUser = uof.getUserDao().GetByLoginAndPassword(u.getLogin(), u.getPassword());
        Pupil dbPupil = uof.getUserDao().GetPupilByUserId(dbUser.getID());
        dbUser.setLogin("login1");
        dbPupil.setName("Test1");
        service.UpdatePupil(dbPupil, dbUser);
        Pupil dbUpPupil = uof.getPupilDao().Select(dbPupil.getID());
        User dbUpUser = uof.getUserDao().Select(dbUser.getID());
        Assert.assertNotNull(dbUpPupil);
        Assert.assertNotNull(dbUpUser);
        Assert.assertTrue(dbUpPupil.getName().equals("Test1"));
        Assert.assertTrue(dbUpUser.getLogin().equals("login1"));
        service.RemovePupil(dbUpPupil.getID());
    }

    private Pupil GetTestPupil(){
        Pupil p = new Pupil();
        p.setName("Test");
        p.setSurname("Test");
        return p;
    }

    private User GetTestUser(){
        User u = new User();
        u.setLogin("login");
        u.setPassword("password");
        u.setRole(Role.PUPIL);
        return u;
    }
}