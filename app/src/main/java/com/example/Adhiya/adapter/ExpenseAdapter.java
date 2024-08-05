package com.example.Adhiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.splash.R;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<ExpenseModal> {
    public ExpenseAdapter(Context context, List<ExpenseModal> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        tvName.setText(user.getExpenseReason());
        tvHome.setText(user.getAmount());
        return convertView;

    }

}