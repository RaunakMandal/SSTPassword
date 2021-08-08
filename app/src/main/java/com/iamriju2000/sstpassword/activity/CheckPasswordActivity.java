package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class CheckPasswordActivity extends AppCompatActivity {

    private EditText password;
    private Button unlock;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_password);

        password = findViewById(R.id.pass_check);
        unlock = findViewById(R.id.save_pass);

        sharedPreferences = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        unlock.setOnClickListener(v -> {
            if (password.getText().toString().equals(sharedPreferences.getString(getString(R.string.app_pass), ""))) {
                startActivity(new Intent(CheckPasswordActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(CheckPasswordActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}