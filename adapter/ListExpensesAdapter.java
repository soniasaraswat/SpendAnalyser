package com.spendanalyzer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spendanalyzer.R;
import com.spendanalyzer.model.Expenses;

import java.util.List;

/**
 * Created by Sonia.
 */

public class ListExpensesAdapter extends BaseAdapter {

    private Context context;
    private List<Expenses> expensesList;

    public ListExpensesAdapter(Context context, List<Expenses> expensesList) {
        this.context = context;
        this.expensesList = expensesList;
    }

    @Override
    public int getCount() {
        return expensesList.size();
    }

    @Override
    public Object getItem(int i) {
        return expensesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return expensesList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.item_listview,null);
        TextView tvItemMoney = (TextView) v.findViewById(R.id.tvItemMoney);
        TextView tvItemCat = (TextView) v.findViewById(R.id.tvItemCat);
        TextView tvItemDate = (TextView) v.findViewById(R.id.tvItemDate);
        TextView tvspend = (TextView) v.findViewById(R.id.spend);

        if (expensesList.get(i).getMoney()!=0){
            tvItemMoney.setVisibility(View.VISIBLE);
            tvItemMoney.setText(String.valueOf(expensesList.get(i).getMoney()) + " Rs");
        }else {
            tvItemMoney.setVisibility(View.GONE);
        }
        if (expensesList.get(i).getSpend()!=0){
            tvspend.setVisibility(View.VISIBLE);
            tvspend.setText(String.valueOf(expensesList.get(i).getSpend()) + " Rs");
        }else {
            tvspend.setVisibility(View.GONE);
        }


        tvItemCat.setText(expensesList.get(i).getCategory());
        tvItemDate.setText(expensesList.get(i).getDate());

        return v;
    }
}
