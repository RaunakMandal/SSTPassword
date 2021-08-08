package com.iamriju2000.sstpassword.viewmodel.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.database.dao.FinanceDao;
import com.iamriju2000.sstpassword.database.db.FinanceDB;

import java.util.List;

public class FinanceRepository {
    private FinanceDao dao;
    private LiveData<List<Finance>> financeList;
    private FinanceDB database;

    public FinanceRepository(Application application) {
        database = FinanceDB.getInstance(application);
        dao = database.financeDao();
        financeList = dao.getAllFinance();
    }

    public LiveData<List<Finance>> getAllFinance() {
        return financeList;
    }
    public void insertOne(Finance finance) {
        new FinanceRepository.InsertOneAsyncTask(dao).execute(finance);
    }
    public void insertAll(List<Finance> finances) { new FinanceRepository.InsertAllAsyncTask(dao).execute(finances); }
    public void deleteOne(String id) { new FinanceRepository.DeleteOneAsyncTask(dao).execute(id); }
    public void updateOne(Finance finance) {
        new FinanceRepository.UpdateOneAsyncTask(dao).execute(finance);
    }

    private static class InsertOneAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao dao;
        private InsertOneAsyncTask(FinanceDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Finance... finances) {
            dao.insertOne(finances[0]);
            return null;
        }
    }

    private static class InsertAllAsyncTask extends AsyncTask<List<Finance>, Void, Void> {
        private FinanceDao dao;
        private InsertAllAsyncTask(FinanceDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<Finance>... lists) {
            dao.insertAll(lists[0]);
            return null;
        }
    }
    private static class DeleteOneAsyncTask extends AsyncTask<String, Void, Void> {
        private FinanceDao dao;
        private DeleteOneAsyncTask(FinanceDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(String... ids) {
            dao.deleteOne(ids[0]);
            return null;
        }
    }
    private static class UpdateOneAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao dao;
        private UpdateOneAsyncTask(FinanceDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Finance... finances) {
            dao.updateOne(finances[0]);
            return null;
        }
    }
}
