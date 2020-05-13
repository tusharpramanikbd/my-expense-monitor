package com.tushar.own.myexpensemonitor.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceServices {


    private static SharedPreferenceServices sharedPreferenceServices = new SharedPreferenceServices();
    private Activity activity;

    public static SharedPreferenceServices getInstance() {
        if(sharedPreferenceServices ==null){
            Class clazz = SharedPreferenceServices.class;
            synchronized (clazz){
                sharedPreferenceServices = new SharedPreferenceServices();
            }
        }

        return sharedPreferenceServices;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setTotalAmountText(String totalAmount){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("total_expense_amount", totalAmount);
        editor.commit();
    }

    public void setCurrentDate(String date){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_date", date);
        editor.commit();
    }

    public String getTotalAmountFromSharedPreference(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("total_expense_amount", "null");
    }

    public String getSavedDateFromSharedPreference(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("current_date", "null");

    }

    public void setLimitAndCurrency(String limit, String currency){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("expense_limit", limit);
        editor.putString("currency", currency);
        editor.commit();
    }

    public String getExpenseLimit(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("expense_limit", null);
    }

    public String getExpenseCurrency(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("currency", "Dollar");
    }

    public void setLimitSwitch(boolean limitSwitch){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("expense_limit_switch", limitSwitch);
        editor.commit();
    }

    public boolean getLimitSwitch(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean("expense_limit_switch", false);
    }


}
