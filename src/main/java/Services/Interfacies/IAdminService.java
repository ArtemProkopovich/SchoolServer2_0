package Services.Interfacies;

import ActionEntities.PupilUser;
import ActionEntities.TeacherUser;
import Entities.Class;
import Entities.Pupil;
import Entities.Teacher;
import Entities.User;
import Services.ServiceException;

import java.util.List;

/**
 * Created by Артем on 25.05.2016.
 */
public interface IAdminService {

    List<PupilUser> GetPupilListByClassID(int classID) throws ServiceException;

    List<PupilUser> GetPupilList() throws ServiceException;

    void AddPupil(Pupil pupil, User user) throws ServiceException;

    PupilUser GetPupilById(int id) throws ServiceException;

    void RemovePupil(int pupilID) throws ServiceException;

    void UpdatePupil(Pupil pupil, User user) throws ServiceException;

    void AddTeacher(Teacher teacher, User user) throws ServiceException;

    TeacherUser GetTeacherById(int id) throws ServiceException;

    void RemoveTeacher(int id) throws ServiceException;

    void UpdateTeacher(Teacher teacher, User user) throws ServiceException;

    List<TeacherUser> GetTeacherList() throws ServiceException;

    void AddClass(Class cls) throws ServiceException;

    Class GetClassById(int id) throws ServiceException;

    void RemoveClass(int id) throws ServiceException;

    void UpdateClass(Class cls) throws ServiceException;

    List<Class> GetClassList() throws ServiceException;
}
