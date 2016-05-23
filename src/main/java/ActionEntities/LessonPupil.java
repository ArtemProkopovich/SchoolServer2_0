package ActionEntities;

import Entities.Mark;
import Entities.Pupil;

/**
 * Created by Артем on 23.05.2016.
 */
public class LessonPupil {
    public int getID() {
        return pupilID;
    }

    public void setID(int pupilID) {
        this.pupilID = pupilID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int pupilID;
    private String name;
    private String surname;
    private int mark;
    private int number;

    public LessonPupil(Pupil pupil, Mark _mark, int number) {
        pupilID = pupil.getID();
        name = pupil.getName();
        surname = pupil.getSurname();
        mark = _mark.getMark();
        this.number = number;
    }
}
