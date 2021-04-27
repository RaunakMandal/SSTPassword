package com.iamriju2000.sstpassword.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.iamriju2000.sstpassword.R;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPersonal extends AppCompatActivity {
    private EditText name, user, pass, web;
    private Button addbtn;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pass);

        name = findViewById(R.id.name_per);
        user = findViewById(R.id.user_per);
        web = findViewById(R.id.website_per);
        pass = findViewById(R.id.pass_per);

        addbtn = findViewById(R.id.submit_btn_per);

        pb = (ProgressBar) findViewById(R.id.progress_per);
        pb.setVisibility(View.GONE);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(name) && checkFields(user) && checkFields(pass)) {
                    if (!checkFields(web)) {
                        web.setText("https://"+name.getText().toString().toLowerCase()+".com");
                    }
                    insertdata();
                }
                else
                    Toast.makeText(AddPersonal.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertdata() {
        addbtn.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        Map<String, String> mp = new HashMap<>();
        mp.put("name", Constants.encrypt(name.getText().toString()));
        mp.put("user", Constants.encrypt(user.getText().toString()));
        mp.put("pass", Constants.encrypt(pass.getText().toString()));
        mp.put("web", Constants.encrypt(web.getText().toString()));


        Call<String> addNewPersonal = ApiClient.fetchData().addNewData("personal", Constants.API_KEY, mp);
        addNewPersonal.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AddPersonal.this, response.body(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddPersonal.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                addbtn.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
            }
        });
    }

    private boolean checkFields(EditText s) {
        return !(s.getText().toString().isEmpty() && TextUtils.isEmpty(s.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}