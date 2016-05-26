package ServiceEntities;

import Entities.Pupil;

import java.util.List;

/**
 * Created by Артем on 26.05.2016.
 */
public class PupilStats {
    Pupil pupil;
    List<PupilSubjectStats> statsList;
    double averageMark;

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public List<PupilSubjectStats> getStatsList() {
        return statsList;
    }

    public void setStatsList(List<PupilSubjectStats> statsList) {
        this.statsList = statsList;
    }
}
