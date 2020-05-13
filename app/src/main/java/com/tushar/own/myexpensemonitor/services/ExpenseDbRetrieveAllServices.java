package com.tushar.own.myexpensemonitor.services;

import android.annotation.SuppressLint;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBRetrieveAllEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbRetrieveAllServices {

    private List<ExpenseLocalDBRetrieveAllEventListener> expenseLocalDBRetrieveAllEventListenerList = new ArrayList<>();

    private static ExpenseDbRetrieveAllServices expenseDbSingleInsertServices = new ExpenseDbRetrieveAllServices();

    public static ExpenseDbRetrieveAllServices getInstance() {
        if(expenseDbSingleInsertServices ==null){
            Class clazz = ExpenseDbRetrieveAllServices.class;
            synchronized (clazz){
                expenseDbSingleInsertServices = new ExpenseDbRetrieveAllServices();
            }
        }

        return expenseDbSingleInsertServices;
    }

    public void AddExpenseLocalDBRetrieveAllEventDoneListener(ExpenseLocalDBRetrieveAllEventListener listener){
        this.expenseLocalDBRetrieveAllEventListenerList.add(listener);
    }

    public void RemoveExpenseLocalDBRetrieveAllEventDoneListener(ExpenseLocalDBRetrieveAllEventListener listener){
        this.expenseLocalDBRetrieveAllEventListenerList.remove(listener);
    }

    private void updateViewOnExpenseLocalDBRetrieveAllEventDone(List<ExpenseModel> expenseModels) {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllEventListenerList.size(); i++){
            this.expenseLocalDBRetrieveAllEventListenerList.get(i).expenseGetSuccessfully(expenseModels);
        }
    }

    private void updateViewOnExpenseLocalDBRetrieveAllEventFailed() {
        for (int i = 0; i < this.expenseLocalDBRetrieveAllEventListenerList.size(); i++){
            this.expenseLocalDBRetrieveAllEventListenerList.get(i).expenseGetFailed();
        }
    }

    public void getAllExpenses(){
        final Disposable subscribe = AppDatabase.getInstance().expenseDao().getAllExpenses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseModel>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<ExpenseModel> expenseModels) throws Exception {
                        if (expenseModels.size() > 0) {
                            updateViewOnExpenseLocalDBRetrieveAllEventDone(expenseModels);
                        } else {
                            updateViewOnExpenseLocalDBRetrieveAllEventFailed();
                        }
                    }
                });
    }
}
