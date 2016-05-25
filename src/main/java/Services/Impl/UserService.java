package Services.Impl;

import ActionEntities.UserData;
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

    public UserData Login(String login, String password) throws ServiceException {
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
            if (user!=null) {
                UserData userData = new UserData();
                userData.setRole(user.getRole());
                userData.setLogin(user.getLogin());
                userData.setPassword(user.getPassword());
                if (user.getRole().equals(Role.PUPIL)){
                    Pupil pupil = uof.getUserDao().GetPupilByUserId(user.getID());
                    userData.setSurname(pupil.getSurname());
                    userData.setName(pupil.getName());
                    Class cls = uof.getClassDao().Select(pupil.getClassID());
                    userData.setClassGrade(cls.getGrade());
                    userData.setClassLetter(cls.getLetter());
                    userData.setEntityID(pupil.getID());
                }
                else if(user.getRole().equals(Role.TEACHER)) {
                    Teacher teacher = uof.getUserDao().GetTeacherByUserId(user.getID());
                    userData.setName(teacher.getName());
                    userData.setSurname(teacher.getSurname());
                    userData.setType(teacher.getType());
                    userData.setEntityID(teacher.getID());
                }
                return userData;
            }
            else {
                UserData userData = new UserData();
                userData.setLogin(login);
                userData.setPassword(password);
                userData.setRole(Role.GUEST);
                return userData;
            }
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

