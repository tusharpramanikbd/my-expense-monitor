package com.tushar.own.myexpensemonitor.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class ExpenseModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int expenseId;

    @ColumnInfo(name = "amount")
    private double expenseAmount;

    @ColumnInfo(name = "category")
    private String expenseCategory;

    @ColumnInfo(name = "details")
    private String expenseDetails;

    @ColumnInfo(name = "date")
    private String expenseDate;

    @ColumnInfo(name = "time")
    private String expenseTime;

    public ExpenseModel(double expenseAmount, String expenseCategory, String expenseDetails, String expenseDate, String expenseTime) {
        this.expenseAmount = expenseAmount;
        this.expenseCategory = expenseCategory;
        this.expenseDetails = expenseDetails;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
    }

    @Ignore
    public ExpenseModel(int id, double expenseAmount, String expenseCategory, String expenseDetails, String expenseDate, String expenseTime) {
        this.expenseId = id;
        this.expenseAmount = expenseAmount;
        this.expenseCategory = expenseCategory;
        this.expenseDetails = expenseDetails;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }
}
