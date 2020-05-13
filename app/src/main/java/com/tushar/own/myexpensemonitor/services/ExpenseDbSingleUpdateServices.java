package com.tushar.own.myexpensemonitor.services;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBUpdateEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbSingleUpdateServices {

    private List<ExpenseLocalDBUpdateEventListener> expenseLocalDBUpdateEventListeners = new ArrayList<>();

    private static ExpenseDbSingleUpdateServices expenseDbSingleUpdateServices = new ExpenseDbSingleUpdateServices();

    public static ExpenseDbSingleUpdateServices getInstance() {
        if(expenseDbSingleUpdateServices ==null){
            Class clazz = ExpenseDbSingleUpdateServices.class;
            synchronized (clazz){
                expenseDbSingleUpdateServices = new ExpenseDbSingleUpdateServices();
            }
        }

        return expenseDbSingleUpdateServices;
    }

    public void AddExpenseLocalDBUpdateEventDoneListener(ExpenseLocalDBUpdateEventListener listener){
        this.expenseLocalDBUpdateEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBUpdateEventDoneListener(ExpenseLocalDBUpdateEventListener listener){
        this.expenseLocalDBUpdateEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBUpdateEventDone() {
        for (int i = 0; i < this.expenseLocalDBUpdateEventListeners.size(); i++){
            this.expenseLocalDBUpdateEventListeners.get(i).expenseUpdateSuccessfully();
        }
    }

    private void updateViewOnExpenseLocalDBUpdateEventFailed() {
        for (int i = 0; i < this.expenseLocalDBUpdateEventListeners.size(); i++){
            this.expenseLocalDBUpdateEventListeners.get(i).expenseUpdateFailed();
        }
    }

    public void updateSingleExpense(final ExpenseModel expenseModel){
        AppDatabase.getInstance().expenseDao().updateExpense(expenseModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        updateViewOnExpenseLocalDBUpdateEventDone();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateViewOnExpenseLocalDBUpdateEventFailed();
                    }
                });
    }
}
