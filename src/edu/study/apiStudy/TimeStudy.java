package edu.study.apiStudy;

import java.util.Calendar;

public class TimeStudy {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        System.out.println("现在时间是："+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DATE)+"日");
        cal.set(Calendar.MONTH, 7);
        cal.set(Calendar.YEAR, 2077);
        cal.set(Calendar.DATE, 1);
        System.out.println("现在时间是："+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DATE)+"日");
        calendar.set(2050,2,1);
        System.out.println(calendar.getTime());
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }
}
