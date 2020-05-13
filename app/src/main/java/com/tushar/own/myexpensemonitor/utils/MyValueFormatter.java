package com.tushar.own.myexpensemonitor.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value) {

        if(value > 0) {
            return mFormat.format(value);
        } else {
            return "";
        }
    }
}
