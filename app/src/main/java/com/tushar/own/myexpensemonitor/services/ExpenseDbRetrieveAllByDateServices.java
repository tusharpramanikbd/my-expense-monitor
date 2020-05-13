package com.tushar.own.myexpensemonitor.services;

import android.annotation.SuppressLint;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllByDateEventListener;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbRetrieveAllByDateServices {

    private List<ExpenseLocalDBRetrieveAllByDateEventListener> expenseLocalDBRetrieveAllByDateEventListeners = new ArrayList<>();

    private static ExpenseDbRetrieveAllByDateServices expenseDbRetrieveAllByDateServices = new ExpenseDbRetrieveAllByDateServices();

    public static ExpenseDbRetrieveAllByDateServices getInstance() {
        if(expenseDbRetrieveAllByDateServices ==null){
            Class clazz = ExpenseDbRetrieveAllByDateServices.class;
            synchronized (clazz){
                expenseDbRetrieveAllByDateServices = new ExpenseDbRetrieveAllByDateServices();
            }
        }

        return expenseDbRetrieveAllByDateServices;
    }

    public void AddExpenseLocalDBRetrieveAllByDateEventDoneListener(ExpenseLocalDBRetrieveAllByDateEventListener listener){
        this.expenseLocalDBRetrieveAllByDateEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBRetrieveAllByDateEventDoneListener(ExpenseLocalDBRetrieveAllByDateEventListener listener){
        this.expenseLocalDBRetrieveAllByDateEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByDateEventDone(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByDateEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByDateEventListeners.get(i).expenseGetSuccessfully(expenseModels);
        }
    }

    private void updateViewOnExpenseLocalDBRetrieveAllByDateEventFailed() {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllByDateEventListeners.size(); i++){
            this.expenseLocalDBRetrieveAllByDateEventListeners.get(i).expenseGetFailed();
        }
    }

    public void getAllExpensesByDate(String date){
        final Disposable subscribe = AppDatabase.getInstance().expenseDao().getAllExpensesByDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseModel>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<ExpenseModel> expenseModels) throws Exception {
                        if (expenseModels.size() > 0) {
                            updateViewOnExpenseLocalDBRetrieveAllByDateEventDone(expenseModels);
                        } else {
                            updateViewOnExpenseLocalDBRetrieveAllByDateEventFailed();
                        }
                    }
                });
    }
}
