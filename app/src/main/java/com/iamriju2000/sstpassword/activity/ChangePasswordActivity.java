package com.iamriju2000.sstpassword.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText password, confirm;
    private String pass1, pass2;
    private Button submit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreferences = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        password = findViewById(R.id.pass1_ch);
        confirm = findViewById(R.id.pass2_ch);
        submit = findViewById(R.id.save_pass_ch);

        submit.setOnClickListener(v -> {
            pass1 = password.getText().toString();
            pass2 = confirm.getText().toString();

            if (!pass1.equals(pass2) || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.app_pass), pass1);
                editor.apply();
                finish();
            }
        });
    }
}