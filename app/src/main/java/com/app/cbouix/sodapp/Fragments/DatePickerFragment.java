package com.app.cbouix.sodapp.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CBouix on 06/05/2017.
 */

public class DatePickerFragment  extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView txtDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        int _month = month+1;
        try {
            Date date = format.parse(day + "/" + _month + "/" + year);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            this.txtDate.setText(formatDay.format(c.getTime()) + "/" +
                    formatMonth.format(c.getTime()) + "/" + c.get(Calendar.YEAR));
        }catch (Exception e){
            this.txtDate.setText(day + "/" + _month + "/" + year);
        }
    }

    public void setTextView(TextView txtDate){
        this.txtDate = txtDate;
    }
}