package com.iamriju2000.sstpassword.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class MainActivity extends AppCompatActivity {
    public static ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout all = (RelativeLayout) findViewById(R.id.personal_btn);
        RelativeLayout finance = (RelativeLayout) findViewById(R.id.finance_btn);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PersonalList.class);
                startActivity(i);
            }
        });

        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FinanceList.class);
                startActivity(i);
            }
        });
        if (!isConnect()) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("No Internet").setMessage("Please Enable WiFi/Mobile Data and Restart the App.");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "Please try again later...", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    }

    private boolean isConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}