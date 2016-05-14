package ServiceEntities;

import Entities.Class;
import Entities.Lesson;
import Entities.Subject;

/**
 * Created by Артем on 05.05.2016.
 */
public class ScheduleTeacherLesson {
    private Lesson lesson;
    private Subject subject;
    private Class cls;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
