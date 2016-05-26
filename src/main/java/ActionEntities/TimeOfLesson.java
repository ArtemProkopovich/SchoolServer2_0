package ActionEntities;

import java.sql.Time;

/**
 * Created by Артем on 23.05.2016.
 */
public class TimeOfLesson {
    public static String NumberToTime(int number){
        StringBuilder builder = new StringBuilder();
        if (number>0&&number<13) {
            return builder.append(number + 7).append(":00 - ").append(number + 7).append(":45").toString();
        }
        return "0:00 - 0:00";
    }
}
