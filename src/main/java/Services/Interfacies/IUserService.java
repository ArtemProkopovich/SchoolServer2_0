package Services.Interfacies;

import Entities.Class;
import Entities.Pupil;
import Entities.Teacher;
import Entities.User;
import Services.ServiceException;

import java.util.List;

/**
 * Created by Артем on 02.05.2016.
 */
public interface IUserService {

    User Login(String login, String password) throws ServiceException;

    List<Pupil> GetPupilList() throws ServiceException;
    Pupil AddPupil(Pupil pupil) throws ServiceException;
    void RemovePupil(Pupil pupil) throws ServiceException;
    Pupil UpdatePupil(Pupil pupil) throws ServiceException;
    User CreateUserForPupil(Pupil pupil) throws ServiceException;

    Teacher AddTeacher(Teacher teacher) throws ServiceException;
    void RemoveTeacher(Teacher teacher) throws ServiceException;
    Teacher UpdateTeacher(Teacher teacher) throws ServiceException;
    List<Teacher> GetTeacherList() throws ServiceException;
    User CreateUserForTeacher(Teacher teacher) throws ServiceException;

    Class AddClass(Class cls) throws ServiceException;
    void RemoveClass(Class cls) throws ServiceException;
    Class UpdateClass(Class cls) throws ServiceException;
    List<Class> GetClassList() throws ServiceException;
}
