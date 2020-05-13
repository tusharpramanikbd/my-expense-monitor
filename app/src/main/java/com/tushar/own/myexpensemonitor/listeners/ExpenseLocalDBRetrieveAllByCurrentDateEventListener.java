package com.tushar.own.myexpensemonitor.listeners;

import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.List;

public interface ExpenseLocalDBRetrieveAllByCurrentDateEventListener {

    void expenseGetSuccessfully(List<ExpenseModel> expenseModels);
    void expenseGetFailed();

}
