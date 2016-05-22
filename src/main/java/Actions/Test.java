package Actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Евгений on 30.04.2016.
 */
public class Test extends ActionSupport {
    private String name;

    public String execute() throws Exception {
        return "success";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
