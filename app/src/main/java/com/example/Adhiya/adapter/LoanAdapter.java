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
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adhiya.BorrowerAddActivity;
import com.example.Adhiya.LoanAddActivity;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.LoanStatus;
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

public class LoanAdapter extends ArrayAdapter<BorrowerLoanModal> {
    Context context;
    BorrowerLoanModal user;

    public List<BorrowerLoanModal> orig;
    List<BorrowerLoanModal> users;
    public LoanAdapter(Context context, List<BorrowerLoanModal> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<BorrowerLoanModal> results = new ArrayList<BorrowerLoanModal>();
                if (orig == null)
                    orig = users;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final BorrowerLoanModal g : orig) {
                            if (g.getBorrowerName().toLowerCase()
                                    .contains(constraint.toString()) || g.getBorrowerId().contains(constraint.toString().toUpperCase())) {
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
                users = (ArrayList<BorrowerLoanModal>) results.values;
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
    public BorrowerLoanModal getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.loan_item_list, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        TextView status = (TextView) convertView.findViewById(R.id.tvLne);
        ImageButton tvedit = (ImageButton) convertView.findViewById(R.id.editbutton);
        ImageButton loanEdit = (ImageButton) convertView.findViewById(R.id.loanedit);

        if(user.isCollectionStatus()==true){
            loanEdit.setVisibility(View.INVISIBLE);
        }else{
            tvedit.setVisibility(View.INVISIBLE);
        }
        loanEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanAddActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("borrower",(Serializable)user);
                intent.putExtra("EDIT",args);
                context.startActivity(intent);
            }
        });
        if(!user.getLoanStatus().equals("Active")){

            loanEdit.setVisibility(View.INVISIBLE);
        }

        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
              SingleSelectionDialog(position);
            }
        });


        tvName.setText(user.getBorrowerName()+"");
        tvHome.setText(user.getLoanAmount()+"");
        status.setText(user.getLoanStatus());
        if(user.getLoanStatusId() == 1){
            status.setTextColor(Color.GREEN);
        }
        if(user.getLoanStatusId() == 2){
            status.setTextColor(Color.RED);
        }
        if(user.getLoanStatusId() == 3){
            status.setTextColor(Color.BLUE);
        }
        return convertView;

    }


    String[] status;
    private void SingleSelectionDialog(int pos) {
        BorrowerLoanModal userselected =  users.get(pos);
        status = context.getResources().getStringArray(R.array.fruit_name);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Change Loan Status");
        dialogBuilder.setSingleChoiceItems(status, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                userselected.setLoanStatus(status[i]);
                if(status[i].equals("Active")){
                    userselected.setLoanStatusId(1);
                }
                if(status[i].equals("Closed")){
                    userselected.setLoanStatusId(2);
                }
                if(status[i].equals("Default")){
                    userselected.setLoanStatusId(4);
                }
                //select = status[i];
            }
        });
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeStatus(userselected);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.create().show();
    }

    private void changeStatus(BorrowerLoanModal userselected){

        LoanStatus loanStatus = new LoanStatus();
        loanStatus.setBorrowerLoanId(userselected.getLoanId());
        loanStatus.setLoanStatusId(userselected.getLoanStatusId());
        RetrofitAPI retrofitAPI = ApiClient.getApiClient();
        Dialog dialog = ProgressUtil.showProgress((Activity) context);

        Call<ResponseModal> call = retrofitAPI.loanStatusChange(loanStatus);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                dialog.dismiss();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();

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