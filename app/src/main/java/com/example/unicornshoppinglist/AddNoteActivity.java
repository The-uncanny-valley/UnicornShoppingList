package com.example.unicornshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonPink;
    private RadioButton radioButtonPurple;
    private RadioButton radioButtonBlue;
    private Button buttonSave;

    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonBlue = findViewById(R.id.radioButtonBlue);
        radioButtonPink = findViewById(R.id.radioButtonPink);
        radioButtonPurple = findViewById(R.id.radioButtonPurple);
        buttonSave = findViewById(R.id.buttonSave);
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
            int id = database.getNotes().size();
            Note note = new Note(id, text, color);
            database.add(note);

            finish();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}