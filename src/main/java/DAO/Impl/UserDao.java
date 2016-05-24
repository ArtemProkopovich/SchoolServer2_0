package DAO.Impl;

import DAO.DAOException;
import DAO.Interfacies.IUserDao;
import DAO.MySqlConnection;
import Entities.Pupil;
import Entities.Role;
import Entities.Teacher;
import Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 01.05.2016.
 */
public class UserDao implements IUserDao {

    private final static String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? && password = ?";
    private final static String INSERT_USER = "INSERT INTO `users` (`login`, `password`, `email`, `role_id`) " +
            "VALUES (?, ?, ?, ?)";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM users " +
            "JOIN roles ON users.role_id=roles.role_id WHERE user_id = ?";
    private final static String SELECT_ALL_USERS = "SELECT * FROM users " +
            "JOIN roles ON users.role_id=roles.role_id";
    private final static String DELETE_USER="DELETE FROM `users` WHERE `user_id`= ?";
    private final static String UPDATE_USER = "UPDATE `users`" +
            "SET `login`= ?, `password`= ?, `email`= ?, `role_id`= ? WHERE `user_id`= ?";
    private final String SELECT_PUPIL_BY_USER_ID = "SELECT * FROM pupils WHERE user_id=?";
    private final String SELECT_TEACHER_BY_USER_ID = "SELECT * FROM teachers WHERE user_id=?";
    private final static String SELECT_ROLE_ID_BY_TYPE = "SELECT * FROM `roles` WHERE type=?";

    private MySqlConnection connection;
    public UserDao(MySqlConnection connection){
        this.connection = connection;
    }

    public User GetByLoginAndPassword(String login, String password) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD);
            st.setString(1,login);
            st.setString(2,password);
            ResultSet set = st.executeQuery();
            if (set.next()){
                return Select(set.getInt("user_id"));
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

    public List<User> GetUserList() throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_ALL_USERS);
            ResultSet set = st.executeQuery();
            ArrayList<User> result = new ArrayList<User>();
            while (set.next()){
                result.add(ResultSetToUserWithRole(set));
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

    public Pupil GetPupilByUserId(int id) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_PUPIL_BY_USER_ID);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                return PupilDao.ResultSetToPupil(set);
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

    public Teacher GetTeacherByUserId(int id) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement stP = cn.prepareStatement(SELECT_TEACHER_BY_USER_ID);
            stP.setInt(1, id);
            ResultSet set = stP.executeQuery();
            if (set.next()) {
                return TeacherDao.ResultSetToTeacher(set);
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

    public int Insert(User item) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(INSERT_USER,PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1,item.getLogin());
            st.setString(2,item.getPassword());
            st.setString(3,item.getEmail());
            st.setInt(4,GetRoleIdByType(item.getRole().toString().toLowerCase()));
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

    public User Select(int id) throws DAOException {
        Connection cn = null;
        try{
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_USER_BY_ID);
            st.setInt(1,id);
            ResultSet set = st.executeQuery();
            if (set.next()){
                return ResultSetToUserWithRole(set);
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

    public void Update(User item) throws DAOException {
        Connection cn = null;
        try {
            cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(UPDATE_USER);
            st.setString(1, item.getLogin());
            st.setString(2, item.getPassword());
            st.setString(3, item.getEmail());
            st.setInt(4, GetRoleIdByType(item.getRole().toString().toLowerCase()));
            st.setInt(5, item.getID());
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
            PreparedStatement st = cn.prepareStatement(DELETE_USER);
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

    public static User ResultSetToUserWithRole(ResultSet set) throws SQLException {
        User user = new User();
        user.setID(set.getInt("user_id"));
        user.setLogin(set.getString("login"));
        user.setPassword(set.getString("password"));
        user.setEmail(set.getString("email"));
        user.setRole(Role.valueOf(set.getString("type").toUpperCase()));
        return user;
    }

    public static User ResultSetToUser(ResultSet set) throws SQLException {

        User user = new User();
        user.setID(set.getInt("user_id"));
        user.setLogin(set.getString("login"));
        user.setPassword(set.getString("password"));
        user.setEmail(set.getString("email"));
        return user;
    }

    private int GetRoleIdByType(String type) throws SQLException{
        try {
            Connection cn = connection.getConnection();
            PreparedStatement st = cn.prepareStatement(SELECT_ROLE_ID_BY_TYPE);
            st.setString(1, type);
            ResultSet set =  st.executeQuery();
            if (set.next())
                return set.getInt("role_id");
        }
        catch (SQLException ex) {
            throw ex;
        }
        return 0;
    }
}
