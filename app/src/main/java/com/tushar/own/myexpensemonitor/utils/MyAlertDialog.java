package com.tushar.own.myexpensemonitor.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.AddExpenseButtonClickListener;
import com.tushar.own.myexpensemonitor.listeners.SettingsButtonClickListener;
import com.tushar.own.myexpensemonitor.services.SharedPreferenceServices;

public class MyAlertDialog {

    public static void showAddExpenseAlertDialog(Context context, Activity activity, final AddExpenseButtonClickListener listener){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_add_expense, null);
        alert.setView(mView);

        String[] COUNTRIES = new String[] {"Housing", "Transportation", "Food", "Utilities",
                "Clothing", "Medical", "Household Items",
                "Education", "Entertainment", "Misc"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        context,
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        final AutoCompleteTextView editTextFilledExposedDropdown =
                mView.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setKeyListener(null);

        final TextInputEditText etExpenseAmount = mView.findViewById(R.id.editTextAmount);
        final TextInputEditText etExpenseDetails = mView.findViewById(R.id.editTextDetails);

        AppCompatButton btnCancel = mView.findViewById(R.id.buttonCancel);
        AppCompatButton btnAdd = mView.findViewById(R.id.buttonAdd);

        final TextInputLayout layoutAmount = mView.findViewById(R.id.textInputLayoutExpenseAmount);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = etExpenseAmount.getText().toString().trim();
                String category = editTextFilledExposedDropdown.getText().toString().trim();
                String details = etExpenseDetails.getText().toString().trim();

                if (amount.equals("")){
                    layoutAmount.setError("You have to add amount");
                }
                else {
                    double amountInNumber = Double.parseDouble(amount);
                    if (amountInNumber <= 0.0){
                        layoutAmount.setError("Invalid amount");
                    }
                    else {
                        alertDialog.dismiss();

                        listener.onAddExpenseButtonClicked(amountInNumber, category, details);
                    }
                }
            }
        });

        alertDialog.show();
    }

    public static void showUpdateExpenseAlertDialog(double amount,
                                                    String category,
                                                    String details,
                                                    Context context,
                                                    Activity activity,
                                                    final AddExpenseButtonClickListener listener){

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_update_expense, null);
        alert.setView(mView);

        String[] COUNTRIES = new String[] {"Housing", "Transportation", "Food", "Utilities",
                "Clothing", "Medical", "Household Items",
                "Education", "Entertainment", "Misc"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        context,
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        final AutoCompleteTextView editTextFilledExposedDropdown =
                mView.findViewById(R.id.filled_exposed_dropdown);

        if (!category.equals("")){
            editTextFilledExposedDropdown.setText(category);
        }

        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setKeyListener(null);

        final TextInputEditText etExpenseAmount = mView.findViewById(R.id.editTextAmount);
        etExpenseAmount.setText(String.valueOf(amount));

        final TextInputEditText etExpenseDetails = mView.findViewById(R.id.editTextDetails);
        if (!details.equals("")){
            etExpenseDetails.setText(details);
        }

        AppCompatButton btnCancel = mView.findViewById(R.id.buttonCancel);
        AppCompatButton btnAdd = mView.findViewById(R.id.buttonAdd);

        final TextInputLayout layoutAmount = mView.findViewById(R.id.textInputLayoutExpenseAmount);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = etExpenseAmount.getText().toString().trim();
                String category = editTextFilledExposedDropdown.getText().toString().trim();
                String details = etExpenseDetails.getText().toString().trim();

                if (amount.equals("")){
                    layoutAmount.setError("You have to add amount");
                }
                else {
                    double amountInNumber = Double.parseDouble(amount);
                    if (amountInNumber <= 0.0){
                        layoutAmount.setError("Invalid amount");
                    }
                    else {
                        alertDialog.dismiss();

                        listener.onAddExpenseButtonClicked(amountInNumber, category, details);
                    }
                }
            }
        });

        alertDialog.show();
    }

    public static void showSettingsAlertDialog(String limit,
                                               String savedCurrency,
                                               Context context,
                                                    Activity activity,
                                                    final SettingsButtonClickListener listener){

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_settings, null);
        alert.setView(mView);

        final TextInputEditText etExpenseLimit = mView.findViewById(R.id.editTextExpenseLimit);
        if (limit == null){
            etExpenseLimit.setText("0.0");
        }
        else {
            etExpenseLimit.setText(limit);
        }

        final SwitchMaterial switchLimit = mView.findViewById(R.id.switchLimit);
        switchLimit.setChecked(SharedPreferenceServices.getInstance().getLimitSwitch());

        if (!SharedPreferenceServices.getInstance().getLimitSwitch()){
            etExpenseLimit.setEnabled(false);
        }
        else {
            etExpenseLimit.setEnabled(true);
        }

        switchLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    etExpenseLimit.setEnabled(true);
                    SharedPreferenceServices.getInstance().setLimitSwitch(true);
                }
                else {
                    etExpenseLimit.setEnabled(false);
                    SharedPreferenceServices.getInstance().setLimitSwitch(false);
                }
            }
        });

        final TextInputEditText etExpenseCurrency = mView.findViewById(R.id.editTextExpenseCurrency);
        etExpenseCurrency.setText(savedCurrency);

        AppCompatButton btnCancel = mView.findViewById(R.id.buttonCancel);
        AppCompatButton btnSave = mView.findViewById(R.id.buttonSave);

        final TextInputLayout layoutLimit = mView.findViewById(R.id.textInputLayoutExpenseLimit);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String limit = etExpenseLimit.getText().toString().trim();
                String currency = etExpenseCurrency.getText().toString().trim();
                String fixedCurrency = "Dollar";

                if(!SharedPreferenceServices.getInstance().getLimitSwitch()){
                    if (currency.equals("")){
                        alertDialog.dismiss();

                        listener.onSettingsButtonClicked(limit, fixedCurrency);
                    }
                    else {
                        alertDialog.dismiss();

                        listener.onSettingsButtonClicked(limit, currency);
                    }
                }
                else {
                    if (Double.parseDouble(limit) <= 0.0){
                        layoutLimit.setError("Invalid Limit");
                    }
                    else {
                        if (currency.equals("")){
                            alertDialog.dismiss();

                            listener.onSettingsButtonClicked(limit, fixedCurrency);
                        }
                        else {
                            alertDialog.dismiss();

                            listener.onSettingsButtonClicked(limit, currency);
                        }
                    }
                }
            }
        });

        alertDialog.show();
    }

    public static void showAboutAlertDialog(Context context, Activity activity){

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_about, null);
        alert.setView(mView);

        AppCompatButton btnCancel = mView.findViewById(R.id.buttonCancel);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
