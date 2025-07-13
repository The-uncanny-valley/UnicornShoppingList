package com.example.unicornshoppinglist;

import java.util.ArrayList;

public class Database { // singleton
    private ArrayList<Note> notes = new ArrayList<>();

    private static Database uniqueInstance;

    public static Database getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Database();
        }
        return uniqueInstance;
    }

    private Database() {
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note " + i, "blue");
            notes.add(note);
        }
    }

    public void add(Note note) {
        notes.add(note);
    }

    public void remove(int id) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id) {
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }
}