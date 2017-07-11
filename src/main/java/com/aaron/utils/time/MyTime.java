package com.aaron.utils.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTime implements Serializable{
    private Date mDate;

    public MyTime() {
        mDate = new Date(System.currentTimeMillis());
    }

    public MyTime(String milliString) {
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        try {
            mDate = sdFormatter.parse(milliString);
        } catch (ParseException e) {
            mDate = new Date(System.currentTimeMillis());
        }
    }

    public void update() {
        mDate = new Date(System.currentTimeMillis());
    }

    /**
     * return time in format of "yyyyMMddHHmmssSSS"
     */
    public String format() {
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String string = sdFormatter.format(mDate);

        return string;
    }

    /**
     * get second of MyDate.
     * @return from 0 to 59.
     */
    public int getSecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.SECOND);
    }

    /**
     * get minute of MyDate.
     * @return from 0 to 59.
     */
    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.MINUTE);
    }

    /**
     * get hour of MyDate.
     * @return from 0 to 23.
     */
    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * get different milli seconds.
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public long getMillisDifferent(MyTime diffMyTime) {
        return (mDate.getTime() - diffMyTime.mDate.getTime());
    }

    /**
     * get different seconds.
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public int getSecondsDifferent(MyTime diffMyTime) {
        return (int) (mDate.getTime() - diffMyTime.mDate.getTime()) / 1000;
    }

    /**
     * get different minutes.
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public int getMinutesDifferent(MyTime diffMyTime) {
        return (int) (mDate.getTime() - diffMyTime.mDate.getTime()) / (1000 * 60);
    }

    /**
     * get different hours.
     *
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public int getHoursDifferent(MyTime diffMyTime) {
        return (int) (mDate.getTime() - diffMyTime.mDate.getTime()) / (1000 * 60 * 60);
    }

    /**
     * get different days.
     * @return if mDate is earlier, return negative number.
     * if mDate is after, return positive number.
     */
    public int getDaysDifferent(MyTime diffMyTime) {
        return (int) (mDate.getTime() - diffMyTime.mDate.getTime()) / (1000 * 60 * 60 * 24);
    }


    public MyTime getTimeAfterMillis(int millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.MILLISECOND, millis);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeAfterSeconds(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.SECOND, seconds);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeAfterMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.MINUTE, minutes);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeAfterHours(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.HOUR, hours);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, days);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }


    public MyTime getTimeBeforeMillis(int millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.roll(Calendar.MILLISECOND, -1 * millis);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();

        return afterMyTime;
    }

    public MyTime getTimeBeforeSeconds(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.SECOND, -1 * seconds);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeBeforeMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.MINUTE, -1 * minutes);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeBeforeHours(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.HOUR, -1 * hours);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }

    public MyTime getTimeBeforeDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, -1 * days);

        MyTime afterMyTime = new MyTime();
        afterMyTime.mDate = calendar.getTime();
        return afterMyTime;
    }
}
