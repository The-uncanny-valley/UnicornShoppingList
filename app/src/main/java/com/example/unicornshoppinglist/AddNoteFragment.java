package com.example.unicornshoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AddNoteFragment extends Fragment {

    private EditText editTextNote;
    private RadioButton radioButtonPink;
    private RadioButton radioButtonPurple;
    private Button buttonSave;
    private RadioGroup radioGroupColors;
    private LinearLayout linearLayoutNote;

    private NoteDatabase noteDatabase;
    boolean isTablet;

    public AddNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextNote = view.findViewById(R.id.editTextNote);
        radioButtonPink = view.findViewById(R.id.radioButtonPink);
        radioButtonPurple = view.findViewById(R.id.radioButtonPurple);
        buttonSave = view.findViewById(R.id.buttonSave);
        radioGroupColors = view.findViewById(R.id.radioGroupColors);
        linearLayoutNote = view.findViewById(R.id.linearLayoutNote);

        noteDatabase = NoteDatabase.getInstance(requireActivity().getApplication());
        isTablet = getResources().getBoolean(R.bool.isTablet);

        radioGroupColors.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonPink) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.surface_default
                ));
            } else if (checkedId == R.id.radioButtonPurple) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.surface_tropical_indigo
                ));
            } else if (checkedId == R.id.radioButtonBlue) {
                editTextNote.setBackgroundColor(ContextCompat.getColor(
                        requireContext(),
                        R.color.surface_uranian_blue
                ));
            }
        });

        buttonSave.setOnClickListener(v -> saveNote());
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
            Toast.makeText(requireContext(), "The field is empty", Toast.LENGTH_SHORT).show();
        } else {
            Note note = new Note(text, color);
            noteDatabase.notesDao().add(note);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).refreshNotes();
            }

            if (isTablet) {
                // Tablet: just clear inputs, keep fragment visible
                clearInputs();
            } else {
                // Phone: go back to previous fragment (NotesListFragment)
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

        }
    }

    public void clearInputs() {
        editTextNote.setText("");
        radioButtonPink.setChecked(true);
        editTextNote.setBackgroundColor(ContextCompat.getColor(
                requireContext(),
                R.color.surface_default
        ));
    }
}