package uitls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String GSON_DATE_FORMATTER = "MMM d, yyyy h:mm:ss a";

    /**
     * java.util.Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return LocalDate.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Gson英文环境序列化日期字符串 反解析为 java.util.Date
     *
     * @param datetime
     * @return
     */
    public static Date parseGsonDateTime(String datetime) {
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(GSON_DATE_FORMATTER, Locale.ENGLISH));
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) throws ParseException {
        String dateString = "Aug 7, 2023 7:51:27 PM";
        Date date = parseGsonDateTime(dateString);
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(outputFormatter.format(date));
    }

}