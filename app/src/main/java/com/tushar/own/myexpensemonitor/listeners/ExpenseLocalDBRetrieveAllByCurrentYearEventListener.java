package com.tushar.own.myexpensemonitor.listeners;

import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.List;

public interface ExpenseLocalDBRetrieveAllByCurrentYearEventListener {

    void expenseGetByYearSuccessfully(List<ExpenseModel> expenseModels);
    void expenseGetByYearFailed();

}
