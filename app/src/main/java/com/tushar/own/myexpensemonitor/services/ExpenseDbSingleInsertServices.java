package com.tushar.own.myexpensemonitor.services;

import com.tushar.own.myexpensemonitor.database.AppDatabase;
import com.tushar.own.myexpensemonitor.listeners.ExpenseLocalDBAdditionEventListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDbSingleInsertServices {

    private List<ExpenseLocalDBAdditionEventListener> expenseLocalDBAdditionEventListenerList = new ArrayList<>();

    private static ExpenseDbSingleInsertServices expenseDbSingleInsertServices = new ExpenseDbSingleInsertServices();

    public static ExpenseDbSingleInsertServices getInstance() {
        if(expenseDbSingleInsertServices ==null){
            Class clazz = ExpenseDbSingleInsertServices.class;
            synchronized (clazz){
                expenseDbSingleInsertServices = new ExpenseDbSingleInsertServices();
            }
        }

        return expenseDbSingleInsertServices;
    }

    public void AddExpenseLocalDBAdditionEventDoneListener(ExpenseLocalDBAdditionEventListener listener){
        this.expenseLocalDBAdditionEventListenerList.add(listener);
    }

    public void RemoveExpenseLocalDBAdditionEventDoneListener(ExpenseLocalDBAdditionEventListener listener){
        this.expenseLocalDBAdditionEventListenerList.remove(listener);
    }

    private void updateViewOnExpenseLocalDBAdditionEventDone() {
        for (int i = 0; i < this.expenseLocalDBAdditionEventListenerList.size(); i++){
            this.expenseLocalDBAdditionEventListenerList.get(i).expenseAddedSuccessfully();
        }
    }

    private void updateViewOnExpenseLocalDBAdditionEventFailed() {
        for (int i = 0; i < this.expenseLocalDBAdditionEventListenerList.size(); i++){
            this.expenseLocalDBAdditionEventListenerList.get(i).expenseAdditionFailure();
        }
    }

    public void insertSingleExpense(final ExpenseModel expenseModel){
        AppDatabase.getInstance().expenseDao().insertExpense(expenseModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long l) {
                        updateViewOnExpenseLocalDBAdditionEventDone();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateViewOnExpenseLocalDBAdditionEventFailed();
                    }
                });
    }
}
