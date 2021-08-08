package com.iamriju2000.sstpassword.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.iamriju2000.sstpassword.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView website = findViewById(R.id.website);
        website.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://srijontours.com")));
        });
    }
}