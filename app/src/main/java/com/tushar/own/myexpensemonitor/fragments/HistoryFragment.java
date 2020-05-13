package com.tushar.own.myexpensemonitor.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.activities.MainActivity;
import com.tushar.own.myexpensemonitor.adapters.ExpenseItemListAdapter;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByDateEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByDateServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment implements ExpenseLocalDBRetrieveAllByDateEventListener {

    private ExpenseItemListAdapter expenseItemListAdapter;
    private ArrayList<ExpenseModel> expenseModelArrayList;
    private AppCompatTextView tvToday, tvNoExpenseAdded;
    private DatePickerDialog picker;
    private Context context;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpenseDbRetrieveAllByDateServices.getInstance().AddExpenseLocalDBRetrieveAllByDateEventDoneListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (MainActivity)context;
    }

    @Override
    public void onDestroy() {
        ExpenseDbRetrieveAllByDateServices.getInstance().RemoveExpenseLocalDBRetrieveAllByDateEventDoneListener(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvToday = view.findViewById(R.id.tvToday);
        tvToday.setText(DateAndTimeServices.getInstance().generateCurrentDate());

        tvNoExpenseAdded = view.findViewById(R.id.tvNoExpenseAdded);

        AppCompatImageView ivDatePicker = view.findViewById(R.id.ivDatePicker);
        ivDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvToday.setText(DateAndTimeServices.getInstance().dateFormatter(
                                        dayOfMonth,
                                        monthOfYear,
                                        year));
                                ExpenseDbRetrieveAllByDateServices.getInstance().getAllExpensesByDate(
                                        DateAndTimeServices.getInstance().dateFormatter(
                                        dayOfMonth,
                                        monthOfYear,
                                        year));
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        expenseModelArrayList = new ArrayList<>();
        ExpenseDbRetrieveAllByDateServices.getInstance().getAllExpensesByDate(DateAndTimeServices.getInstance().generateCurrentDate());

        expenseItemListAdapter = new ExpenseItemListAdapter(context, activity, expenseModelArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(expenseItemListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void expenseGetSuccessfully(List<ExpenseModel> expenseModels) {
        tvNoExpenseAdded.setVisibility(View.GONE);
        expenseModelArrayList.clear();
        Collections.reverse(expenseModels);
        expenseModelArrayList.addAll(expenseModels);
        expenseItemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void expenseGetFailed() {
        expenseModelArrayList.clear();
        expenseItemListAdapter.notifyDataSetChanged();
        tvNoExpenseAdded.setVisibility(View.VISIBLE);
    }

}
