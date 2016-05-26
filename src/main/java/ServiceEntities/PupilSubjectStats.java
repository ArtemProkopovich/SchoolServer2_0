package ServiceEntities;

import Entities.Mark;
import Entities.Pupil;
import Entities.Subject;

import java.util.List;

/**
 * Created by Артем on 26.05.2016.
 */
public class PupilSubjectStats {
    Subject subject;
    List<Mark> markList;
    double averageMark;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Mark> getMarkList() {
        return markList;
    }

    public void setMarkList(List<Mark> markList) {
        this.markList = markList;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }
}
