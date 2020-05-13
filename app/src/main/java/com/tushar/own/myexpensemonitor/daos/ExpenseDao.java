package com.tushar.own.myexpensemonitor.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tushar.own.myexpensemonitor.models.ExpenseModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ExpenseDao {

    @Query("select * from expense_table")
    Flowable<List<ExpenseModel>> getAllExpenses();

    @Query("select * from expense_table where date=:date")
    Flowable<List<ExpenseModel>> getAllExpensesByDate(String date);

    @Query("select * from expense_table where date=:date")
    Flowable<List<ExpenseModel>> getAllExpensesByCurrentDate(String date);

    @Query("select * from expense_table where date like '%' || :month || '%'")
    Flowable<List<ExpenseModel>> getAllExpensesByCurrentMonth(String month);

    @Query("select * from expense_table where date like '%' || :year || '%'")
    Flowable<List<ExpenseModel>> getAllExpensesByCurrentYear(String year);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertExpense(ExpenseModel expenseModel);

    @Update
    Single<Integer> updateExpense(ExpenseModel expenseModel);

    @Delete
    Single<Integer> deleteExpense(ExpenseModel expenseModel);
}
