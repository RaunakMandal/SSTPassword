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

public class UpdatePersonal extends AppCompatActivity {
    private EditText name, user, web, pass;
    private String id, namestr, userstr, webstr, passstr;
    private Button updatebtn;
    private ProgressBar pb;

    private PersonalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pass);
        name = findViewById(R.id.name_per);
        user = findViewById(R.id.user_per);
        web = findViewById(R.id.website_per);
        pass = findViewById(R.id.pass_per);

        updatebtn = findViewById(R.id.submit_btn_per);
        pb = findViewById(R.id.progress_per);

        pb.setVisibility(View.GONE);

        repository = new PersonalRepository(getApplication());

        Bundle data = getIntent().getExtras();
        if (data != null) {
            id = data.getString("id");
            namestr = data.getString("name");
            userstr = data.getString("userid");
            passstr = data.getString("loginpass");
            webstr = data.getString("web");
        }
        name.setText(namestr);
        user.setText(userstr);
        pass.setText(passstr);
        web.setText(webstr);

        updatebtn.setOnClickListener(v -> {
            if (checkFields(name) && checkFields(user) && checkFields(pass)) {
                if (!checkFields(web)) {
                    web.setText("https://" + name.getText().toString().toLowerCase() + ".com");
                }
                updateData();
            } else
                Toast.makeText(UpdatePersonal.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
        });
    }

    public void updateData() {
        updatebtn.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        Map<String, String> mp = new HashMap<>();
        mp.put("name", name.getText().toString());
        mp.put("user", user.getText().toString());
        mp.put("pass", pass.getText().toString());
        mp.put("web", web.getText().toString());

        Call<Personal> call = ApiClient.fetchData().editPersonal(id, Constants.API_KEY, Constants.API_KEY, mp);
        call.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                if (response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    updatebtn.setVisibility(View.VISIBLE);
                    repository.updateOne(response.body());
                    finish();
                } else {
                    Toast.makeText(UpdatePersonal.this, R.string.failed_edit, Toast.LENGTH_SHORT).show();
                    updatebtn.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                Toast.makeText(UpdatePersonal.this, R.string.failed_edit, Toast.LENGTH_SHORT).show();
                updatebtn.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
            }
        });
    }

    private boolean checkFields(EditText s) {
        return !(s.getText().toString().isEmpty() && TextUtils.isEmpty(s.getText().toString()));
    }
}