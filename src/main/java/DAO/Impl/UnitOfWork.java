package DAO.Impl;

import DAO.Interfacies.*;
import DAO.MySqlConnection;

/**
 * Created by Артем on 02.05.2016.
 */
public class UnitOfWork implements IUnitOfWork {

    MySqlConnection connection;
    IClassDao classDao;
    ILessonDao lessonDao;
    IMarkDao markDao;
    IPupilDao pupilDao;
    ISubjectDao subjectDao;
    ITeacherDao teacherDao;
    IUserDao userDao;

    public UnitOfWork(MySqlConnection connection)
    {
        this.connection = connection;
        classDao = new ClassDao(this.connection);
        lessonDao = new LessonDao(this.connection);
        markDao = new MarkDao(this.connection);
        pupilDao = new PupilDao(this.connection);
        subjectDao = new SubjectDao(this.connection);
        teacherDao = new TeacherDao(this.connection);
        userDao = new UserDao(this.connection);
    }

    public IClassDao getClassDao() {
        return classDao;
    }

    public ILessonDao getLessonDao()
    {
        return lessonDao;
    }

    public IMarkDao getMarkDao() {
        return markDao;
    }

    public IPupilDao getPupilDao() {
        return pupilDao;
    }

    public ISubjectDao getSubjectDao()
    {
        return subjectDao;
    }

    public ITeacherDao getTeacherDao() {
        return teacherDao;
    }

    public IUserDao getUserDao(){
        return userDao;
    }

    public void closeConnection() {
        if (connection!=null)
            connection.closeConnection();
    }
}
