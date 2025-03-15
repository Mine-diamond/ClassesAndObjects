package com.api.date;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateLearn1 {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        System.out.println(date);
        System.out.println(date);
        System.out.println(date.before(new Date()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"+" HH:mm:ss");
        System.out.println(sdf.format(date));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss，E a");
        System.out.println(sdf2.format(date));
        String today = "2025年7月10日";
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");
        Date parsed = sdf3.parse(today);
        System.out.println(parsed);
    }
}
