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

public class AddFinance extends AppCompatActivity {
    private EditText name, acname, user, pass, web, acno, branch, ifsc, micr, profilepass, txnpass;
    private Button insert;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_pass);

        name = (EditText) findViewById(R.id.name_bank);
        acname = (EditText) findViewById(R.id.accname_bank);
        user = (EditText) findViewById(R.id.user_bank);
        pass = (EditText) findViewById(R.id.pass_bank);
        web = (EditText) findViewById(R.id.website_bank);
        acno = (EditText) findViewById(R.id.acno_bank);
        branch = (EditText) findViewById(R.id.branch_bank);
        ifsc = (EditText) findViewById(R.id.ifsc_bank);
        micr = (EditText) findViewById(R.id.micr_bank);
        profilepass = (EditText) findViewById(R.id.ppass_bank);
        txnpass = (EditText) findViewById(R.id.txnpass_bank);

        pb = (ProgressBar) findViewById(R.id.progress_bank);
        pb.setVisibility(View.GONE);

        insert = (Button) findViewById(R.id.submit_btn_bank);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(name) && checkFields(user) && checkFields(pass) && checkFields(acname) && checkFields(acno)) {
                    if (!checkFields(web)) {
                        web.setText("https://"+name.getText().toString().toLowerCase()+".com");
                    }
                    if (!checkFields(branch)) {
                        branch.setText("Katabagan");
                    }
                    if (!checkFields(ifsc)) {
                        ifsc.setText("SBIN0007147");
                    }
                    if (!checkFields(micr)) {
                        micr.setText("Not Available");
                    }
                    if (!checkFields(profilepass)) {
                        profilepass.setText("Not Available");
                    }
                    if (!checkFields(txnpass)) {
                        txnpass.setText("Not Available");
                    }
                    insertdata();
                } else
                    Toast.makeText(AddFinance.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertdata() {
        insert.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
//        Map<String, String> mp = new HashMap<>();
//        mp.put("bankname", Constants.encrypt(name.getText().toString()));
//        mp.put("accname", Constants.encrypt(acname.getText().toString()));
//        mp.put("user", Constants.encrypt(user.getText().toString()));
//        mp.put("pass", Constants.encrypt(pass.getText().toString()));
//        mp.put("acno", Constants.encrypt(acno.getText().toString()));
//        mp.put("branch", Constants.encrypt(branch.getText().toString()));
//        mp.put("ifsc", Constants.encrypt(ifsc.getText().toString()));
//        mp.put("micr", Constants.encrypt(micr.getText().toString()));
//        mp.put("profpass", Constants.encrypt(profilepass.getText().toString()));
//        mp.put("txnpass", Constants.encrypt(txnpass.getText().toString()));
//        mp.put("web", Constants.encrypt(web.getText().toString()));


//        Call<String> addNewPersonal = ApiClient.fetchData().addNewData("finance", Constants.API_KEY, mp);
//        addNewPersonal.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(AddFinance.this, response.body(), Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(AddFinance.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
//                insert.setVisibility(View.VISIBLE);
//                pb.setVisibility(View.GONE);
//            }
//        });
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