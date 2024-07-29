package com.example.Adhiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Adhiya.modal.SpinnerModal;
import com.example.splash.R;

import java.util.ArrayList;

public class MySpinnerAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<SpinnerModal> myObjs;


    public MySpinnerAdapter(Context context, int textViewResourceId,
                            ArrayList<SpinnerModal> myObjs) {
        super(context, textViewResourceId, myObjs);
        this.context = context;
        this.myObjs = myObjs;
    }

    public int getCount(){
        return myObjs.size();
    }

    public SpinnerModal getItem(int position){
        return myObjs.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text1);
        textViewName.setText(myObjs.get(position).getText());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text1);
        textViewName.setText(myObjs.get(position).getText());
        return convertView;
    }
}

