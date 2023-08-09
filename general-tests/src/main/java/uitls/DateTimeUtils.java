package uitls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final String GSON_DATE_FORMATTER = "MMM d, yyyy h:mm:ss a";

    /**
     * java.util.Date 转 java.time.LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * java.time.LocalDate 转 java.util.Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return new Date(instant.toEpochMilli());
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

        System.out.println(outputFormatter.format(localDateToDate(LocalDate.now().plusDays(-14))));
        System.out.println(outputFormatter.format(localDateToDate(LocalDate.now())));

        System.out.println(dateToLocalDateTime(new Date()));

    }

}