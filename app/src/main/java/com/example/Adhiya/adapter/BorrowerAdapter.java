package com.example.Adhiya.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adhiya.BorrowerAddActivity;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerAdapter extends ArrayAdapter<BorrowerModal> implements Filterable {
    public List<BorrowerModal> orig;
    List<BorrowerModal> users;
    Context context;
     Switch btn;
    public BorrowerAdapter(Context context, List<BorrowerModal> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<BorrowerModal> results = new ArrayList<BorrowerModal>();
                if (orig == null)
                    orig = users;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final BorrowerModal g : orig) {
                            if (g.getFirstName().toLowerCase()
                                    .contains(constraint.toString()) || g.getBorrowerId().contains(constraint.toString().toUpperCase()) || g.getMobileNubmer().toUpperCase()
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
                users = (ArrayList<BorrowerModal>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public BorrowerModal getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BorrowerModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        Switch active = (Switch) convertView.findViewById(R.id.switch1);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        TextView tvLine = (TextView) convertView.findViewById(R.id.tvLne);
        ImageButton tvedit = (ImageButton) convertView.findViewById(R.id.editbutton);

        tvName.setText(user.getBorrowerId() + " - " + user.getFirstName() + "  ");
        tvHome.setText(user.getMobileNubmer());
        tvLine.setText(user.getLineName());
        String status = user.getBorrowerStatus().toString().trim();
        if (status.equals("Active")) {
            active.setChecked(true);
        } else {
            active.setChecked(false);
        }
        active.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               btn = (Switch) v;
                final boolean switchChecked = btn.isChecked();

                if (btn.isChecked()) {
                    btn.setChecked(false);
                } else {
                    btn.setChecked(true);
                }

                String message = "Are you sure you want to Inactive the borrower?";
                if (!btn.isChecked()) {
                    message = "Are you sure you want to active the borrower?";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context); // Change "this" to `getActivity()` if you're using this on a fragment
                builder.setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                // "Yes" button was clicked

                                getBorrowerList(String.valueOf(user.getId()),switchChecked);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });


        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BorrowerAddActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("borrower",(Serializable)user);
                intent.putExtra("EDIT",args);
                context.startActivity(intent);
            }
        });

        return convertView;

    }

    private void getBorrowerList(String body,boolean switchChecked){

       RetrofitAPI retrofitAPI = ApiClient.getApiClient();
        Dialog dialog = ProgressUtil.showProgress((Activity) context);

        Call<ResponseModal> call = retrofitAPI.deleteBorrower(body);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                dialog.dismiss();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    if(responseFromAPI.getStatus() == 1){
                        if (switchChecked) {
                            btn.setChecked(true);
                        } else {
                            btn.setChecked(false);
                        }
                    }
                    Toast.makeText(context, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(context, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}