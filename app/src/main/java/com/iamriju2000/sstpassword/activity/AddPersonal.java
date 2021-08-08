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
import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.viewmodel.repository.PersonalRepository;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPersonal extends AppCompatActivity {
    private EditText name, user, pass, web;
    private Button addbtn;
    private ProgressBar pb;
    private PersonalRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pass);

        repository = new PersonalRepository(getApplication());

        name = findViewById(R.id.name_per);
        user = findViewById(R.id.user_per);
        web = findViewById(R.id.website_per);
        pass = findViewById(R.id.pass_per);

        addbtn = findViewById(R.id.submit_btn_per);

        pb = findViewById(R.id.progress_per);
        pb.setVisibility(View.GONE);

        addbtn.setOnClickListener(v ->  {
                if (checkFields(name) && checkFields(user) && checkFields(pass)) {
                    if (!checkFields(web)) {
                        web.setText("https://"+name.getText().toString().toLowerCase()+".com");
                    }
                    insertdata();
                }
                else
                    Toast.makeText(AddPersonal.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
        });
    }

    private void insertdata() {
        addbtn.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        Map<String, String> mp = new HashMap<>();
        mp.put("name", name.getText().toString());
        mp.put("user", user.getText().toString());
        mp.put("pass", pass.getText().toString());
        mp.put("web", web.getText().toString());

        Call<Personal> call = ApiClient.fetchData().addPersonal(Constants.API_KEY, Constants.API_KEY, mp);
        call.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                if(response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    repository.insertOne(response.body());
                    finish();
                } else {
                    Toast.makeText(AddPersonal.this, R.string.failed_add, Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    addbtn.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                Toast.makeText(AddPersonal.this, R.string.failed_add, Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                addbtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean checkFields(EditText s) {
        return !(s.getText().toString().isEmpty() && TextUtils.isEmpty(s.getText().toString()));
    }
}