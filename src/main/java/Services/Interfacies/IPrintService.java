package Services.Interfacies;

import Entities.Pupil;
import Entities.Subject;
import Entities.Teacher;
import Services.ServiceException;

import java.io.InputStream;

/**
 * Created by Артем on 02.05.2016.
 */
public interface IPrintService {

    //Лист статистики ученика(средние баллы по предметам.)
    InputStream PrintPDFAchivementStatistics(int pupilID) throws ServiceException;
    InputStream PrintXLSAchivementStatistics(int pupilID)throws ServiceException;
    InputStream PrintCSVAchivementStatistics(int pupilID)throws ServiceException;

    //Печать листа благодраности
    InputStream PrintPDFThanksLetter(Pupil pupil, Teacher teacher)throws ServiceException;
    InputStream PrintXLSThanksLetter(Pupil pupil, Teacher teacher)throws ServiceException;
    InputStream PrintCSVThanksLetter(Pupil pupil, Teacher teacher)throws ServiceException;

    //Лист журнала с одним предметом
    InputStream PrintPDFSubjectList(int subjectID)throws ServiceException;
    InputStream PrintXLSSubjectList(int subjectID)throws ServiceException;
    InputStream PrintCSVSubjectList(int subjectID)throws ServiceException;

    //Расписание недели учителя
    InputStream PrintPDFTeacherWeekSchedule(int teacherID)throws ServiceException;
    InputStream PrintXLSTeacherWeekSchedule(int teacherID)throws ServiceException;
    InputStream PrintCSVTeacherWeekSchedule(int teacherID)throws ServiceException;

    //Расписание недели ученика
    InputStream PrintPDFPupilWeekSchedule(int pupilID)throws ServiceException;
    InputStream PrintXLSPupilWeekSchedule(int pupilID)throws ServiceException;
    InputStream PrintCSVPupilWeekSchedule(int pupilID)throws ServiceException;
}
