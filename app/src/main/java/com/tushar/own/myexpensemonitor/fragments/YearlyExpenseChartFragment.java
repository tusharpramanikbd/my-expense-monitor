package com.tushar.own.myexpensemonitor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentYearEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentYearServices;
import com.tushar.own.myexpensemonitor.services.ViewPagerPageChangedServices;
import com.tushar.own.myexpensemonitor.utils.MyValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YearlyExpenseChartFragment extends Fragment implements
        ExpenseLocalDBRetrieveAllByCurrentYearEventListener,
        ViewPagerPageChangedServices.ViewPagerPageChangeListener{

    private BarChart barChartYearlyExpense;
    private float[] myYAxis = new float[12];
    private double maxMonthlyExpense = 0.0;
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yearly_expense_chart, container, false);

        ExpenseDbRetrieveAllByCurrentYearServices.getInstance().getAllExpensesByCurrentYear(DateAndTimeServices.getInstance().generateCurrentYear());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChartYearlyExpense = view.findViewById(R.id.barChartYearlyExpense);

        initializeBarChart();

    }

    private void initializeBarChart() {
        barChartYearlyExpense.setDrawBarShadow(false);
        barChartYearlyExpense.setDrawValueAboveBar(true);
        barChartYearlyExpense.setMaxVisibleValueCount((int)maxMonthlyExpense);
        barChartYearlyExpense.setPinchZoom(false);
        barChartYearlyExpense.setDrawGridBackground(true);
        barChartYearlyExpense.getDescription().setEnabled(false);
        barChartYearlyExpense.animateY(1000, Easing.EaseInOutCubic);


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //Storing X data & Y data to mPieEntries
        for(int i = 0; i < myYAxis.length; i++){
            barEntries.add(new BarEntry((float)(i) , myYAxis[i]));
        }

        XAxis xAxis = barChartYearlyExpense.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount()));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Yearly Expense Chart");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        data.setValueFormatter(new MyValueFormatter());
        barChartYearlyExpense.setData(data);
        barChartYearlyExpense.setFitBars(true);
        barChartYearlyExpense.invalidate();


    }

    private ArrayList<String> getAreaCount(){

        return new ArrayList<>(Arrays.asList(months));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPagerPageChangedServices.getInstance().AddViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentYearServices.getInstance().AddExpenseLocalDBRetrieveAllByCurrentYearEventDoneListener(this);
    }

    @Override
    public void onDestroy() {
        ViewPagerPageChangedServices.getInstance().RemoveViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentYearServices.getInstance().RemoveExpenseLocalDBRetrieveAllByCurrentYearEventDoneListener(this);
        super.onDestroy();
    }

    @Override
    public void expenseGetByYearSuccessfully(List<ExpenseModel> expenseModels) {
        for (int i = 1; i <= 12; i++){
            double monthlyTotalExpense = 0.0;
            for (int j = 0; j < expenseModels.size(); j++){
                if (months[i-1].equals(expenseModels.get(j).getExpenseDate().substring(3, 6))){
                    monthlyTotalExpense = monthlyTotalExpense + expenseModels.get(j).getExpenseAmount();
                }
            }

            myYAxis[i-1] = (float) monthlyTotalExpense;

            if (monthlyTotalExpense > maxMonthlyExpense){
                maxMonthlyExpense = monthlyTotalExpense;
            }
        }

        initializeBarChart();

    }

    @Override
    public void expenseGetByYearFailed() {
        myYAxis = new float[12];
    }

    @Override
    public void onPageViewPagerChanged() {
        initializeBarChart();
    }
}
