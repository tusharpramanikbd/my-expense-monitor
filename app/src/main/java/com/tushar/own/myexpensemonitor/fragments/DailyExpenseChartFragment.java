package com.tushar.own.myexpensemonitor.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentDateEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentDateServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentMonthServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByDateServices;
import com.tushar.own.myexpensemonitor.services.ViewPagerPageChangedServices;
import com.tushar.own.myexpensemonitor.utils.MyValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyExpenseChartFragment extends Fragment implements
        ExpenseLocalDBRetrieveAllByCurrentDateEventListener,
        ViewPagerPageChangedServices.ViewPagerPageChangeListener {

    private PieChart pieChart;
    //Declaring & Initializing X & Y data for PieChart
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f, 30.5f, 17.3f, 5.7f};
    private String[] xData = {"Housing", "Transportation", "Food", "Utilities",
            "Clothing", "Medical", "Household Items",
            "Education", "Entertainment", "Misc"};
    private float[] myYAxis = new float[10];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_expense_chart, container, false);

        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().getAllExpensesByCurrentDate(DateAndTimeServices.getInstance().generateCurrentDate());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initializing Pie Chart Element
        pieChart = view.findViewById(R.id.pieChart);

        initializePieChart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPagerPageChangedServices.getInstance().AddViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().AddExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(this);
    }

    @Override
    public void onDestroy() {
        ViewPagerPageChangedServices.getInstance().RemoveViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().RemoveExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(this);
        super.onDestroy();
    }

    private void initializePieChart() {
        //Setting PieChart Functionality
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Daily Expense Chart");
        pieChart.setCenterTextSize(18);
        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.getLegend().setEnabled(true);

        //Adding All The DateSet(X data, Y data, Colors, etc.) To Pie Chart
        addDataSetToPieChart();
    }

    private void addDataSetToPieChart() {

        //Initializing An ArrayList of PieEntry Object
        ArrayList<PieEntry> mPieEntries = new ArrayList<>();

        //Storing X data & Y data to mPieEntries
        for(int i = 0; i < myYAxis.length; i++){
            mPieEntries.add(new PieEntry(myYAxis[i] , xData[i]));
        }

        //Initializing PieDataSet & Setting UI Functionality
        PieDataSet pieDataSet = new PieDataSet(mPieEntries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //Initializing An ArrayList of Colors and Adding the Colors to the List
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0,202,120));
        colors.add(Color.rgb(0,216,190));
        colors.add(Color.rgb(255,210,70));
        colors.add(Color.rgb(255,83,77));
        colors.add(Color.rgb(119,226,57));
        colors.add(Color.rgb(105,63,246));
        colors.add(Color.rgb(0,134,247));
        colors.add(Color.rgb(255,46,195));
        colors.add(Color.rgb(255,138,73));
        colors.add(Color.rgb(0,194,225));

        //Setting the Colors List to pieDataSet
        pieDataSet.setColors(colors);

        //Initializing a PieData object with pieDataSet
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new MyValueFormatter());

        //Setting PieData to pie_Chart & UI Functionality
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.invalidate();

    }

    @Override
    public void expenseGetSuccessfully(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < 10; i++){
            double totalSum = 0.0;
            for (int j = 0; j < expenseModels.size(); j++){
                if (xData[i].equals(expenseModels.get(j).getExpenseCategory())){
                    totalSum = totalSum + expenseModels.get(j).getExpenseAmount();
                }
            }
            myYAxis[i] = (float) totalSum;
        }

        addDataSetToPieChart();
    }

    @Override
    public void expenseGetFailed() {
        myYAxis = new float[10];
        addDataSetToPieChart();
    }

    @Override
    public void onPageViewPagerChanged() {
        initializePieChart();
    }

}
