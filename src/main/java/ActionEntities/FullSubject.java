package ActionEntities;

import Entities.Class;
import Entities.Subject;
import Entities.Teacher;

/**
 * Created by Артем on 25.05.2016.
 */
public class FullSubject {
    private int subjectID;
    private String name;
    private int lessonCount;
    private int classID;
    private int classGrade;
    private String classLetter;
    private int teacherID;
    private String teacher;

    public FullSubject(Subject subject , Teacher teacher, Class cls) {
        subjectID = subject.getID();
        name = subject.getName();
        lessonCount = subject.getLessonCount();
        if (cls != null) {
            classID = cls.getID();
            classGrade = cls.getGrade();
            classLetter = cls.getLetter();
        }
        if (teacher != null) {
            teacherID = teacher.getID();
            this.teacher = teacher.getSurname() + " " + teacher.getName();
        }
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
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

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
