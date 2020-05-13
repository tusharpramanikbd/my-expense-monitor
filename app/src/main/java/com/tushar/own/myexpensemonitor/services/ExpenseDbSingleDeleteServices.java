package com.tushar.own.myexpensemonitor.services;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBDeleteEventListener;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBUpdateEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbSingleDeleteServices {

    private List<ExpenseLocalDBDeleteEventListener> expenseLocalDBDeleteEventListeners = new ArrayList<>();

    private static ExpenseDbSingleDeleteServices expenseDbSingleDeleteServices = new ExpenseDbSingleDeleteServices();

    public static ExpenseDbSingleDeleteServices getInstance() {
        if(expenseDbSingleDeleteServices ==null){
            Class clazz = ExpenseDbSingleDeleteServices.class;
            synchronized (clazz){
                expenseDbSingleDeleteServices = new ExpenseDbSingleDeleteServices();
            }
        }

        return expenseDbSingleDeleteServices;
    }

    //No need to add this listener in the fragment because after delete retrieve all will be called automatically
    public void AddExpenseLocalDBDeleteEventDoneListener(ExpenseLocalDBDeleteEventListener listener){
        this.expenseLocalDBDeleteEventListeners.add(listener);
    }

    public void RemoveExpenseLocalDBDeleteEventDoneListener(ExpenseLocalDBDeleteEventListener listener){
        this.expenseLocalDBDeleteEventListeners.remove(listener);
    }

    private void updateViewOnExpenseLocalDBDeleteEventDone() {
        for (int i = 0; i < this.expenseLocalDBDeleteEventListeners.size(); i++){
            this.expenseLocalDBDeleteEventListeners.get(i).expenseDeleteSuccessfully();
        }
    }

    private void updateViewOnExpenseLocalDBDeleteEventFailed() {
        for (int i = 0; i < this.expenseLocalDBDeleteEventListeners.size(); i++){
            this.expenseLocalDBDeleteEventListeners.get(i).expenseDeleteFailed();
        }
    }

    public void deleteSingleExpense(final ExpenseModel expenseModel){
        AppDatabase.getInstance().expenseDao().deleteExpense(expenseModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        updateViewOnExpenseLocalDBDeleteEventDone();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateViewOnExpenseLocalDBDeleteEventFailed();
                    }
                });
    }
}
