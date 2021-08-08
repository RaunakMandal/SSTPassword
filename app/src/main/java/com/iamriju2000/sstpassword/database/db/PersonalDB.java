package com.iamriju2000.sstpassword.database.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.iamriju2000.sstpassword.data.Personal;
import com.iamriju2000.sstpassword.database.dao.PersonalDao;

@Database(entities = {Personal.class}, version = 1, exportSchema = false)
public abstract class PersonalDB extends RoomDatabase {
    public abstract PersonalDao personalDao();

    private static volatile PersonalDB instance;

    public static synchronized PersonalDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PersonalDB.class, "personal")
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
            new PopulateAsyncTask(instance);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersonalDao dao;
        public PopulateAsyncTask(PersonalDB db) {
            this.dao = db.personalDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
