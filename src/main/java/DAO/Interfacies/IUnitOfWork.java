package DAO.Interfacies;

/**
 * Created by Артем on 02.05.2016.
 */
public interface IUnitOfWork {

    IClassDao getClassDao();
    ILessonDao getLessonDao();
    IMarkDao getMarkDao();
    IPupilDao getPupilDao();
    ISubjectDao getSubjectDao();
    ITeacherDao getTeacherDao();
    IUserDao getUserDao();

    void closeConnection();
}
