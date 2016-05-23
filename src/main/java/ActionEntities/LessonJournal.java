package ActionEntities;

import Entities.Lesson;
import Entities.Mark;
import Entities.Pupil;
import Entities.Subject;
import Entities.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Артем on 23.05.2016.
 */
public class LessonJournal {
    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(int auditorium) {
        this.auditorium = auditorium;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
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

    public List<LessonPupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<LessonPupil> pupils) {
        this.pupils = pupils;
    }

    private int lessonID;
    private String timeRange;
    private String subject;
    private int auditorium;
    private String homework;
    private int classGrade;
    private String classLetter;
    List<LessonPupil> pupils;

    public LessonJournal(Map<Pupil, Mark> pupilMarkMap, Lesson lesson, Subject _subject, Class cls) {
        lessonID = lesson.getID();
        timeRange = TimeOfLesson.NumberToTime(lesson.getScheduleNumber());
        subject = _subject.getName();
        auditorium = lesson.getRoom();
        homework = lesson.getHomework();
        classGrade = cls.getGrade();
        classLetter = cls.getLetter();
        pupils = new ArrayList<LessonPupil>();
        Set<Map.Entry<Pupil, Mark>> set = pupilMarkMap.entrySet();
        int i=1;
        for (Map.Entry<Pupil, Mark> s : set) {
            pupils.add(new LessonPupil(s.getKey(), s.getValue(), i));
            i++;
        }
    }
}
