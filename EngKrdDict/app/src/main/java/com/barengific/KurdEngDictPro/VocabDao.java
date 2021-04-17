package com.barengific.KurdEngDictPro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VocabDao {

    @Insert
    void insertAll(List<Vocab> vocabs);

    @Update
    void update(Vocab vocab);

    @Query("SELECT * FROM vocab_table WHERE history = 1 ORDER BY word COLLATE NOCASE ASC")
    List<Vocab> getAllVocabs();

    @Query("SELECT * FROM vocab_table WHERE language LIKE :language AND word LIKE :hledany ORDER BY word COLLATE NOCASE ASC")
    List<Vocab> getSearchedVocabs(String hledany, String language);

}