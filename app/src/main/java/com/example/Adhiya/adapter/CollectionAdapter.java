package com.example.Adhiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.splash.R;

import java.util.List;

public class CollectionAdapter extends ArrayAdapter<CollectionModal> {
    public CollectionAdapter(Context context, List<CollectionModal> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectionModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        tvName.setText(user.getBorrowerName());
        tvHome.setText(user.getPayableAmount());
        return convertView;

    }

}