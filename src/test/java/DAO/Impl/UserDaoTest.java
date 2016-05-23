package DAO.Impl;

import DAO.MySqlConnection;
import Entities.Pupil;
import Entities.Role;
import Entities.Teacher;
import Entities.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Артем on 22.05.2016.
 */
public class UserDaoTest {

    static MySqlConnection connection;
    static UserDao dao;

    static {
        connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");
        dao = new UserDao(connection);
    }

    @Test
    public void getByLoginAndPassword() throws Exception {
        Assert.assertNotNull(dao.GetByLoginAndPassword("admin","admin"));
    }

    @Test
    public void getUserList() throws Exception {
        List<User> list = dao.GetUserList();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void getPupilByUserId() throws Exception {
        Pupil pupil = dao.GetPupilByUserId(2);
        Assert.assertNotNull(pupil);
        Assert.assertTrue(pupil.getName().equals("Ivan") && pupil.getUserID() == 2);
    }

    @Test
    public void getTeacherByUserId() throws Exception {
        Teacher teacher = dao.GetTeacherByUserId(4);
        Assert.assertNotNull(teacher);
        Assert.assertTrue(teacher.getName().equals("Petr") && teacher.getUserID() == 4);
    }

    @Test
    public void insert() throws Exception {
        User user = GetTestUser();
        int id = dao.Insert(user);
        Assert.assertTrue(id > 0);
        User dbUser = dao.Select(id);
        Assert.assertNotNull(dbUser);
        Assert.assertTrue(dbUser.getLogin().equals(user.getLogin()) && dbUser.getRole().equals(user.getRole()));
        dao.Delete(id);
    }

    @Test
    public void select() throws Exception {
        User dbUser = dao.Select(1);
        Assert.assertNotNull(dbUser);
        Assert.assertTrue(dbUser.getLogin().equals("admin") && dbUser.getRole().equals(Role.ADMIN));
    }

    @Test
    public void update() throws Exception {
        User nUser = GetTestUser();
        int id = dao.Insert(nUser);
        nUser.setID(id);
        nUser.setLogin("update_admin");
        dao.Update(nUser);
        User dbUser = dao.Select(id);
        Assert.assertTrue(dbUser.getLogin().equals("update_admin")&&dbUser.getPassword().equals(nUser.getPassword()));
        dao.Delete(id);
    }

    @Test
    public void delete() throws Exception {
        User user = GetTestUser();
        int id = dao.Insert(user);
        dao.Delete(id);
        User dbUser = dao.Select(id);
        Assert.assertNull(dbUser);
    }

    public User GetTestUser(){
        User user = new User();
        user.setLogin("admin1");
        user.setPassword("admin1");
        user.setRole(Role.ADMIN);
        user.setEmail("admin2@mail.com");
        return user;
    }

}