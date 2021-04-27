package com.iamriju2000.sstpassword.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iamriju2000.sstpassword.R;

public class BankDetails extends AppCompatActivity {
    String name, acc, ifsc, branch, userid, loginpass, profpass, txnpass, micr, url, accname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            name = data.getString("name");
            acc = data.getString("acc");
            ifsc = data.getString("ifsc");
            branch = data.getString("branch");
            userid = data.getString("userid");
            loginpass = data.getString("loginpass");
            profpass = data.getString("profpass");
            txnpass = data.getString("txnpass");
            micr = data.getString("micr");
            url = data.getString("uri");
            accname = data.getString("accname");

            TextView bname = findViewById(R.id.bankname);
            bname.setText(name);
            bname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(i);
                }
            });
            TextView acno = findViewById(R.id.acno);
            acno.setText(acc);
            acno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", acc);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Account Number Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView ifscno = findViewById(R.id.ifscno);
            ifscno.setText(ifsc);
            ifscno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", ifsc);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "IFSC Code Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView branchname = findViewById(R.id.branchname);
            branchname.setText(branch);
            branchname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", branch);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Branch Name Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView micrc = findViewById(R.id.micrno);
            micrc.setText(micr);
            micrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", micr);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "MICR Code Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView useri = findViewById(R.id.userid);
            useri.setText(userid);
            useri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", userid);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "User ID Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView pw = findViewById(R.id.pwd);
            pw.setText(loginpass);
            pw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", loginpass);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Login Password Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView prof = findViewById(R.id.profpass);
            prof.setText(profpass);
            prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", profpass);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Profile Password Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView txn = findViewById(R.id.txnpass);
            txn.setText(txnpass);
            txn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", txnpass);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Transaction Password Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            TextView acname = findViewById(R.id.accname_details);
            acname.setText(accname);
            acname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData = ClipData.newPlainText("", accname);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(BankDetails.this, "Account Name Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button send = (Button) findViewById(R.id.share);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Account Number: " + acc + "\nAccount Name: "+accname +"\nBank Name: " + name + "\nBranch Name: " + branch + "\nIFSC Code: " + ifsc + "\nMICR Code: " + micr);
                Intent chooser = Intent.createChooser(intent, "Share your Bank Details with: ");
                startActivity(chooser);
            }
        });

    }
}