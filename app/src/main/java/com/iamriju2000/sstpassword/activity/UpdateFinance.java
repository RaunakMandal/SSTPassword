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

public class UpdateFinance extends AppCompatActivity {
    private EditText name, acname, user, pass, web, acno, branch, ifsc, micr, profilepass, txnpass;
    private String id, name_get, accname_get, user_get, web_get, pass_get, acno_get, branch_get, ifsc_get, micr_get, ppas_get, txnpass_get;
    private Button updatebtn;
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

        Bundle data = getIntent().getExtras();
        if (data != null) {
            id = data.getString("id");
            name_get = data.getString("name");
            user_get = data.getString("userid");
            pass_get = data.getString("loginpass");
            web_get = data.getString("web");
            acno_get = data.getString("acno");
            ppas_get = data.getString("profpass");
            txnpass_get = data.getString("txnpass");
            accname_get = data.getString("accname");
            branch_get = data.getString("branch");
            ifsc_get = data.getString("ifsc");
            micr_get = data.getString("micr");
        }

        name.setText(name_get);
        user.setText(user_get);
        pass.setText(pass_get);
        web.setText(web_get);
        acno.setText(acno_get);
        profilepass.setText(ppas_get);
        txnpass.setText(txnpass_get);
        acname.setText(accname_get);
        branch.setText(branch_get);
        ifsc.setText(ifsc_get);
        micr.setText(micr_get);

        updatebtn = (Button) findViewById(R.id.submit_btn_bank);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(name) && checkFields(user) && checkFields(pass) && checkFields(acname) && checkFields(acno)) {
                    if (!checkFields(web)) {
                        web.setText("https://srijontours.com");
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
                    updateData();
                } else
                    Toast.makeText(UpdateFinance.this, getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        updatebtn.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        Map<String, String> mp = new HashMap<>();
        mp.put("bankname", name.getText().toString());
        mp.put("accname", acname.getText().toString());
        mp.put("user", user.getText().toString());
        mp.put("pass", pass.getText().toString());
        mp.put("acno", acno.getText().toString());
        mp.put("branch", branch.getText().toString());
        mp.put("ifsc", ifsc.getText().toString());
        mp.put("micr", micr.getText().toString());
        mp.put("profpass", profilepass.getText().toString());
        mp.put("txnpass", txnpass.getText().toString());
        mp.put("web", web.getText().toString());


        Call<String> addNewPersonal = ApiClient.fetchData().editById("finance", id, Constants.API_KEY, mp);
        addNewPersonal.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(UpdateFinance.this, response.body(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateFinance.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                updatebtn.setVisibility(View.VISIBLE);
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