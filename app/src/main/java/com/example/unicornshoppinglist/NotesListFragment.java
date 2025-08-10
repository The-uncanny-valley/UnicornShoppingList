package com.example.unicornshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesListFragment extends Fragment {

    private RecyclerView recyclerViewNotes;
    private View buttonAddNote;
    private NotesAdapter notesAdapter;
    private NoteDatabase noteDatabase = NoteDatabase.getInstance(requireActivity().getApplication());

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewNotes = view.findViewById(R.id.recyclerViewNotes);
        buttonAddNote = view.findViewById(R.id.buttonAddNote);

        notesAdapter = new NotesAdapter();

        recyclerViewNotes.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.END
        ) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction
            ) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                noteDatabase.notesDao().remove(note.getId());
                showNotes();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonAddNote.setOnClickListener(v -> {
            View addNoteContainer = requireActivity().findViewById(R.id.add_note_container);
            // Check if add_note_container exists (tablet mode)
            boolean isTablet = addNoteContainer != null;

            if (isTablet) {
                // Tablet mode
                AddNoteFragment addNoteFragment = (AddNoteFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.add_note_container);
                if (addNoteFragment != null) {
                    addNoteFragment.clearInputs();
                }
            } else {
                // Phone mode
                Intent intent = AddNoteActivity.newIntent(getContext());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showNotes();
    }

    void showNotes() {
        notesAdapter.setNotes(noteDatabase.notesDao().getNotes());
    }
}