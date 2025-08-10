package com.example.unicornshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonPink;
    private RadioButton radioButtonPurple;
    private Button buttonSave;
    private RadioGroup radioGroupColors;

    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        initViews();

        noteDatabase = NoteDatabase.getInstance(getApplication());

        radioGroupColors.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonPink) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(AddNoteActivity.this, R.color.surface_default));
            } else if (checkedId == R.id.radioButtonPurple) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(AddNoteActivity.this, R.color.surface_tropical_indigo));
            } else if (checkedId == R.id.radioButtonBlue) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(AddNoteActivity.this, R.color.surface_uranian_blue));
            }
        });

        buttonSave.setOnClickListener(view -> saveNote());
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonPink = findViewById(R.id.radioButtonPink);
        radioButtonPurple = findViewById(R.id.radioButtonPurple);
        buttonSave = findViewById(R.id.buttonSave);
        radioGroupColors = findViewById(R.id.radioGroupColors);
    }

    private void saveNote() {
        String text = editTextNote.getText().toString().trim();
        String color;
        if (radioButtonPink.isChecked()) {
            color = "pink";
        } else if (radioButtonPurple.isChecked()) {
            color = "purple";
        } else {
            color = "blue";
        }

        if (text.isEmpty()) {
            Toast.makeText(this, "The field is empty", Toast.LENGTH_SHORT).show();
        } else {

            Note note = new Note(text, color);
            noteDatabase.notesDao().add(note);

            finish(); // activity завершает работу
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}