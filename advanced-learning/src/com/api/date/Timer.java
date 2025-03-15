package com.api.date;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Timer {
    private long days; // 天数
    private long hours; // 时
    private long minutes; // 分
    private long seconds; // 秒
    private Map<Integer, Long> markers; // 用于存储标记的时间点，key为mark编号，value为时间点（秒）
    private boolean isPaused;

    // 常量定义
    public static final int IN_SECONDS = 1;
    public static final int IN_MINUTES = 2;
    public static final int IN_HOURS = 3;
    public static final int FORMATTED = 4;

    public Timer() {
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.markers = new ConcurrentHashMap<>();
        this.isPaused = false;
    }

    // 设置时间
    public void setTime(int hours, int minutes, int seconds) {
        if (!isValidTime(hours, minutes, seconds)) return;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.days = 0; // 设置时间时重置天数
    }

    // 设置时间（不重置天数，根据传入天数调整）
    public void setTimeWithoutReset(int hours, int minutes, int seconds, int day) {
        if (!isValidTime(hours, minutes, seconds)) return;
        if (day == 0) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        } else if (day > 0) {
            this.days += day;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        } else {
            System.out.println("Invalid day parameter. Day must be 0 or greater.");
        }
    }

    // 添加时间
    public void addTime(double additionalSeconds) {
        if (isPaused) {
            System.out.println("Timer is paused. Time cannot be added.");
            return;
        }
        long totalSeconds = (long) (this.getTotalSeconds() + additionalSeconds);
        this.updateTimeFromSeconds(totalSeconds);
    }

    // 获取当前时间的格式化字符串（不包含天数）
    public String getTime() {
        return getFormattedTime(getTotalSeconds() % 86400); // 只返回当天时间部分
    }

    // 获取带天数的格式化时间字符串
    public String getTimeWithDay() {
        return String.format("Day %d - %02d:%02d:%02d", days, hours, minutes, seconds);
    }

    // 获取当前秒数（从00:00:00算起的总秒数）
    public long getSecond() {
        return getTotalSeconds();
    }

    // 获取天数
    public int getDay() {
        return (int) days;
    }

    // 添加标记
    public void addMarker(int markerId) {
        if (markerId < 0) {
            System.out.println("Marker ID cannot be negative.");
            return;
        }
        if (!markers.containsKey(markerId)) {
            markers.put(markerId, getTotalSeconds());
        } else {
            System.out.println("Marker ID " + markerId + " already exists!");
        }
    }

    // 移除标记
    public void removeMarker(int markerId) {
        if (markers.containsKey(markerId)) {
            markers.remove(markerId);
        } else {
            System.out.println("Marker ID " + markerId + " does not exist!");
        }
    }

    // 获取指定标记的时间（以秒数返回）
    public long getMarkerTime(int markerId) {
        if (!markers.containsKey(markerId)) {
            System.out.println("Marker ID " + markerId + " does not exist!");
            return -1; // 返回-1表示未找到
        }
        return markers.get(markerId);
    }

    // 获取标记时间差（支持秒、分钟、小时、格式化时间）
    public String getTimeDifferenceFromMarker(int markerId, int format) {
        if (!markers.containsKey(markerId)) {
            return "Marker ID " + markerId + " does not exist!";
        }

        long markerTime = markers.get(markerId);
        long difference = getTotalSeconds() - markerTime;

        return formatTimeDifference(difference, format);
    }

    // 获取两个标记的时间差（支持秒、分钟、小时、格式化时间）
    public String getDifferenceBetweenMarkers(int markerId1, int markerId2, int format) {
        if (!markers.containsKey(markerId1)) {
            return "Marker ID " + markerId1 + " does not exist!";
        }
        if (!markers.containsKey(markerId2)) {
            return "Marker ID " + markerId2 + " does not exist!";
        }

        long time1 = markers.get(markerId1);
        long time2 = markers.get(markerId2);
        long difference = Math.abs(time1 - time2); // 计算时间差，使用绝对值

        return formatTimeDifference(difference, format);
    }

    // 获取所有标记
    public void getAllMarkers() {
        for (Map.Entry<Integer, Long> entry : markers.entrySet()) {
            System.out.println("Marker " + entry.getKey() + ": " + entry.getValue() + " seconds");
        }
    }

    // 重置时间和标记
    public void reset() {
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.markers.clear();
    }

    // 暂停或恢复计时器
    public void pause(boolean pause) {
        this.isPaused = pause;
    }

    // 校验时间是否合法
    private boolean isValidTime(int hours, int minutes, int seconds) {
        if (hours < 0 || hours >= 24 || minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60) {
            System.out.println("Invalid time input. Hours must be 0-23, minutes and seconds must be 0-59.");
            return false;
        }
        return true;
    }

    // 获取时间差的通用格式化方法
    private String formatTimeDifference(long difference, int format) {
        switch (format) {
            case IN_SECONDS:
                return difference + " seconds";
            case IN_MINUTES:
                return (difference / 60) + " minutes";
            case IN_HOURS:
                return (difference / 3600) + " hours";
            case FORMATTED:
                return getFormattedTime(difference);
            default:
                return "Invalid format value. Use 1 (seconds), 2 (minutes), 3 (hours), or 4 (formatted time).";
        }
    }

    // 获取时间差的格式化字符串（支持天数）
    public String getFormattedTimeWithDays(long seconds) {
        long days = seconds / 86400;
        seconds = seconds % 86400;
        long hrs = seconds / 3600;
        long mins = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("Day %d - %02d:%02d:%02d", days, hrs, mins, secs);
    }

    // 获取时间的格式化字符串（不含天数）
    public String getFormattedTime(long seconds) {
        long hrs = seconds / 3600;
        long mins = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    // 私有方法：获取当前时间的总秒数
    private long getTotalSeconds() {
        return days * 86400 + hours * 3600 + minutes * 60 + seconds;
    }

    // 私有方法：根据总秒数更新时间
    private void updateTimeFromSeconds(long totalSeconds) {
        days = totalSeconds / 86400; // 每86400秒为一天
        totalSeconds = totalSeconds % 86400; // 计算当天剩余的秒数
        this.hours = totalSeconds / 3600;
        this.minutes = (totalSeconds % 3600) / 60;
        this.seconds = totalSeconds % 60;
    }
}


class main{
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.setTime(8,0,0);
        System.out.println(timer.getTime());
        System.out.println(timer.getTimeWithDay());
        timer.addTime(150);
        System.out.println(timer.getTimeWithDay());
        timer.setTimeWithoutReset(12,30,0,1);
        System.out.println(timer.getTimeWithDay());
        timer.pause(true);
        System.out.println(timer.getTime());
        timer.addTime(180);
        System.out.println(timer.getTimeWithDay());
        timer.pause(false);
        timer.addMarker(5);
        System.out.println(timer.getFormattedTime(1000));
        timer.addTime(150);
        System.out.println(timer.getTimeDifferenceFromMarker(5, timer.FORMATTED));
        timer.addMarker(1);
        timer.addTime(6000);
        System.out.println(timer.getTime());
        System.out.println(timer.getTimeDifferenceFromMarker(1, timer.IN_MINUTES));

    }
}
