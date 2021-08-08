package com.iamriju2000.sstpassword.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.iamriju2000.sstpassword.activity.FinanceList;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;
import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.activity.BankDetails;
import com.iamriju2000.sstpassword.R;
import com.iamriju2000.sstpassword.activity.UpdateFinance;
import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.viewmodel.repository.FinanceRepository;
import com.iamriju2000.sstpassword.viewmodel.repository.PersonalRepository;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceAdapter extends ArrayAdapter<Finance> implements Filterable {

    private static FinanceRepository repository;
    private Application application;

    private ArrayList<Finance> nonfiltered;
    private ArrayList<Finance> filtered;
    private static Context context;
    private AlertDialog.Builder alertdialog;

    public FinanceAdapter(Context context, ArrayList<Finance> objects, Application application) {
        super(context, 0, objects);
        this.context = context;
        this.filtered = objects;
        this.nonfiltered = new ArrayList<>(objects);
        this.application = application;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Finance pwd = getItem(position);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        repository = new FinanceRepository(application);

        Context context = view.getContext();
        String id = pwd.get_id();
        String name = pwd.getBankname();
        String userid = pwd.getUser();
        String loginpass = pwd.getPass();
        String web = pwd.getWeb();
        String acc = pwd.getAcno();
        String profpass = pwd.getProfpass();
        String txnpass = pwd.getTxnpass();
        String branch = pwd.getBranch();
        String ifsc = pwd.getIfsc();
        String micr = pwd.getMicr();
        String accname = pwd.getAccname();

        TextView nameM = (TextView) view.findViewById(R.id.name);
        nameM.setText(name);
        TextView userM = (TextView) view.findViewById(R.id.username);
        userM.setText(userid);
        TextView passM = (TextView) view.findViewById(R.id.password);
        passM.setText(loginpass);

        nameM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("id", id);
                data.putString("name", name);
                data.putString("acno", acc);
                data.putString("ifsc", ifsc);
                data.putString("branch", branch);
                data.putString("userid", userid);
                data.putString("loginpass", loginpass);
                data.putString("profpass", profpass);
                data.putString("txnpass", txnpass);
                data.putString("micr", micr);
                data.putString("web", web);
                data.putString("accname", accname);

                Intent intent = new Intent(context, BankDetails.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(data);
                context.startActivity(intent);
            }
        });

        userM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Username", userid);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Username Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        passM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Password", loginpass);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Password Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton more = (ImageButton) view.findViewById(R.id.show_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.actions_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.update:

                                Bundle data = new Bundle();
                                data.putString("id", id);
                                data.putString("name", name);
                                data.putString("acno", acc);
                                data.putString("ifsc", ifsc);
                                data.putString("branch", branch);
                                data.putString("userid", userid);
                                data.putString("loginpass", loginpass);
                                data.putString("profpass", profpass);
                                data.putString("txnpass", txnpass);
                                data.putString("micr", micr);
                                data.putString("web", web);
                                data.putString("accname", accname);

                                Intent intent = new Intent(context, UpdateFinance.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtras(data);
                                context.startActivity(intent);
                                return true;

                            case R.id.delete:
                                alertdialog = new AlertDialog.Builder(view.getRootView().getContext());
                                alertdialog.setMessage(view.getContext().getString(R.string.delete_conf))
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                delete(view, id);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).show();
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Finance> resultData = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                resultData.addAll(nonfiltered);
            } else {
                for (Finance data : nonfiltered) {
                    String detected = charSequence.toString().toLowerCase();
                    if (data.getBankname().toLowerCase().contains(detected) || data.getUser().toLowerCase().contains(detected)) {
                        resultData.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = resultData;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filtered.clear();
            filtered.addAll((Collection<? extends Finance>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static void delete(View view, String id) {
        ProgressDialog dialog = new ProgressDialog(view.getRootView().getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<String> call = ApiClient.fetchData().deleteFinance(id, Constants.API_KEY, Constants.API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(view.getContext(), response.body(), Toast.LENGTH_SHORT).show();
                repository.deleteOne(id);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.failed_delete), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
