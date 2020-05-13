package com.tushar.own.myexpensemonitor.services;

import android.annotation.SuppressLint;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByCurrentYearEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbRetrieveAllByCurrentYearServices {

    private List<ExpenseLocalDBRetrieveAllByCurrentYearEventListener> expenseLocalDBRetrieveAllByCurrentYearEventListeners = new ArrayList<>();

    private static ExpenseDbRetrieveAllByCurrentYearServices expenseDbRetrieveAllByCurrentYearServices = new ExpenseDbRetrieveAllByCurrentYearServices();

    public static ExpenseDbRetrieveAllByCurrentYearServices getInstance() {
        if(expenseDbRetrieveAllByCurrentYearServices ==null){
            Class clazz = ExpenseDbRetrieveAllByCurrentYearServices.class;
            synchronized (clazz){
                expenseDbRetrieveAllByCurrentYearServices = new ExpenseDbRetrieveAllByCurrentYearServices();
            }
        }

        return expenseDbRetrieveAllByCurrentYearServices;
    }

    public void AddExpenseLocalDBRetrieveAllByCurrentYearEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentYearEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBRetrieveAllByCurrentYearEventDoneListener(ExpenseLocalDBRetrieveAllByCurrentYearEventListener listener){
        this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentYearEventDone(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.get(i).expenseGetByYearSuccessfully(expenseModels);
        }
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByCurrentYearEventFailed() {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByCurrentYearEventListeners.get(i).expenseGetByYearFailed();
        }
    }

    public void getAllExpensesByCurrentYear(String year){
        final Disposable subscribe = AppDatabase.getInstance().expenseDao().getAllExpensesByCurrentYear(year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseModel>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<ExpenseModel> expenseModels) throws Exception {
                        if (expenseModels.size() > 0) {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentYearEventDone(expenseModels);
                        } else {
                            updateViewOnExpenseLocalDBRetrieveAllByCurrentYearEventFailed();
                        }
                    }
                });
    }
}
