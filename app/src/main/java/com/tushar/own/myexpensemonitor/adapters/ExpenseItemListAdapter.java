package com.tushar.own.myexpensemonitor.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.listeners.AddExpenseButtonClickListener;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;
import com.tushar.own.myexpensemonitor.services.ExpenseDbSingleDeleteServices;
import com.tushar.own.myexpensemonitor.services.ExpenseDbSingleUpdateServices;
import com.tushar.own.myexpensemonitor.utils.MyAlertDialog;

import java.util.ArrayList;

public class ExpenseItemListAdapter extends RecyclerView.Adapter<ExpenseItemListAdapter.ExpenseItemListViewHolder> {

    private Context mContext;
    private Activity activity;
    private ArrayList<ExpenseModel> expenseModelArrayList;

    public ExpenseItemListAdapter(Context context, Activity activity, ArrayList<ExpenseModel> ArrayList) {
        this.mContext = context;
        this.expenseModelArrayList = ArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExpenseItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_recyclearview, parent, false);
        return new ExpenseItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseItemListViewHolder holder, final int position) {

        holder.ivItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.expense_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_edit:
                                MyAlertDialog.showUpdateExpenseAlertDialog(
                                        expenseModelArrayList.get(position).getExpenseAmount(),
                                        expenseModelArrayList.get(position).getExpenseCategory(),
                                        expenseModelArrayList.get(position).getExpenseDetails(),
                                        mContext, activity, new AddExpenseButtonClickListener() {
                                    @Override
                                    public void onAddExpenseButtonClicked(double amount, String category, String details) {
                                        ExpenseModel expenseModel = new ExpenseModel(
                                                expenseModelArrayList.get(position).getExpenseId(),
                                                amount,
                                                category,
                                                details,
                                                expenseModelArrayList.get(position).getExpenseDate(),
                                                expenseModelArrayList.get(position).getExpenseTime());
                                        ExpenseDbSingleUpdateServices.getInstance().updateSingleExpense(expenseModel);
                                    }
                                });
                                return true;
                            case R.id.item_delete:
                                ExpenseDbSingleDeleteServices.getInstance().deleteSingleExpense(expenseModelArrayList.get(position));
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        switch (this.expenseModelArrayList.get(position).getExpenseCategory()) {
            case "Housing":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_home_black_24dp);
                break;
            case "Transportation":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_trasportation_black_24dp);
                break;
            case "Food":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_local_dining_black_24dp);
                break;
            case "Utilities":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_utilities);
                break;
            case "Clothing":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_clothings);
                break;
            case "Medical":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_healthcare);
                break;
            case "Household Items":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_household_supplies);
                break;
            case "Education":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_education);
                break;
            case "Entertainment":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_entertainment);
                break;
            case "Misc":
                holder.ivCategory.setBackgroundResource(R.drawable.ic_misc_black_24dp);
                break;
            default:
                holder.ivCategory.setBackgroundResource(R.drawable.ic_add_black_24dp);
        }

        if (expenseModelArrayList.get(position).getExpenseCategory().equals("")){
            holder.recyclerItemCategory.setText(R.string.no_category_added);
        }
        else {
            holder.recyclerItemCategory.setText(expenseModelArrayList.get(position).getExpenseCategory());
        }
        if (expenseModelArrayList.get(position).getExpenseDetails().equals("")){
            holder.recyclerItemDescription.setText(R.string.no_description_added);
        }
        else {
            holder.recyclerItemDescription.setText(expenseModelArrayList.get(position).getExpenseDetails());
        }
        holder.recyclerItemAmount.setText(String.valueOf(expenseModelArrayList.get(position).getExpenseAmount()));
        holder.recyclerItemTime.setText(expenseModelArrayList.get(position).getExpenseTime());

    }


    @Override
    public int getItemCount() {
        return expenseModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ExpenseItemListViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView recyclerItemCategory, recyclerItemAmount, recyclerItemTime, recyclerItemDescription;
        AppCompatImageView ivCategory, ivItemMenu;

        ExpenseItemListViewHolder(View itemView) {
            super(itemView);

            ivCategory = itemView.findViewById(R.id.ivCategory);
            ivItemMenu = itemView.findViewById(R.id.ivItemMenu);

            recyclerItemCategory = itemView.findViewById(R.id.tvCategory);
            recyclerItemAmount = itemView.findViewById(R.id.tvAmount);
            recyclerItemTime = itemView.findViewById(R.id.tvTime);
            recyclerItemDescription = itemView.findViewById(R.id.tvDescription);

        }
    }
}

