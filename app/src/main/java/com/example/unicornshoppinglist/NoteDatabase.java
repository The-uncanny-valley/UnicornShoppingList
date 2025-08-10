package com.example.unicornshoppinglist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance = null;
    private static final String DB_NAME = "notes.db";

    // Migration from version 1 to 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Add 'checked' column as INTEGER (0 = false, 1 = true), default false (0)
            database.execSQL("ALTER TABLE Note ADD COLUMN checked INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static NoteDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    NoteDatabase.class,
                    DB_NAME
            ).addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();
}
