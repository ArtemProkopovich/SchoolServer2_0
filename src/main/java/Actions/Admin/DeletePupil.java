package Actions.Admin;

import Services.Interfacies.IUserService;
import Services.ServiceException;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Артем on 08.05.2016.
 */
public class DeletePupil extends ActionSupport {
    public int id;
    private IUserService userService = ServiceFactory.getUserService();

    public String execute() throws Exception {
        try {
            if (id!=0) {
                userService.RemovePupil(id);
                return SUCCESS;
            }
            return ERROR;

        } catch (ServiceException ex) {
            return ERROR;
        }catch (Exception ex) {
            return ERROR;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
