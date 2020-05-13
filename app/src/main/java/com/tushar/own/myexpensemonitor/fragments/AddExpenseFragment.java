package com.tushar.own.myexpensemonitor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.AddExpenseButtonClickListener;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBAdditionEventListener;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentDateEventListener;
import com.tushar.own.myexpensemonitor.listeners.SettingsChangedListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.DateAndTimeServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbRetrieveAllByCurrentDateServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbSingleInsertServices;
import com.tushar.own.myexpensemonitor.services.SettingsChangedServices;
import com.tushar.own.myexpensemonitor.services.SharedPreferenceServices;
import com.tushar.own.myexpensemonitor.utils.MyAlertDialog;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddExpenseFragment extends Fragment implements ExpenseLocalDBAdditionEventListener,
        ExpenseLocalDBRetrieveAllByCurrentDateEventListener,
        SettingsChangedListener {

    private AppCompatTextView tvCurrentDate, tvTotalExpense, tvExpenseCurrency;
    private AppCompatImageButton ibAddExpense;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpenseDbSingleInsertServices.getInstance().AddExpenseLocalDBAdditionEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().AddExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(this);
        SettingsChangedServices.getInstance().AddSettingsChangedEventDoneListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initializing all the UI element
        initializeUI(view);

    }

    @Override
    public void onDestroy() {
        ExpenseDbSingleInsertServices.getInstance().RemoveExpenseLocalDBAdditionEventDoneListener(this);
        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().RemoveExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(this);
        SettingsChangedServices.getInstance().RemoveSettingsChangedEventDoneListener(this);
        super.onDestroy();
    }

    private void initializeUI(View view) {
        tvCurrentDate = view.findViewById(R.id.tvCurrentDate);
        ibAddExpense = view.findViewById(R.id.ibAddExpense);
        tvTotalExpense = view.findViewById(R.id.tvTotalExpense);
        tvExpenseCurrency = view.findViewById(R.id.tvExpenseCurrency);

        tvCurrentDate.setText(DateAndTimeServices.getInstance().generateCurrentDate());
        tvExpenseCurrency.setText(SharedPreferenceServices.getInstance().getExpenseCurrency());

        String savedDate = SharedPreferenceServices.getInstance().getSavedDateFromSharedPreference();
        if (savedDate.equals(DateAndTimeServices.getInstance().generateCurrentDate())){
            tvTotalExpense.setText(SharedPreferenceServices.getInstance().getTotalAmountFromSharedPreference());

            if (SharedPreferenceServices.getInstance().getLimitSwitch()){
                if (SharedPreferenceServices.getInstance().getExpenseLimit() != null){
                    double dailyExpenseLimit = Double.parseDouble(SharedPreferenceServices.getInstance().getExpenseLimit());
                    double savedTotalAmount = Double.parseDouble(SharedPreferenceServices.getInstance().getTotalAmountFromSharedPreference());
                    if (savedTotalAmount >= dailyExpenseLimit){
                        tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                        tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                    }
                }
            }

        }
        else {
            tvTotalExpense.setText("0.0");
        }

        ExpenseDbRetrieveAllByCurrentDateServices.getInstance().getAllExpensesByCurrentDate(DateAndTimeServices.getInstance().generateCurrentDate());

        ibAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlertDialog.showAddExpenseAlertDialog(getContext(), getActivity(), new AddExpenseButtonClickListener() {
                    @Override
                    public void onAddExpenseButtonClicked(double amount, String category, String details) {
                        //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                        ExpenseModel expenseModel = new ExpenseModel(amount,
                                category,
                                details,
                                DateAndTimeServices.getInstance().generateCurrentDate(),
                                DateAndTimeServices.getInstance().generateCurrentTime());
                        ExpenseDbSingleInsertServices.getInstance().insertSingleExpense(expenseModel);
                    }
                });
            }
        });
    }



    @Override
    public void expenseAddedSuccessfully() {
        Toasty.success(getContext(), "Expense Added Successfully!", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void expenseAdditionFailure() {
        Toasty.error(getContext(), "Expense Addition Failed!", Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void expenseGetSuccessfully(List<ExpenseModel> expenseModels) {
        double totalExpense = 0;
        for (int i = 0; i < expenseModels.size(); i++){
            totalExpense = totalExpense + expenseModels.get(i).getExpenseAmount();
        }
        tvTotalExpense.setText(String.valueOf(totalExpense));
        SharedPreferenceServices.getInstance().setTotalAmountText(String.valueOf(totalExpense));
        SharedPreferenceServices.getInstance().setCurrentDate(DateAndTimeServices.getInstance().generateCurrentDate());

        if (SharedPreferenceServices.getInstance().getLimitSwitch()){
            if (SharedPreferenceServices.getInstance().getExpenseLimit() != null){
                double dailyExpenseLimit = Double.parseDouble(SharedPreferenceServices.getInstance().getExpenseLimit());
                if (totalExpense >= dailyExpenseLimit){
                    tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                    tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                }
                else {
                    tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
                    tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
                }
            }
        }
        else {
            tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
            tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
        }

    }

    @Override
    public void expenseGetFailed() {
        tvTotalExpense.setText("0.0");
    }

    @Override
    public void onSettingsChanged() {
        if (SharedPreferenceServices.getInstance().getLimitSwitch()){
            if (SharedPreferenceServices.getInstance().getExpenseLimit() != null){
                double dailyExpenseLimit = Double.parseDouble(SharedPreferenceServices.getInstance().getExpenseLimit());
                double savedTotalAmount = Double.parseDouble(SharedPreferenceServices.getInstance().getTotalAmountFromSharedPreference());
                if (savedTotalAmount >= dailyExpenseLimit){
                    tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                    tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextRed));
                }
                else {
                    tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
                    tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
                }
            }
        }
        else {
            tvTotalExpense.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
            tvExpenseCurrency.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextDefault));
        }

        tvExpenseCurrency.setText(SharedPreferenceServices.getInstance().getExpenseCurrency());
    }
}
