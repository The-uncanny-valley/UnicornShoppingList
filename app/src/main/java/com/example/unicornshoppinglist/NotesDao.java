package com.example.unicornshoppinglist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Insert
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

    @Update
    void update(Note note);
}