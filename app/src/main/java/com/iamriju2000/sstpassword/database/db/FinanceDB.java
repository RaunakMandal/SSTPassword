package com.iamriju2000.sstpassword.database.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.database.dao.FinanceDao;

@Database(entities = {Finance.class}, version = 1, exportSchema = false)
public abstract class FinanceDB extends RoomDatabase {
    public abstract FinanceDao financeDao();

    private static volatile FinanceDB instance;

    public static synchronized FinanceDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FinanceDB.class, "finance")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new FinanceDB.PopulateAsyncTask(instance);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private FinanceDao dao;
        public PopulateAsyncTask(FinanceDB db) {
            this.dao = db.financeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
