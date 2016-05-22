package Services.Impl;

import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.Class;
import Entities.*;
import Services.Interfacies.IUserService;
import Services.ServiceException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Артем on 02.05.2016.
 */
public class UserService implements IUserService {

    private IUnitOfWork uof;

    public UserService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public User Login(String login, String password) throws ServiceException {
        if (login==null || login.length()==0)
        {
            throw new ServiceException(new IllegalArgumentException("Login can't be a empty."));
        }
        if (password==null || password.length()==0)
        {
            throw new ServiceException(new IllegalArgumentException("Password can't be a empty."));
        }
        try {
            User user = uof.getUserDao().GetByLoginAndPassword(login, password);
            if (user!=null)
                return user;
            else
                throw new ServiceException("The user with the login and password can not be found");

        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Pupil> GetPupilList() throws ServiceException {
        try{
            return uof.getPupilDao().GetPupilList();
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Pupil AddPupil(Pupil pupil) throws ServiceException {
        try{
            User user =  CreateUserForPupil(pupil);
            int user_id = uof.getUserDao().Insert(user);
            pupil.setUserID(user_id);
            int pupil_id = uof.getPupilDao().Insert(pupil);
            pupil.setID(pupil_id);
            return pupil;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Pupil GetPupilById(int id) throws ServiceException {
        try{
            return uof.getPupilDao().Select(id);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemovePupil(Pupil pupil) throws ServiceException {
        try{
            Pupil dbPupil = uof.getPupilDao().Select(pupil.getID());
            if (dbPupil!=null)
            {
                uof.getPupilDao().Delete(dbPupil.getID());
                uof.getUserDao().Delete(dbPupil.getUserID());
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemovePupil(int id) throws ServiceException {
        try{
            Pupil dbPupil = uof.getPupilDao().Select(id);
            if (dbPupil!=null)
            {
                uof.getPupilDao().Delete(dbPupil.getID());
                uof.getUserDao().Delete(dbPupil.getUserID());
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }

    }

    public Pupil UpdatePupil(Pupil pupil) throws ServiceException {
        try{
            Pupil dbPupil = uof.getPupilDao().Select(pupil.getID());
            if(dbPupil!=null) {
                dbPupil.setName(pupil.getName());
                dbPupil.setSurname(pupil.getSurname());
                uof.getPupilDao().Update(dbPupil);
                return dbPupil;
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
        return null;
    }

    public User CreateUserForPupil(Pupil pupil) throws ServiceException {
        User user = new User();
        user.setRole(Role.PUPIL);
        user.setEmail("");
        user.setLogin(NamesToLogin(pupil.getSurname(),pupil.getName()));
        user.setPassword(PasswordFromLogin(user.getLogin()));
        return user;
    }

    public Teacher AddTeacher(Teacher teacher) throws ServiceException {
        try{
            User user =  CreateUserForTeacher(teacher);
            int user_id = uof.getUserDao().Insert(user);
            teacher.setUserID(user_id);
            int teacher_id = uof.getTeacherDao().Insert(teacher);
            teacher.setID(teacher_id);
            return teacher;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Teacher GetTeacherById(int id) throws ServiceException {
        try{
            return uof.getTeacherDao().Select(id);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemoveTeacher(Teacher teacher) throws ServiceException {
        try{
            Teacher dbTeacher = uof.getTeacherDao().Select(teacher.getID());
            if (dbTeacher!=null)
            {
                uof.getTeacherDao().Delete(dbTeacher.getID());
                uof.getUserDao().Delete(dbTeacher.getUserID());
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemoveTeacher(int id) throws ServiceException {
        try{
            Teacher dbTeacher = uof.getTeacherDao().Select(id);
            if (dbTeacher!=null)
            {
                uof.getTeacherDao().Delete(dbTeacher.getID());
                uof.getUserDao().Delete(dbTeacher.getUserID());
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }

    }

    public Teacher UpdateTeacher(Teacher teacher) throws ServiceException {
        try{
            Teacher dbTeacher = uof.getTeacherDao().Select(teacher.getID());
            if(dbTeacher!=null) {
                dbTeacher.setName(teacher.getName());
                dbTeacher.setSurname(teacher.getSurname());
                dbTeacher.setType(teacher.getType());
                uof.getTeacherDao().Update(dbTeacher);
                return dbTeacher;
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
        return null;
    }

    public List<Teacher> GetTeacherList() throws ServiceException {
        try{
            return uof.getTeacherDao().GetTeacherList();
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public User CreateUserForTeacher(Teacher teacher) throws ServiceException {
        User user = new User();
        user.setRole(Role.TEACHER);
        user.setEmail("");
        user.setLogin(NamesToLogin(teacher.getSurname(),teacher.getName()));
        user.setPassword(PasswordFromLogin(user.getLogin()));
        return user;
    }

    public Class AddClass(Class cls) throws ServiceException {
        try{
            int class_id = uof.getClassDao().Insert(cls);
            cls.setID(class_id);
            return cls;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Class GetClassById(int id) throws ServiceException {
        try{
            return uof.getClassDao().Select(id);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemoveClass(Class cls) throws ServiceException {
        try {
            if (uof.getClassDao().Select(cls.getID()) != null)
                uof.getClassDao().Delete(cls.getID());
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemoveClass(int id) throws ServiceException {
        try {
            if (uof.getClassDao().Select(id) != null)
                uof.getClassDao().Delete(id);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public Class UpdateClass(Class cls) throws ServiceException {
        try {
            uof.getClassDao().Update(cls);
            return cls;
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public List<Class> GetClassList() throws ServiceException {
        try{
            return uof.getClassDao().GetClassList();
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }


    private String NamesToLogin(String surname, String name){
        String sn = SymbolsToEngAlphabet(surname);
        String n = SymbolsToEngAlphabet(name);
        if (sn.length()>5)
            sn=sn.substring(0,4);
        if (n.length()>5)
            n=n.substring(0,4);
        return sn+n+sn.hashCode()*n.hashCode();
    }

    private String PasswordFromLogin(String login){
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            byte[] data = login.getBytes();
            m.update(data, 0, data.length);
            BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i).substring(0,10);
        }
        catch (NoSuchAlgorithmException e) {
        }
        return ((Integer)login.hashCode()).toString();
    }

    private String SymbolsToEngAlphabet(String str)
    {
        StringBuilder result = new StringBuilder();
        for(char c:str.toLowerCase().toCharArray()) {
            if (c>'a'&&c<'z')
                result.append(c);
            else
                result.append(c%('z'-'a')+'a');
        }
        return result.toString();
    }
}

