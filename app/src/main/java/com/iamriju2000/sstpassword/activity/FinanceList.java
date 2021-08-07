package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iamriju2000.sstpassword.R;
import com.iamriju2000.sstpassword.adapter.FinanceAdapter;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;
import com.iamriju2000.sstpassword.data.Finance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceList extends AppCompatActivity {
    private FinanceAdapter financeAdapter;
    private RelativeLayout listRelative, emptyView;
    private ListView listView;
    private FloatingActionButton fab;
    private Button retryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_list);
        listRelative = (RelativeLayout) findViewById(R.id.list_relative);
        emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        listView = (ListView) findViewById(R.id.list);
        fab = findViewById(R.id.addnew);

        listRelative.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinanceList.this, AddFinance.class);
                startActivity(i);
            }
        });
    }

//    private void fetchData() {
//        listRelative.setVisibility(View.GONE);
//        emptyView.setVisibility(View.GONE);
//        Call<ArrayList<Finance>> call = ApiClient.fetchData().getFinance(Constants.API_KEY, Constants.API_KEY);
//        call.enqueue(new Callback<ArrayList<Finance>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Finance>> call, Response<ArrayList<Finance>> response) {
//                loading.setVisibility(View.GONE);
//                listRelative.setVisibility(View.VISIBLE);
//                financeAdapter = new FinanceAdapter(getApplicationContext(), response.body());
//                listView.setAdapter(financeAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Finance>> call, Throwable t) {
//                loading.setVisibility(View.GONE);
//                listRelative.setVisibility(View.GONE);
//                emptyView.setVisibility(View.VISIBLE);
//                retryBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loading.setVisibility(View.VISIBLE);
//                        listRelative.setVisibility(View.GONE);
//                        emptyView.setVisibility(View.GONE);
//                        fetchData();
//                    }
//                });
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                financeAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}