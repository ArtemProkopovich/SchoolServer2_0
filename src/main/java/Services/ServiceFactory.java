package Services;

import DAO.Impl.UnitOfWork;
import DAO.MySqlConnection;
import Services.Impl.*;
import Services.Interfacies.*;

/**
 * Created by Артем on 08.05.2016.
 */
public class ServiceFactory {

    private ServiceFactory() {}

    private static MySqlConnection connection = new MySqlConnection("jdbc:mysql://localhost:3306/school_test_db?useSSL=true", "root", "root");

    public static IPrintService getPrintService() {
        return new PrintService(new UnitOfWork(connection));
    }

    public static IScheduleService getScheduleService(){
        return new ScheduleService(new UnitOfWork(connection));
    }

    public static IUserService getUserService(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return new UserService(new UnitOfWork(connection));
        }
        catch (Exception ex){}
        return null;
    }

    public static IStudyService getStudyService(){
        return new StudyService(new UnitOfWork(connection));
    }

    public static IAdminService getAdminService(){
        return new AdminService(new UnitOfWork(connection));
    }
}
