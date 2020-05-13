package com.tushar.own.myexpensemonitor.services;

import android.annotation.SuppressLint;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentMonthEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbRetrieveAllByCurrentMonthServices {

    private List<ExpenseLocalDBRetrieveAllByCurrentMonthEventListener> expenseLocalDBRetrieveAllByCurrentMonthEventListeners = new ArrayList<>();

    private static ExpenseDbRetrieveAllByCurrentMonthServices expenseDbRetrieveAllByCurrentMonthServices = new ExpenseDbRetrieveAllByCurrentMonthServices();

    public static ExpenseDbRetrieveAllByCurrentMonthServices getInstance() {
        if(expenseDbRetrieveAllByCurrentMonthServices ==null){
            Class clazz = ExpenseDbRetrieveAllByCurrentMonthServices.class;
            synchronized (clazz){
                expenseDbRetrieveAllByCurrentMonthServices = new ExpenseDbRetrieveAllByCurrentMonthServices();
            }
        }

        return expenseDbRetrieveAllByCurrentMonthServices;
    }

    public void AddExpenseLocalDBRetrieveAllByCurrentMonthEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentMonthEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBRetrieveAllByCurrentMonthEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentMonthEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentMonthEventDone(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.get(i).expenseGetByMonthSuccessfully(expenseModels);
        }
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentMonthEventFailed() {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentMonthEventListeners.get(i).expenseGetByMonthFailed();
        }
    }

    public void getAllExpensesByCurrentMonth(String month){
        final Disposable subscribe = AppDatabase.getInstance().expenseDao().getAllExpensesByCurrentMonth(month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseModel>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<ExpenseModel> expenseModels) throws Exception {
                        if (expenseModels.size() > 0) {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentMonthEventDone(expenseModels);
                        } else {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentMonthEventFailed();
                        }
                    }
                });
    }
}
