package com.tushar.own.myexpensemonitor.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateAndTimeServices {

    private static DateAndTimeServices dateAndTimeServices = new DateAndTimeServices();

    public static DateAndTimeServices getInstance() {
        if(dateAndTimeServices ==null){
            Class clazz = DateAndTimeServices.class;
            synchronized (clazz){
                dateAndTimeServices = new DateAndTimeServices();
            }
        }

        return dateAndTimeServices;
    }

    public String generateCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    public String generateCurrentTime(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return df.format(c);
    }

    public String generateCurrentMonth(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM", Locale.getDefault());
        return df.format(c);
    }

    public String generateCurrentYear(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        return df.format(c);
    }

    public String dateFormatter(int dayOfMonth, int monthOfYear, int year){
        String day;

        //Day modification
        if (dayOfMonth < 10){
            day = "0" + dayOfMonth;
        }
        else {
            day = String.valueOf(dayOfMonth);
        }

        String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


        return day + "-" + months[monthOfYear] + "-" + year;

    }

}
