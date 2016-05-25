package Services.Interfacies;

import ActionEntities.UserData;
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

    UserData Login(String login, String password) throws ServiceException;
}
