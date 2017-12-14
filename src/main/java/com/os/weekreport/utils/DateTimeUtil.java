package com.os.weekreport.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    private final String DATE_FORMAT = "yyyy-MM-dd";

    public String lastMonday() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(getFirstDayOfWeek(calendar.getTime(), 2));
    }

    public String lastFirday() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 9);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(getFirstDayOfWeek(calendar.getTime(), 6));//这是从上周日开始数的到本周五为6

    }

    public String thisMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(cal.getTime());
    }

    public  String getToday(){
        Calendar calendar = Calendar.getInstance();
        return dateToString(calendar.getTime());
    }

    public String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date.getTime());

    }

    private Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.setFirstDayOfWeek(firstDayOfWeek);//设置一星期的第一天是哪一天
        cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);//指示一个星期中的某天
        cal.set(Calendar.HOUR_OF_DAY, 0);//指示一天中的小时。HOUR_OF_DAY 用于 24 小时制时钟。例如，在 10:04:15.250 PM 这一时刻，HOUR_OF_DAY 为 22。
        cal.set(Calendar.MINUTE, 0);//指示一小时中的分钟。例如，在 10:04:15.250 PM 这一时刻，MINUTE 为 4。
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
