package com.app.cbouix.sodapp.Utils;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CBouix on 21/04/2017.
 */

public class DateTimeUtil {

    public static String getDateNowString(){
        Calendar c = Calendar.getInstance();
        String day= String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String year = String.valueOf(c.get(Calendar.YEAR));

        DecimalFormat mFormat= new DecimalFormat("00");
        mFormat.setRoundingMode(RoundingMode.DOWN);
        return mFormat.format(Double.valueOf(day)) +  mFormat.format(Double.valueOf(month)) + year;
    }
    public static String getDateNowStringWithMinutes(){
        Calendar c = Calendar.getInstance();
        String day= String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String year = String.valueOf(c.get(Calendar.YEAR));
        String hours= String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(c.get(Calendar.MINUTE));
        String seconds = String.valueOf(c.get(Calendar.SECOND));

        DecimalFormat mFormat= new DecimalFormat("00");
        mFormat.setRoundingMode(RoundingMode.DOWN);
        return mFormat.format(Double.valueOf(day)) +  mFormat.format(Double.valueOf(month)) + year+
                mFormat.format(Double.valueOf(hours)) +  mFormat.format(Double.valueOf(minutes)) +
                mFormat.format(Double.valueOf(seconds));
    }

    public static String convertDateToFormat(String dateAsString){
        DateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");

        try {
            Date date = sourceFormat.parse(dateAsString);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            String day= String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            String month = String.valueOf(c.get(Calendar.MONTH)+1);
            String year = String.valueOf(c.get(Calendar.YEAR));

            DecimalFormat mFormat= new DecimalFormat("00");
            mFormat.setRoundingMode(RoundingMode.DOWN);
            return mFormat.format(Double.valueOf(day)) +"-"+  mFormat.format(Double.valueOf(month)) +"-"+ year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAsString;
    }
}
