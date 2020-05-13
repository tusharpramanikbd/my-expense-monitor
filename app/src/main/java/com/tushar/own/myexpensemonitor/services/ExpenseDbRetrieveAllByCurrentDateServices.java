package com.tushar.own.myexpensemonitor.services;

import android.annotation.SuppressLint;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentDateEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbRetrieveAllByCurrentDateServices {

    private List<ExpenseLocalDBRetrieveAllByCurrentDateEventListener> expenseLocalDBRetrieveAllByCurrentDateEventListeners = new ArrayList<>();

    private static ExpenseDbRetrieveAllByCurrentDateServices expenseDbRetrieveAllByCurrentDateServices = new ExpenseDbRetrieveAllByCurrentDateServices();

    public static ExpenseDbRetrieveAllByCurrentDateServices getInstance() {
        if(expenseDbRetrieveAllByCurrentDateServices ==null){
            Class clazz = ExpenseDbRetrieveAllByCurrentDateServices.class;
            synchronized (clazz){
                expenseDbRetrieveAllByCurrentDateServices = new ExpenseDbRetrieveAllByCurrentDateServices();
            }
        }

        return expenseDbRetrieveAllByCurrentDateServices;
    }

    public void AddExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentDateEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBRetrieveAllByCurrentDateEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentDateEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentDateEventDone(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.get(i).expenseGetSuccessfully(expenseModels);
        }
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentDateEventFailed() {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentDateEventListeners.get(i).expenseGetFailed();
        }
    }

    public void getAllExpensesByCurrentDate(String date){
        final Disposable subscribe = AppDatabase.getInstance().expenseDao().getAllExpensesByCurrentDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseModel>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<ExpenseModel> expenseModels) throws Exception {
                        if (expenseModels.size() > 0) {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentDateEventDone(expenseModels);
                        } else {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentDateEventFailed();
                        }
                    }
                });
    }
}
