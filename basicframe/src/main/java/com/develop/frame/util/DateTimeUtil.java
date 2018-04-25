package com.develop.frame.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sam on 2017/11/29.
 */

public class DateTimeUtil {

    private DateTimeUtil(){
        throw new UnsupportedOperationException("u can't instantiate me ...");
    }


    private static final DateFormat DEFAULT_FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * 获取当前Date
     * @return Date类型时间
     */
    public static Date getNowDate(){
        return new Date();
    }


    public static String getFormatDate(Date date){
        return DEFAULT_FORMAT.format(date);
    }

    /**
     * 获取当天00:00的时间戳
     * @return
     */
    public static long getWeeOfToday(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long string2Millis(final String time,final DateFormat format){
        try{
            return format.parse(time).getTime();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return -1;
    }


    public static String getFriendlyTimeSpanByNow(final String time){
        return getFriendlyTimeSpanByNow(time,DEFAULT_FORMAT);
    }

    public static String getFriendlyTimeSpanByNow(final String time,final DateFormat format){
        return getFriendlyTimeSpanByNow(string2Millis(time,format));
    }

    /**
     * 获取友好型与当前时间的差
     * @param millis
     * @return 如果小于1秒，显示刚刚
     *         如果在1分钟内显示xx秒前
     *         如果在1小时间内，显示XX分钟前
     *         如果在1小时外的今天内，显示今天HH:mm
     *         如果是昨天，显示昨天HH:mm
     *         其余显示   yyyy-MM-dd
     */

    public static String getFriendlyTimeSpanByNow(final long millis){

        long now = System.currentTimeMillis();
        long span = now-millis;

        if (span <0) return String.format("%tc",millis);
        if (span <1000){
            return "刚刚";
        }else if (span < TimeConstants.MIN){
            return String.format(Locale.getDefault(),"%d秒前",span/TimeConstants.SEC);
        }else if (span <TimeConstants.HOUR){
            return String.format(Locale.getDefault(),"%d分钟前",span/TimeConstants.MIN);
        }

        //获取当天00:00的时间戳
        long wee =getWeeOfToday();

        if (millis >=wee){
            return String.format("今天%tR",millis);
        }else if (millis >= wee - TimeConstants.DAY){
            return String.format("昨天%tR",millis);
        }else {
            return String.format("%tF",millis);
        }
    }


    public static String getCurrentTimeZone(){
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(false,true,tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,boolean includeMinuteSeparator,int offsetMillis){
        int offsetMinutes = offsetMillis/60000;
        char sign = '+';
        if (offsetMinutes < 0){
            sign ='-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt){
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder,2,offsetMinutes/60);

        if (includeMinuteSeparator){
            builder.append(':');
        }
        appendNumber(builder,2,offsetMillis%60);
        return builder.toString();
    }

    public static void appendNumber(StringBuilder builder,int count,int value){
        String string = Integer.toString(value);
        for (int i=0; i<count -string.length();i++){
            builder.append('0');
        }
        builder.append(string);
    }


    public final class TimeConstants {

        /**
         * 毫秒与毫秒倍数
         */

        public static final int MSEC = 1;

        /**
         * 秒与毫秒的倍数
         */

        public static final int SEC = 1000;

        /**
         * 分与毫秒的倍数
         */
        public static final int MIN = 60000;

        /**
         * 时与毫秒倍数
         */

        public static final int HOUR = 3600000;

        /**
         * 天与毫秒倍数
         */

        public static final int DAY = 86400000;

    }

}
