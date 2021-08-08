package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iamriju2000.sstpassword.R;
import com.iamriju2000.sstpassword.adapter.FinanceAdapter;
import com.iamriju2000.sstpassword.adapter.PersonalAdapter;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;
import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.viewmodel.repository.FinanceRepository;
import com.iamriju2000.sstpassword.viewmodel.viewmodels.FinanceViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceList extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private FinanceAdapter financeAdapter;
    private RelativeLayout emptyView;
    private ListView listView;
    private FloatingActionButton fab;

    private FinanceViewModel financeViewModel;
    private FinanceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_list);
        emptyView = findViewById(R.id.empty_view);
        listView = findViewById(R.id.list);
        fab = findViewById(R.id.addnew);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        repository = new FinanceRepository(getApplication());
        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);
        financeViewModel.getAllFinance().observe(this, new Observer<List<Finance>>() {
            @Override
            public void onChanged(List<Finance> financeList) {
                Log.d("TAGTAG", "Changed: " + financeList.size());

                if (financeList.size() <= 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    financeAdapter = new FinanceAdapter(getApplicationContext(), (ArrayList<Finance>) financeList, getApplication());
                    listView.setAdapter(financeAdapter);
                }
            }
        });
        fab.setOnClickListener(v -> {
            startActivity(new Intent(FinanceList.this, AddFinance.class));
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }

    private void fetchData() {
        Call<List<Finance>> call = ApiClient.fetchData().getFinance(Constants.API_KEY, Constants.API_KEY);
        call.enqueue(new Callback<List<Finance>>() {
            @Override
            public void onResponse(Call<List<Finance>> call, Response<List<Finance>> response) {
                if (response.isSuccessful()) {
                    Log.d("TAGTAG", " " + response.body().size());
                    repository.insertAll(response.body());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Finance>> call, Throwable t) {
                Toast.makeText(FinanceList.this, "Failed to fetch data. Retry!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            fetchData();
        }
        return super.onOptionsItemSelected(item);
    }
}