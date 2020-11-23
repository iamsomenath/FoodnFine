package snd.orgn.foodnfine.helper.other;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {


        private static DateHelper singleInstance=null;

    private DateHelper(){
    }
        public static DateHelper getInstance(){
        if(singleInstance==null){
            singleInstance=new DateHelper();
        }
        return singleInstance;
    }

        public String getDay(String date){
        String day="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        try {
            Date selectedDate=dateFormat.parse(date);
            day = (String) DateFormat.format("dd",selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

        public String getMonth(String date){
        String month="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        try {
            Date selectedDate=dateFormat.parse(date);
            month  = (String) DateFormat.format("MMM",  selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }
}

