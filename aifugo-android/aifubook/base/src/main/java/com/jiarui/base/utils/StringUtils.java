package com.jiarui.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("SimpleDateFormat")
public class StringUtils {
    private final static double EARTH_RADIUS = 6378.137;

    /**
     * 精确到小数点后几位(例："0.0"，"0.00")
     */
    public static String getDoublePrecision(String v1, String type) {
        BigDecimal subA = new BigDecimal(v1);
        DecimalFormat df = new DecimalFormat(type);
        return df.format(subA.doubleValue());
    }

    /**
     * 从Assets中读取
     */
    public static String getFromAssets(Context mContext, String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
        } catch (Exception e) {
        } finally {
            return Result;
        }
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    public static String formatPhoneNumber(String user_phone) {
        String phoneString = user_phone;
        if (!isEmpty(user_phone)) {
            phoneString = user_phone.substring(0, 3) + "****" + user_phone.substring(7, user_phone.length());
        }

        return phoneString;
    }

    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static SimpleDateFormat dateFormater = new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private final static SimpleDateFormat dateFormater2 = new
    // SimpleDateFormat("yyyy-MM-dd");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {

            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        if (isEmpty(sdate)) {
            return "Unknown";
        }
        long lcc = Long.valueOf(sdate);
        Date time = new Date(lcc * 1000L);
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    /**
     * 返回String类型的今天的日期 小时，分
     *
     * @return
     */
    public static String getStrToday() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date toDay = null;
        String today = "";
        try {
            toDay = format.parse(dateStr);
            long longDay = toDay.getTime();
            String strDay = String.valueOf(longDay);
            today = strDay.substring(0, 10);
            return today;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // curDate = curDate.replace("-", "");

        return today;
    }

