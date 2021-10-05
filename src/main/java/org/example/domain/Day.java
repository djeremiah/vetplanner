package org.example.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Day {

    private int dayOfYear;
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("E, MMM d", Locale.ENGLISH);

    public Day(){}

    public Day(int dayOfYear){
        this.dayOfYear = dayOfYear;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public String getDateString() {
        return DAY_FORMATTER.format(toDate());
    }

    public LocalDate toDate() {
        return LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear);
    }

    @Override
    public String toString() {
        return getDateString();
    }
}
