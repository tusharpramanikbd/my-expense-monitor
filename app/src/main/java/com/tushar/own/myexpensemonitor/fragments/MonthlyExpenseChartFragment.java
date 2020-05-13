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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentMonthEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentMonthServices;
import com.tushar.own.myexpensemonitor.services.ViewPagerPageChangedServices;
import com.tushar.own.myexpensemonitor.utils.MyValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MonthlyExpenseChartFragment extends Fragment implements
        ExpenseLocalDBRetrieveAllByCurrentMonthEventListener,
        ViewPagerPageChangedServices.ViewPagerPageChangeListener{

    private BarChart barChartDailyExpense;
    private float[] myYAxis = new float[31];
    private double maxDailyExpense = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly_expense_chart, container, false);

        ExpenseDbRetrieveAllByCurrentMonthServices.getInstance().getAllExpensesByCurrentMonth(DateAndTimeServices.getInstance().generateCurrentMonth());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChartDailyExpense = view.findViewById(R.id.barChartMonthlyExpense);

        initializeBarChart();

    }

    private void initializeBarChart() {
        barChartDailyExpense.setDrawBarShadow(false);
        barChartDailyExpense.setDrawValueAboveBar(true);
        barChartDailyExpense.setMaxVisibleValueCount((int)maxDailyExpense);
        barChartDailyExpense.setPinchZoom(false);
        barChartDailyExpense.setDrawGridBackground(true);
        barChartDailyExpense.getDescription().setEnabled(false);
        barChartDailyExpense.animateY(1000, Easing.EaseInOutCubic);


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //Storing X data & Y data to mPieEntries
        for(int i = 0; i < myYAxis.length; i++){
            barEntries.add(new BarEntry((float)(i+1) , myYAxis[i]));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Monthly Expense Chart");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        data.setValueFormatter(new MyValueFormatter());
        barChartDailyExpense.setData(data);
        barChartDailyExpense.setFitBars(true);
        barChartDailyExpense.invalidate();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPagerPageChangedServices.getInstance().AddViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentMonthServices.getInstance().AddExpenseLocalDBRetrieveAllByCurrentMonthEventDoneListener(this);
    }

    @Override
    public void onDestroy() {
        ViewPagerPageChangedServices.getInstance().RemoveViewPagerPageChangedEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentMonthServices.getInstance().RemoveExpenseLocalDBRetrieveAllByCurrentMonthEventDoneListener(this);
        super.onDestroy();
    }

    @Override
    public void expenseGetByMonthSuccessfully(List<ExpenseModel> expenseModels) {

        for (int i = 1; i <= 31; i++){
            double dailyTotalExpense = 0.0;
            for (int j = 0; j < expenseModels.size(); j++){
                if (i == Integer.parseInt(expenseModels.get(j).getExpenseDate().substring(0, 2))){
                    dailyTotalExpense = dailyTotalExpense + expenseModels.get(j).getExpenseAmount();
                }
            }

            myYAxis[i-1] = (float) dailyTotalExpense;

            if (dailyTotalExpense > maxDailyExpense){
                maxDailyExpense = dailyTotalExpense;
            }
        }

        initializeBarChart();

    }

    @Override
    public void expenseGetByMonthFailed() {
        myYAxis = new float[31];
    }

    @Override
    public void onPageViewPagerChanged() {
        initializeBarChart();

    }
}
