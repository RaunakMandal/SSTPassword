package com.iamriju2000.sstpassword.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iamriju2000.sstpassword.data.Personal;

import java.util.List;

@Dao
public interface PersonalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Personal> personalList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Personal personal);

    @Query("Select * from personal")
    LiveData<List<Personal>> getAllPersonal();

    @Query("Delete from personal")
    void deleteAll();

    @Update
    void updateOne(Personal personal);

    @Query("Delete from personal where _id = :id")
    void deleteOne(String id);
}
