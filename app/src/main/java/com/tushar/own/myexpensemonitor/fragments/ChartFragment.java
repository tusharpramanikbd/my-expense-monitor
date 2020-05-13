package com.tushar.own.myexpensemonitor.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tushar.own.myexpensemonitor.R;

public class ChartFragment extends Fragment {

    private AppCompatButton btnDailyChart, btnMonthlyChart, btnYearlyChart;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                btnDailyChart.setTextColor(Color.parseColor("#000000"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                btnMonthlyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnMonthlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
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

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                btnMonthlyChart.setTextColor(Color.parseColor("#000000"));
                btnMonthlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                btnDailyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
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

                btnYearlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                btnYearlyChart.setTextColor(Color.parseColor("#000000"));
                btnYearlyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnDailyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                btnDailyChart.setTextColor(Color.parseColor("#FFFFFF"));
                btnDailyChart.setTypeface(Typeface.DEFAULT_BOLD);

                btnMonthlyChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
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
