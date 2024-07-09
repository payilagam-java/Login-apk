package com.example.Adhiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Adhiya.modal.BorrowerModal;
import com.example.splash.R;

import java.util.ArrayList;
import java.util.List;

public class BorrowerAdapter extends ArrayAdapter<BorrowerModal> {
    public BorrowerAdapter(Context context, List<BorrowerModal> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BorrowerModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        tvName.setText(user.getFirstName()+" "+user.getLastName());
        tvHome.setText(user.getMobileNubmer());
        return convertView;

    }

}