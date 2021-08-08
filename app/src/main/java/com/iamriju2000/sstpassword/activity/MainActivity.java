package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout all = findViewById(R.id.personal_btn);
        RelativeLayout finance = findViewById(R.id.finance_btn);
        all.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PersonalList.class);
            startActivity(i);
        });

        finance.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, FinanceList.class);
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about_menu) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (item.getItemId() == R.id.lock_menu) {
            startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}