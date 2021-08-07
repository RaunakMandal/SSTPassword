package com.iamriju2000.sstpassword.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.iamriju2000.sstpassword.activity.PersonalList;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;
import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.R;
import com.iamriju2000.sstpassword.activity.UpdatePersonal;
import com.iamriju2000.sstpassword.viewmodel.repository.PersonalRepository;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalAdapter extends ArrayAdapter<Personal> implements Filterable {

    private static PersonalRepository repository;
    private Application application;

    private ArrayList<Personal> nonfiltered;
    private ArrayList<Personal> filtered;
    private static Context context;

    private AlertDialog.Builder alertdialog;

    public PersonalAdapter(Context context, ArrayList<Personal> objects, Application application) {
        super(context, 0, objects);
        this.context = context;
        this.filtered = objects;
        this.nonfiltered = new ArrayList<>(objects);
        this.application = application;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Personal pwd = getItem(position);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        repository = new PersonalRepository(application);

        String id = pwd.get_id();
        String name = pwd.getName();
        String userid = pwd.getUser();
        String loginpass = pwd.getPass();
        String web = pwd.getWeb();

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
                                data.putString("userid", userid);
                                data.putString("loginpass", loginpass);
                                data.putString("web", web);

                                context.startActivity(new Intent(context, UpdatePersonal.class).putExtras(data).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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


        TextView namee = (TextView) view.findViewById(R.id.name);
        namee.setText(name);
        TextView userr = (TextView) view.findViewById(R.id.username);
        userr.setText(userid);
        TextView passs = (TextView) view.findViewById(R.id.password);
        passs.setText(loginpass);

        namee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(web);
                Intent i = new Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        userr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Username", userid);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Username Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        passs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Password", loginpass);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Password Copied to Clipboard", Toast.LENGTH_SHORT).show();
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
            ArrayList<Personal> resultData = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                resultData.addAll(nonfiltered);
            } else {
                for (Personal data : nonfiltered) {
                    String detected = charSequence.toString().toLowerCase();
                    if (data.getName().toLowerCase().contains(detected) || data.getUser().toLowerCase().contains(detected)) {
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
            filtered.addAll((Collection<? extends Personal>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static void delete(View view, String id) {
        ProgressDialog dialog = new ProgressDialog(view.getRootView().getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<String> call = ApiClient.fetchData().deletePersonal(id, Constants.API_KEY, Constants.API_KEY);
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

