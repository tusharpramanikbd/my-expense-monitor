package com.tushar.own.myexpensemonitor.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentDateEventListener;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentMonthEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentDateServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentMonthServices;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    AppCompatButton btnDailyChart, btnMonthlyChart, btnYearlyChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDailyChart = view.findViewById(R.id.btnDailyChart);
        btnMonthlyChart = view.findViewById(R.id.btnMonthlyChart);
        btnYearlyChart = view.findViewById(R.id.btnYearlyChart);

        Fragment childFragmentDailyExpenseChart = new DailyExpenseChartFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.childFrameChartFragment, childFragmentDailyExpenseChart).commit();

        btnDailyChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment childFragmentDailyExpenseChart = new DailyExpenseChartFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.childFrameChartFragment, childFragmentDailyExpenseChart).commit();

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                btnDailyChart.setTextColor(Color.parseColor("#000000"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnMonthlyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnMonthlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnYearlyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnYearlyChart.setTypeface(Typeface.DEFAULT_BOLD);


            }
        });

        btnMonthlyChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment childFragmentMonthlyExpenseChart = new MonthlyExpenseChartFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.childFrameChartFragment, childFragmentMonthlyExpenseChart).commit();

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                btnMonthlyChart.setTextColor(Color.parseColor("#000000"));
                btnMonthlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnDailyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnYearlyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnYearlyChart.setTypeface(Typeface.DEFAULT_BOLD);

            }
        });

        btnYearlyChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment childFragmentYearlyExpenseChart = new YearlyExpenseChartFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.childFrameChartFragment, childFragmentYearlyExpenseChart).commit();

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                btnYearlyChart.setTextColor(Color.parseColor("#000000"));
                btnYearlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnDailyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                btnMonthlyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnMonthlyChart.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