    /**
     * 将时间转换为时间戳2
     */
    public static long dateToStamp2(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        res = String.valueOf(date.getTime());
        if (res.length() > 10) {
            res = res.substring(0, 10);
        }
        long ts = Integer.parseInt(res);

        return ts;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        long lcc = Long.valueOf(s);
//        int i = Integer.parseInt(s);
        String times = sdr.format(new Date(lcc));
        return times;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStampDateTian(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }


    /**
     * 计算两个日期相差多少天
     */
    public static int daysBetween(long endTimer, long startTimer) {
        long between_days = 0;
        if (endTimer > startTimer) {
            between_days = (endTimer - startTimer) / (1000 * 3600 * 24);
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 返回String类型的今天的日期
     *
     * @return
     */
    public static String getTodayDate(int day) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int days = 0;

        if (day != 0) {
            days = cal.get(Calendar.DAY_OF_MONTH) + 1;
        } else {
            days = cal.get(Calendar.DAY_OF_MONTH);
        }
        String monthStr = "";
        String dayStr = "";
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = month + "";
        }
        if (days < 10) {
            dayStr = "0" + days;
        } else {
            dayStr = days + "";
        }
        String dateStr = year + "-" + monthStr + "-" + dayStr;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date toDay = null;
//        String today = "";
//        try {
//            toDay = format.parse(dateStr);
//            long longDay = toDay.getTime();
//            String strDay = String.valueOf(longDay);
//            today = strDay.substring(0, 10);
//            return today;
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        // curDate = curDate.replace("-", "");

        return dateStr;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;


        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static String isNull(String input) {

        return isEmpty(input) ? "" : input;
    }

    public static String isNumberNull(String input) {

        return isEmpty(input) ? "0" : input;
    }


    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }


    public static String getStringDate(int year, int mouth, int day) {
        String mouthStr, dayStr;
        if (mouth < 10) {
            mouthStr = "0" + mouth;
        } else {
            mouthStr = "" + mouth;
        }
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        return year + mouthStr + dayStr;
    }

    public static String getStringDate2(int year, int month, int day, int hour, int minute) {
        String monthStr, dayStr, hourStr, minuteStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        return year + "年" + monthStr + "月" + dayStr + "日" + hourStr + "时" + minuteStr + "分";
    }

    public static String getStringDate3(int year, int month, int day, int hour, int minute) {
        String monthStr, dayStr, hourStr, minuteStr;

        monthStr = "" + month;

        dayStr = "" + day;

        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        return year + "年" + monthStr + "月" + dayStr + "日" + "  " + hourStr + ":" + minuteStr;
    }

    /**
     * 根据生日得到当前年龄
     *
     * @param birth 生日
     * @return
     */
    public static String getToAge(String birth) {
        Calendar cal = Calendar.getInstance();
        int toyear = cal.get(Calendar.YEAR);
        int birthyear = Integer.parseInt(birth.substring(0, 4));
        return "" + (toyear - birthyear);
    }

    /**
     * 转换格式 将20140503转换为2014-05-03
     *
     * @param dateStr 20140503
     * @return
     */
    public static String translateDateString(String dateStr) {

        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
    }

    /**
     * string md5算法
     *
     * @param password
     * @return
     */
    public static String makeMD5(String password) {
        MessageDigest md;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String pwd = new BigInteger(1, md.digest()).toString(16);
            System.err.println(pwd);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * 保留一位小数
     */
    public static float getDecimalFormat(float f) {
        // BigDecimal b = new BigDecimal(f);
        // float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        float f1 = (float) (Math.round(f * 10)) / 10;
        return f1;
    }

    public static float getDecimalFormat2(double f) {
        // BigDecimal b = new BigDecimal(f);
        // float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        float f1 = (float) (Math.round(f * 10)) / 10;
        return f1;
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String byteToString(byte[] string) {
        String str = "";
        try {
            if (string == null) {
                return str;
            }
            str = new String(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd$HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14 16:09"）
     *
     * @param time
     * @return
     */
    public static String timesDay(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日"）
     *
     * @param time
     * @return
     */
    public static String timesStamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static long Data2LinuxTime(String time) throws ParseException {
        return Data2LinuxTime(time.trim(), ConstantUtil.YEAR_DATEFORMAT);
    }

    public static long Data2LinuxTime(String time, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat.trim());
        Date date = format.parse(time.trim());
        return date.getTime();
    }

    /**
     * @param time
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static String String2Date(long time, String formatStr) throws ParseException {
        return new SimpleDateFormat(formatStr).format(new Date(time));
    }

    /**
     * @param time
     * @param formatStr
     * @return
     */
    public static String String2Date(String time, String formatStr) {
        long t = 0;
        try {
            t = Long.parseLong(time) * 1000;
        } catch (Exception exception) {
            t = 0;
        }
        return new SimpleDateFormat(formatStr).format(new Date(t));
    }

    /**
     * 最小单位为s 输入时间戳 输出时分秒单位String
     * <li>注意时间戳最小单位为S
     * <p/>
     * $
     * <p/>
     * $
     *
     * @param time
     * @return
     */
    public static String countDown(long time) {
        int h = 0;
        int m = 0;

        h = (int) (time / (60 * 60));
        time -= h * 60 * 60;
        m = (int) (time / 60);
        time -= m * 60;

        String hour = h < 10 ? "0" + h : "" + h;
        String min = m < 10 ? "0" + m : "" + m;
        String sec = time < 10 ? "0" + time : "" + time;

        return hour + ":" + min + ":" + sec;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时";
    }

    /******
     * MD5
     */
    public static String getMessageDigest(String src) {

        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            // localMessageDigest.reset();
            localMessageDigest.update(src.getBytes());
            String str = bytesToHexString(localMessageDigest.digest());
            return str;
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
        }
        return "error";
    }

    private static String bytesToHexString(byte[] bytes) {

        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 判断手机号
     *
     * @param mobiles 手机号
     * @return true正确 ，反之不对
     */
    public static boolean isMobileNO(String mobiles) {
        return mobiles.startsWith("1") && mobiles.length() == 11 ? false : true;
//        Pattern p = Pattern.compile("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
    }

    /**
     * 判断昵称不包含数字
     *
     * @param nickname
     * @return
     */
    public static boolean isNickNO(String nickname) {
        Pattern p = Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]*$");
        Matcher m = p.matcher(nickname);
        return m.matches();

    }

    /**
     * HTML转义字符 --> 字符串
     */
    public static String htmlEscapeCharsToString(String source) {
        return isEmpty(source) ? source : source.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&copy;", "")
                .replaceAll("&yen;", "¥")
                .replaceAll("&divide;", "÷")
                .replaceAll("&times;", "×")
                .replaceAll("&reg;", "")
                .replaceAll("&sect;", "§")
                .replaceAll("&pound;", "£")
                .replaceAll("&cent;", "￠");
    }

    /**
     * 保留两位小数
     *
     * @param newDistance
     * @return
     */
    public static float saveTwoDecimalPlaces(float newDistance) {
        BigDecimal bigdec = new BigDecimal(newDistance);
        float result = bigdec.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//保存两位小数
        return result;
    }
}
