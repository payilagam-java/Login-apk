package com.example.Adhiya.util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adhiya.BorrowerActivity;
import com.example.Adhiya.BorrowerAddActivity;
import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ParentModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.splash.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataProccessor {
    private static Context context;


    public DataProccessor(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "MySharedPref";
    public static String getString(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key,"");
    }
    public static void SetString(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", token);;
        myEdit.apply();
    }
    public static String getToken(){
        return getString("token");
    }
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
