package com.iamriju2000.sstpassword.viewmodel.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.database.dao.PersonalDao;
import com.iamriju2000.sstpassword.database.db.PersonalDB;

import java.util.List;

public class PersonalRepository {
    private PersonalDao dao;
    private LiveData<List<Personal>> personalList;
    private PersonalDB database;

    public PersonalRepository(Application application) {
        database = PersonalDB.getInstance(application);
        dao = database.personalDao();
        personalList = dao.getAllPersonal();
    }

    public LiveData<List<Personal>> getAllPersonal() {
        return personalList;
    }
    public void insertOne(Personal personal) {
        new InsertOneAsyncTask(dao).execute(personal);
    }
    public void insertAll(List<Personal> personalList) { new InsertAllAsyncTask(dao).execute(personalList); }
    public void deleteOne(String id) { new DeleteOneAsyncTask(dao).execute(id); }
    public void updateOne(Personal personal) {
        new UpdateOneAsyncTask(dao).execute(personal);
    }

    private static class InsertOneAsyncTask extends AsyncTask<Personal, Void, Void> {
        private PersonalDao dao;
        private InsertOneAsyncTask(PersonalDao personalDao) {
            this.dao = personalDao;
        }
        @Override
        protected Void doInBackground(Personal... personals) {
            dao.insertOne(personals[0]);
            return null;
        }
    }

    private static class InsertAllAsyncTask extends AsyncTask<List<Personal>, Void, Void> {
        private PersonalDao dao;
        private InsertAllAsyncTask(PersonalDao personalDao) {
            this.dao = personalDao;
        }

        @Override
        protected Void doInBackground(List<Personal>... lists) {
            dao.insertAll(lists[0]);
            return null;
        }
    }
    private static class DeleteOneAsyncTask extends AsyncTask<String, Void, Void> {
        private PersonalDao dao;
        private DeleteOneAsyncTask(PersonalDao personalDao) {
            this.dao = personalDao;
        }
        @Override
        protected Void doInBackground(String... ids) {
            dao.deleteOne(ids[0]);
            return null;
        }
    }
    private static class UpdateOneAsyncTask extends AsyncTask<Personal, Void, Void> {
        private PersonalDao dao;
        private UpdateOneAsyncTask(PersonalDao personalDao) {
            this.dao = personalDao;
        }
        @Override
        protected Void doInBackground(Personal... personals) {
            dao.updateOne(personals[0]);
            return null;
        }
    }

}
