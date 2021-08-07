package com.iamriju2000.sstpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.iamriju2000.sstpassword.adapter.PersonalAdapter;
import com.iamriju2000.sstpassword.api.ApiClient;
import com.iamriju2000.sstpassword.constants.Constants;
import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.viewmodel.repository.PersonalRepository;
import com.iamriju2000.sstpassword.viewmodel.viewmodels.PersonalViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalList extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private PersonalAdapter personalAdapter;
    private RelativeLayout listRelative, emptyView;
    private ListView listView;
    private FloatingActionButton fab;

    private PersonalViewModel personalViewModel;
    private PersonalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_list);
        listRelative = findViewById(R.id.list_relative);
        emptyView = findViewById(R.id.empty_view);
        listView = findViewById(R.id.list);
        fab = findViewById(R.id.addnew);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        repository = new PersonalRepository(getApplication());
        personalViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);
        personalViewModel.getAllPersonal().observe(this, new Observer<List<Personal>>() {
            @Override
            public void onChanged(List<Personal> personalList) {
                Log.v("TAGTAG", "Changed: " + personalList.size());

                if (personalList.size() <= 0) {
                    listRelative.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    listRelative.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    personalAdapter = new PersonalAdapter(getApplicationContext(), (ArrayList<Personal>) personalList, getApplication());
                    listView.setAdapter(personalAdapter);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalList.this, AddPersonal.class));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }

    private void fetchData() {
        Call<List<Personal>> call = ApiClient.fetchData().getPersonal(Constants.API_KEY, Constants.API_KEY);
        call.enqueue(new Callback<List<Personal>>() {
            @Override
            public void onResponse(Call<List<Personal>> call, Response<List<Personal>> response) {
                if (response.isSuccessful()) {
                    Log.v("TAGTAG", " " + response.body().size());
                    repository.insertAll(response.body());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Personal>> call, Throwable t) {
                Toast.makeText(PersonalList.this, "Failed to fetch data. Retry!", Toast.LENGTH_SHORT).show();
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
                personalAdapter.getFilter().filter(s);
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