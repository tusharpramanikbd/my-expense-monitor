package com.tushar.own.myexpensemonitor.listeners;

import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.List;

public interface ExpenseLocalDBRetrieveAllByCurrentMonthEventListener {

    void expenseGetByMonthSuccessfully(List<ExpenseModel> expenseModels);
    void expenseGetByMonthFailed();

}
