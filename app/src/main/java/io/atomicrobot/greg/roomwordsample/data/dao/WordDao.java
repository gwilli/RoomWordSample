package io.atomicrobot.greg.roomwordsample.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.atomicrobot.greg.roomwordsample.data.entity.Word;

@Dao
public interface WordDao {

    @Insert
    void insertWord(Word word);

    @Query("delete from word_table")
    void deleteAll();

    @Query("select * from word_table order by word asc")
    LiveData<List<Word>> getAllWords();
}
