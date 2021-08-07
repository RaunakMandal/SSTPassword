package com.iamriju2000.sstpassword.database.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iamriju2000.sstpassword.data.Finance;
import com.iamriju2000.sstpassword.database.dao.FinanceDao;
import com.iamriju2000.sstpassword.database.dao.PersonalDao;

@Database(entities = {Finance.class}, version = 1)
public abstract class FinanceDB extends RoomDatabase {
    private static FinanceDB instance;

    public abstract FinanceDao financeDao();

    public static synchronized FinanceDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FinanceDB.class, "finance")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
