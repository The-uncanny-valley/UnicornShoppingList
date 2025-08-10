package com.example.unicornshoppinglist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private final int id;
    private final String text;
    private final String color;

    public Note(int id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    @Ignore
    public Note(String text, String color) {
        this(0, text, color);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}