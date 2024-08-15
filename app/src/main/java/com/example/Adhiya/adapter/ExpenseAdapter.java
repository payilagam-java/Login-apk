package com.example.Adhiya.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.Adhiya.BorrowerAddActivity;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.splash.R;

import java.io.Serializable;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<ExpenseModal> {
    Context context;
    public ExpenseAdapter(Context context, List<ExpenseModal> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        Switch active = (Switch) convertView.findViewById(R.id.switch1);
        active.setVisibility(View.INVISIBLE);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        TextView tvLine = (TextView) convertView.findViewById(R.id.tvLne);
        ImageButton tvedit = (ImageButton) convertView.findViewById(R.id.editbutton);
        tvedit.setVisibility(View.INVISIBLE);

        tvName.setText(user.getLineName());
        tvHome.setText(user.getExpenseReason());
        tvLine.setText(user.getAmount());

//        tvedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BorrowerAddActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("expense",(Serializable)user);
//                intent.putExtra("EDIT",args);
//                context.startActivity(intent);
//            }
//        });
        return convertView;

    }

}