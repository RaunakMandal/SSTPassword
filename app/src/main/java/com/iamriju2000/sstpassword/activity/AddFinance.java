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
import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.viewmodel.repository.FinanceRepository;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFinance extends AppCompatActivity {
    private EditText name, acname, user, pass, web, acno, branch, ifsc, micr, profilepass, txnpass;
    private Button insert;
    private ProgressBar pb;
    private FinanceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_pass);

        repository = new FinanceRepository(getApplication());

        name = findViewById(R.id.name_bank);
        acname = findViewById(R.id.accname_bank);
        user = findViewById(R.id.user_bank);
        pass = findViewById(R.id.pass_bank);
        web = findViewById(R.id.website_bank);
        acno = findViewById(R.id.acno_bank);
        branch = findViewById(R.id.branch_bank);
        ifsc = findViewById(R.id.ifsc_bank);
        micr = findViewById(R.id.micr_bank);
        profilepass = findViewById(R.id.ppass_bank);
        txnpass = findViewById(R.id.txnpass_bank);

        pb = findViewById(R.id.progress_bank);
        pb.setVisibility(View.GONE);

        insert = findViewById(R.id.submit_btn_bank);
        insert.setOnClickListener(v -> {
            if (checkFields(name) && checkFields(user) && checkFields(pass) && checkFields(acname) && checkFields(acno)) {
                if (!checkFields(web)) {
                    web.setText("https://" + name.getText().toString().toLowerCase() + ".com");
                }
                if (!checkFields(branch)) {
                    branch.setText("Katabagan");
                }
                if (!checkFields(ifsc)) {
                    ifsc.setText("SBIN0007147");
                }
                if (!checkFields(micr)) {
                    micr.setText(R.string.not_available);
                }
                if (!checkFields(profilepass)) {
                    profilepass.setText(R.string.not_available);
                }
                if (!checkFields(txnpass)) {
                    txnpass.setText(R.string.not_available);
                }
                insertdata();
            } else
                Toast.makeText(AddFinance.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
        });
    }

    private void insertdata() {
        insert.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        Map<String, String> mp = new HashMap<>();
        mp.put("bankname", (name.getText().toString()));
        mp.put("accname", (acname.getText().toString()));
        mp.put("user", (user.getText().toString()));
        mp.put("pass", (pass.getText().toString()));
        mp.put("acno", (acno.getText().toString()));
        mp.put("branch", (branch.getText().toString()));
        mp.put("ifsc", (ifsc.getText().toString()));
        mp.put("micr", (micr.getText().toString()));
        mp.put("profpass", (profilepass.getText().toString()));
        mp.put("txnpass", (txnpass.getText().toString()));
        mp.put("web", (web.getText().toString()));

        Call<Finance> call = ApiClient.fetchData().addFinance(Constants.API_KEY, Constants.API_KEY, mp);
        call.enqueue(new Callback<Finance>() {
            @Override
            public void onResponse(Call<Finance> call, Response<Finance> response) {
                if (response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    repository.insertOne(response.body());
                    finish();
                } else {
                    Toast.makeText(AddFinance.this, R.string.failed_add, Toast.LENGTH_SHORT).show();
                    insert.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Finance> call, Throwable t) {
                Toast.makeText(AddFinance.this, R.string.failed_add, Toast.LENGTH_SHORT).show();
                insert.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
            }
        });
    }

    private boolean checkFields(EditText s) {
        return !(s.getText().toString().isEmpty() && TextUtils.isEmpty(s.getText().toString()));
    }
}