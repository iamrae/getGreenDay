package com.example.getgreenday.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrackDataDAO {

    // Favorite 테이블에 데이터 삽입/삭제/가져오기

    @Query("SELECT * FROM trackData")
    LiveData<List<TrackData>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackData trackData);

    @Delete
    void delete(TrackData trackData);

}
