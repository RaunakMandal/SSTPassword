package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class NewPassWordActivity extends AppCompatActivity {

    private EditText password, confirm;
    private String pass1, pass2;
    private Button submit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_word);

        sharedPreferences = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        boolean first = sharedPreferences.getBoolean(getString(R.string.is_first), true);

        if (first) {
            password = findViewById(R.id.pass1_et);
            confirm = findViewById(R.id.pass2_et);
            submit = findViewById(R.id.save_pass);

            submit.setOnClickListener(v -> {
                pass1 = password.getText().toString();
                pass2 = confirm.getText().toString();

                if (!pass1.equals(pass2)) {
                    Toast.makeText(NewPassWordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.app_pass), pass1);
                    editor.putBoolean(getString(R.string.is_first), false);
                    editor.apply();
                    startActivity(new Intent(NewPassWordActivity.this, MainActivity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(NewPassWordActivity.this, CheckPasswordActivity.class));
            finish();
        }
    }
}