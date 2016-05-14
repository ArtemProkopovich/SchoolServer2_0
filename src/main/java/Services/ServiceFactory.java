package Services;

import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Services.Impl.PrintService;
import Services.Impl.ScheduleService;
import Services.Impl.StudyService;
import Services.Impl.UserService;
import Services.Interfacies.IPrintService;
import Services.Interfacies.IScheduleService;
import Services.Interfacies.IStudyService;
import Services.Interfacies.IUserService;

/**
 * Created by Артем on 08.05.2016.
 */
public class ServiceFactory {

    private ServiceFactory() {}

    public static IPrintService getPrintService(){
        return new PrintService(new UnitOfWork(new MySqlConnection()));
    }

    public static IScheduleService getScheduleService(){
        return new ScheduleService(new UnitOfWork(new MySqlConnection()));
    }

    public static IUserService getUserService(){
        return new UserService(new UnitOfWork(new MySqlConnection()));
    }

    public static IStudyService getStudyService(){
        return new StudyService(new UnitOfWork(new MySqlConnection()));
    }
}
