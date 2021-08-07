package com.iamriju2000.sstpassword.viewmodel.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.viewmodel.repository.PersonalRepository;

import java.util.List;

public class PersonalViewModel extends AndroidViewModel {
    private final PersonalRepository repository;
    private final LiveData<List<Personal>> allPersonal;

    public PersonalViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonalRepository(application);
        allPersonal = repository.getAllPersonal();
    }

    public LiveData<List<Personal>> getAllPersonal() {
        return allPersonal;
    }
}
