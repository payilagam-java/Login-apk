package com.example.Adhiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.splash.R;

import java.util.ArrayList;

public class MySpinnerAdapter extends ArrayAdapter implements Filterable {

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

    private ArrayList<SpinnerModal> orig;
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SpinnerModal> results = new ArrayList<SpinnerModal>();
                if (orig == null)
                    orig = myObjs;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final SpinnerModal g : orig) {
                            if (g.getText().toLowerCase()
                                    .contains(constraint.toString())) {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                myObjs = (ArrayList<SpinnerModal>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

