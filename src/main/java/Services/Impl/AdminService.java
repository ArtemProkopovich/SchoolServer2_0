package Services.Impl;

import ActionEntities.PupilUser;
import ActionEntities.TeacherUser;
import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.*;
import Entities.Class;
import Services.Interfacies.IAdminService;
import Services.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 25.05.2016.
 */
public class AdminService implements IAdminService {

    private IUnitOfWork uof;

    public AdminService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public List<PupilUser> GetPupilListByClassID(int classID) throws ServiceException {
        try {
            List<Pupil> pupils = uof.getClassDao().GetClassPupilList(classID);
            List<PupilUser> result = new ArrayList<PupilUser>();
            for (Pupil pupil : pupils) {
                User user = uof.getUserDao().Select(pupil.getUserID());
                result.add(new PupilUser(pupil, user));
            }
            return result;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<PupilUser> GetPupilList() throws ServiceException {
        try {
            List<Pupil> pupils = uof.getPupilDao().GetPupilList();
            List<PupilUser> result = new ArrayList<PupilUser>();
            for (Pupil pupil : pupils) {
                User user = uof.getUserDao().Select(pupil.getUserID());
                result.add(new PupilUser(pupil, user));
            }
            return result;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void AddPupil(Pupil pupil, User user) throws ServiceException {
        try {
            int user_id = uof.getUserDao().Insert(user);
            pupil.setUserID(user_id);
            int pupil_id = uof.getPupilDao().Insert(pupil);
            pupil.setID(pupil_id);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public PupilUser GetPupilById(int id) throws ServiceException {
        try {
            Pupil p = uof.getPupilDao().Select(id);
            User u = uof.getUserDao().Select(p.getID());
            return new PupilUser(p, u);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void RemovePupil(int pupilID) throws ServiceException {
        try{
            Pupil dbPupil = uof.getPupilDao().Select(pupilID);
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

    public void UpdatePupil(Pupil pupil, User user) throws ServiceException {
        try {
            Pupil dbPupil = uof.getPupilDao().Select(pupil.getID());
            User dbUser = dbUser = uof.getUserDao().Select(dbPupil.getUserID());
            if (dbPupil != null) {
                dbPupil.setName(pupil.getName());
                dbPupil.setSurname(pupil.getSurname());
                uof.getPupilDao().Update(dbPupil);
                dbUser.setLogin(user.getLogin());
                dbUser.setPassword(user.getPassword());
                dbUser.setEmail(user.getEmail());
                uof.getUserDao().Update(dbUser);
            }
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
    }

    public void AddTeacher(Teacher teacher, User user) throws ServiceException {
        try{
            int user_id = uof.getUserDao().Insert(user);
            teacher.setUserID(user_id);
            int teacher_id = uof.getTeacherDao().Insert(teacher);
            teacher.setID(teacher_id);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public TeacherUser GetTeacherById(int id) throws ServiceException {
        try {
            Teacher teacher = uof.getTeacherDao().Select(id);
            User user = uof.getUserDao().Select(teacher.getUserID());
            return new TeacherUser(teacher, user);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void RemoveTeacher(int id) throws ServiceException {
        try {
            Teacher dbTeacher = uof.getTeacherDao().Select(id);
            if (dbTeacher != null) {
                uof.getTeacherDao().Delete(dbTeacher.getID());
                uof.getUserDao().Delete(dbTeacher.getUserID());
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void UpdateTeacher(Teacher teacher, User user) throws ServiceException {
        try {
            Teacher dbTeacher = uof.getTeacherDao().Select(teacher.getID());
            User dbUser = uof.getUserDao().Select(dbTeacher.getUserID());
            if (dbTeacher != null) {
                dbTeacher.setName(teacher.getName());
                dbTeacher.setSurname(teacher.getSurname());
                dbTeacher.setType(teacher.getType());
                uof.getTeacherDao().Update(dbTeacher);
                dbUser.setLogin(user.getLogin());
                dbUser.setPassword(user.getPassword());
                dbUser.setEmail(user.getEmail());
                uof.getUserDao().Update(dbUser);
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<TeacherUser> GetTeacherList() throws ServiceException {
        try {
            List<Teacher> teachers = uof.getTeacherDao().GetTeacherList();
            List<TeacherUser> result = new ArrayList<TeacherUser>();
            for (Teacher teacher : teachers) {
                User user = uof.getUserDao().Select(teacher.getUserID());
                result.add(new TeacherUser(teacher, user));
            }
            return result;
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public void AddClass(Class cls) throws ServiceException {
        try{
            int class_id = uof.getClassDao().Insert(cls);
            cls.setID(class_id);
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

    public void UpdateClass(Class cls) throws ServiceException {
        try {
            uof.getClassDao().Update(cls);
        } catch (DAOException ex) {
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
}
