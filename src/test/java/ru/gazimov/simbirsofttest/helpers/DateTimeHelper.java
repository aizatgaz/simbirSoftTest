package ru.gazimov.simbirsofttest.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeHelper {

    public static String formatDateTime(String dateTime) {
        // Форматируем дату-время
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, inputFormatter);
        return parsedDateTime.format(outputFormatter);
    }

}
