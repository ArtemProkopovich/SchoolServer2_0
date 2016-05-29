package ActionEntities;

import Entities.Class;

/**
 * Created by Артем on 29.05.2016.
 */


public class ActionClass {
    private int classID;
    private int classGrade;
    private String classLetter;

    public ActionClass(Class cls)
    {
        classID = cls.getID();
        classGrade = cls.getGrade();
        classLetter = cls.getLetter();
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(int classGrade) {
        this.classGrade = classGrade;
    }

    public String getClassLetter() {
        return classLetter;
    }

    public void setClassLetter(String classLetter) {
        this.classLetter = classLetter;
    }
}
