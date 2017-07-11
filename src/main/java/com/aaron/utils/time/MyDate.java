package com.aaron.utils.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate implements Serializable{
    private Date mDate;

    public MyDate() {
        mDate = new Date(System.currentTimeMillis());
    }

    public MyDate(String dateString) {
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMdd");

        try {
            mDate = sdFormatter.parse(dateString);
        } catch (ParseException e) {
            mDate = new Date(System.currentTimeMillis());
        }
    }

    /**
     * update date to current.
     */
    public void update() {
        mDate = new Date(System.currentTimeMillis());
    }

    /**
     * return time in format.
     * @return time like "yyyyMMdd"
     */
    public String format() {
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMdd");
        String string = sdFormatter.format(mDate);

        return string;
    }

    /**
     * get day of MyDate.
     * @return from 1 to 31.
     */
    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * get month of MyDate.
     * @return from 0 to 11.
     */
    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.MONTH);
    }

    /**
     * get year of MyDate.
     * @return year number.
     */
    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * get different days.
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public int getDateDifferent(MyDate diffMyDate) {
        return (int) (mDate.getTime() - diffMyDate.mDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    public MyDate getTimeAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, days);

        MyDate afterDate = new MyDate();
        afterDate.mDate = calendar.getTime();

        return afterDate;
    }

    public MyDate getTimeBeforeDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, -1 * days);

        MyDate afterDate = new MyDate();
        afterDate.mDate = calendar.getTime();

        return afterDate;
    }
}
