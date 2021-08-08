package com.iamriju2000.sstpassword.viewmodel.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.viewmodel.repository.FinanceRepository;

import java.util.List;

public class FinanceViewModel extends AndroidViewModel {
    private final FinanceRepository repository;
    private final LiveData<List<Finance>> allFinance;

    public FinanceViewModel(@NonNull Application application) {
        super(application);
        repository = new FinanceRepository(application);
        allFinance = repository.getAllFinance();
    }

    public LiveData<List<Finance>> getAllFinance() {
        return allFinance;
    }
}
