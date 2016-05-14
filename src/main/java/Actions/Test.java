package Actions;

/**
 * Created by Евгений on 30.04.2016.
 */
public class Test {
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
