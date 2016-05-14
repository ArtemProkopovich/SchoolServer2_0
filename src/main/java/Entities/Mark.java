package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class Mark implements IEntity {
    private int ID=0;
    private int mark;
    private int pupilID;
    private int lessonID;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID=value;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }
}
