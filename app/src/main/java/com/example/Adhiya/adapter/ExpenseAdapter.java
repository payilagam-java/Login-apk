package com.example.Adhiya.adapter;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.Adhiya.ExpenseActivity;
import com.example.Adhiya.ExpenseAddActivity;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseAdapter extends ArrayAdapter<ExpenseModal>implements Filterable {
    Context context;
    public List<ExpenseModal> orig;
    List<ExpenseModal> users;
    public ExpenseAdapter(Context context, List<ExpenseModal> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<ExpenseModal> results = new ArrayList<ExpenseModal>();
                if (orig == null)
                    orig = users;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ExpenseModal g : orig) {
                            if (g.getLineName().toLowerCase()
                                    .contains(constraint.toString()) || g.getDateOfExpense().toUpperCase()
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
                users = (ArrayList<ExpenseModal>) results.values;
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
    public ExpenseModal getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_item_list, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        TextView tvLine = (TextView) convertView.findViewById(R.id.tvLne);
        TextView tvdate = (TextView) convertView.findViewById(R.id.tvdate);
        ImageButton tvedit = (ImageButton) convertView.findViewById(R.id.editbutton);
        ImageButton tvdelete = (ImageButton) convertView.findViewById(R.id.deletebutton);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()).trim();
        String eDate[] = user.getDateOfExpense().toString().split("T");
//        if(date.equals(eDate[0].trim())){
//            tvedit.setVisibility(View.VISIBLE);
//        }else{
//            tvedit.setVisibility(View.INVISIBLE);
//        }

        tvName.setText(user.getLineName());
        tvdate.setText(user.getDateOfExpense().split("T")[0]);
        tvHome.setText(user.getExpenseReason());
        tvLine.setText(user.getAmount());

        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExpenseAddActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("expense",(Serializable)user);
                intent.putExtra("EDIT",args);
                context.startActivity(intent);
            }
        });
        tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Are you sure you want to delete the expense?";
                AlertDialog.Builder builder = new AlertDialog.Builder(context); // Change "this" to `getActivity()` if you're using this on a fragment
                builder.setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                deleteExpense(user.getId());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        return convertView;

    }

    private void deleteExpense(String body){

        RetrofitAPI retrofitAPI = ApiClient.getApiClient();
        Dialog dialog = ProgressUtil.showProgress((Activity) context);
        Call<ResponseModal> call = retrofitAPI.deleteExpense(body);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                dialog.dismiss();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    if(responseFromAPI.getStatus() == 1){
                        Intent intent1=new Intent(context, ExpenseActivity.class);

                        context.startActivity(intent1);
                        ((Activity)context).finish();

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