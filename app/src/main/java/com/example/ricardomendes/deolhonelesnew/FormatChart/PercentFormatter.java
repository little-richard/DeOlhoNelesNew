package com.example.ricardomendes.deolhonelesnew.FormatChart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class PercentFormatter implements IValueFormatter {
    protected DecimalFormat mFormat;
    public PercentFormatter(){
        mFormat = new DecimalFormat("###,###,##0.0");
    }
    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " %";
    }
    public PercentFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    public int getDecimalDigits() {
        return 1;
    }
}
