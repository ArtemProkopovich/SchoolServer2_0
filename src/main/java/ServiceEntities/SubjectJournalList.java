package ServiceEntities;

import Entities.Class;
import Entities.*;

import java.util.List;

/**
 * Created by Артем on 05.05.2016.
 */
public class SubjectJournalList {

    private Subject subject;
    private Teacher teacher;
    private Class cls;
    private List<Lesson> lessonList;
    private List<Pupil> pupilList;
    private List<List<Mark>> marksList;

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Pupil> getPupilList() {
        return pupilList;
    }

    public void setPupilList(List<Pupil> pupilList) {
        this.pupilList = pupilList;
    }

    public List<List<Mark>> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<List<Mark>> marksList) {
        this.marksList = marksList;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }
}
