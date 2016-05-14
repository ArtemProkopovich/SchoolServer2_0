package Entities;

/**
 * Created by Артем on 01.05.2016.
 */
public class Class implements IEntity {
    private int ID;
    private int grade;
    private String letter;

    public int getID() {
        return ID;
    }

    public void setID(int value) {
        ID=value;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getName() {
        return grade + "\"" + letter + "\"";
    }
}
