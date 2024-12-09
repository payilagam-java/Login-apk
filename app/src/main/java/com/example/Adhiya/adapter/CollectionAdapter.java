package com.example.Adhiya.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adhiya.CollectionListActivity;
import com.example.Adhiya.ExpenseAddActivity;
import com.example.Adhiya.LoanAddActivity;
import com.example.Adhiya.LoanHistory;
import com.example.Adhiya.PaymentActivity;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SendCollection;
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

public class CollectionAdapter extends ArrayAdapter<CollectionModal> {
    Context context;
    SendCollection date;
    public List<CollectionModal> orig;
    List<CollectionModal> users;
    public CollectionAdapter(Context context, List<CollectionModal> users, SendCollection date) {
        super(context, 0, users);
        this.context = context;
        this.date = date;
        this.users = users;
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<CollectionModal> results = new ArrayList<CollectionModal>();
                if (orig == null)
                    orig = users;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final CollectionModal g : orig) {
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
                users = (ArrayList<CollectionModal>) results.values;
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
    public CollectionModal getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectionModal user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.collection_item_list, parent, false);
        }

        TextView field1 = (TextView) convertView.findViewById(R.id.field1);
        TextView field2 = (TextView) convertView.findViewById(R.id.field2);
        TextView field3 = (TextView) convertView.findViewById(R.id.field3);
        TextView field4 = (TextView) convertView.findViewById(R.id.field4);
        TextView field5 = (TextView) convertView.findViewById(R.id.field5);

        field1.setText(user.getBorrowerName());
        field2.setText("Loan ID: "+String.valueOf(user.getBorrowerLoanId()));
        field3.setText("Loan Amount: "+String.valueOf(user.getPayableAmount()));
        Float bal = Float.parseFloat(user.getPayableAmount()) - Float.parseFloat(user.getBalance());
        field4.setText("Balance: "+String.valueOf(bal));

        field5.setText("Amount Paid: "+String.valueOf(user.getAmountofPaid()));

        ImageButton pay= (ImageButton) convertView.findViewById(R.id.pay);
        try {
            if (Float.parseFloat(user.getAmountofPaid()) == 0) {
                pay.setImageResource(R.drawable.baseline_currency_rupee_24);//
            }else{
                pay.setImageResource(R.drawable.baseline_edit_24);
            }
        }catch (NumberFormatException e){

        }
        ImageButton history = (ImageButton) convertView.findViewById(R.id.history);
        ImageButton call = (ImageButton) convertView.findViewById(R.id.call);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("payloan",(Serializable)user);
                args.putSerializable("date",(Serializable) date);
                intent.putExtra("BUNDLE",args);
                //intent.putExtra("date",date);
                context.startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanHistory.class);
                context.startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBorrowerList(user.getBorrowerId());

            }
        });



        return convertView;

    }

    private void getBorrowerList(String body){
        RetrofitAPI retrofitAPI  = new ApiClient(context).getApiClient();
        Call call = retrofitAPI.getBorrower(body);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    List<BorrowerModal> b = responseFromAPI.getObject();
                    BorrowerModal bm = b.get(0);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" +bm.getMobileNubmer()));

                    context.startActivity(callIntent);
                }
            }
            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(context, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}