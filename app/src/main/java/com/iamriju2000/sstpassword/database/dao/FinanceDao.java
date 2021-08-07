package com.iamriju2000.sstpassword.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iamriju2000.sstpassword.data.Finance;

import java.util.List;

@Dao
public interface FinanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Finance> financeList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Finance finance);

    @Query("Select * from finance")
    LiveData<List<Finance>> getAllFinance();

    @Query("Select * from finance where _id = :id")
    Finance getFinanceById(String id);

    @Query("Delete from finance")
    void deleteAll();

    @Update
    void updateOne(Finance finance);

    @Delete
    void deleteOne(Finance finance);
}
