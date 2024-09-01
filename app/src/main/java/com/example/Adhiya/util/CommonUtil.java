package com.example.Adhiya.util;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.Adhiya.MainActivity;
import com.example.Adhiya.network.ApiClient;
import com.example.splash.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
//    private static Context context;
//
//
//    public CommonUtil(Context context) {
//        this.context = context;
//    }

    public static void getTitleBar(Activity view,String title){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.inflateMenu(R.menu.borrower_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view); // Change "this" to `getActivity()` if you're using this on a fragment
                builder.setMessage(Datautil.LOGOUT_MSG)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                new ApiClient(view).SetString("");
                                Intent intent = new Intent(view, MainActivity.class);
                                view.startActivity(intent);
                                view.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }

    public static String addDisplayDay(String oldDate, int numberOfDays) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_YEAR,numberOfDays);
        dateFormat=new SimpleDateFormat("dd-MM-YYYY");
        Date newDate=new Date(c.getTimeInMillis());
        String resultDate=dateFormat.format(newDate);
        return resultDate;
    }
    public static String addDay(String oldDate, int numberOfDays) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_YEAR,numberOfDays);
        dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Date newDate=new Date(c.getTimeInMillis());
        String resultDate=dateFormat.format(newDate);
        return resultDate;
    }


//
//
//    public static String getString(String key) {
//        SharedPreferences prefs = context.getSharedPreferences(Datautil.PREFS_NAME, MODE_PRIVATE);
//        return prefs.getString(key, "");
//    }
//
//    public static void SetString(String token) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(Datautil.PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putString("token", token);
//        myEdit.apply();
//    }
//
//    public static String getToken() {
//        return getString("token");
//    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

//    public static void getResponse(Call call, ActionUtil actionUtil) {
//
//        call.enqueue(new Callback<ResponseModal>() {
//            @Override
//            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
//                if (response.code() == 200) {                // this method is called when we get response from our api.
//                    ResponseModal responseFromAPI = response.body();
//                    if (responseFromAPI.getStatus() == 1) {
//                        actionUtil.successAction(responseFromAPI.getObject());
//                    }
//                } else {
//                    Toast.makeText(context, "API Response code " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModal> call, Throwable t) {
//                Log.v("Siva ", t.getLocalizedMessage());
//                Toast.makeText(context, "API Faiure due to " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
