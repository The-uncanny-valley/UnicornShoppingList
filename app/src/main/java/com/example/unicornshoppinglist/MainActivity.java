    package com.example.unicornshoppinglist;

    import android.os.Bundle;
    import androidx.activity.EdgeToEdge;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
                if (findViewById(R.id.add_note_container) != null) {
                    // Tablet: show both fragments side by side
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.notes_list_container, new NotesListFragment())
                            .replace(R.id.add_note_container, new AddNoteFragment())
                            .commit();
                } else {
                    // Phone: show only notes list in a single container
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new NotesListFragment())
                            .commit();
                }
            }
        }

        public void refreshNotes() {
            NotesListFragment fragment = (NotesListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.notes_list_container);
            if (fragment != null) {
                fragment.showNotes();
            }
        }
    }

//    public class MainActivity extends AppCompatActivity {
//
//        private RecyclerView recyclerViewNotes;
//        private FloatingActionButton buttonAddNote;
//        private NotesAdapter notesAdapter;
//
//        private Database database = Database.getInstance();
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            EdgeToEdge.enable(this);
//            setContentView(R.layout.activity_main);
//            initViews();
//
//            notesAdapter = new NotesAdapter();
//            notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
//                @Override
//                public void onNoteClick(Note note) {
//                }
//            });
//
//            recyclerViewNotes.setAdapter(notesAdapter);
//
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
//                    new ItemTouchHelper.SimpleCallback(
//                            0,
//                            ItemTouchHelper.END
//                    ) {
//                @Override
//                public boolean onMove(
//                        @NonNull RecyclerView recyclerView,
//                        @NonNull RecyclerView.ViewHolder viewHolder,
//                        @NonNull RecyclerView.ViewHolder target
//                ) {
//                    return false;
//                }
//
//                @Override
//                public void onSwiped(
//                        @NonNull RecyclerView.ViewHolder viewHolder,
//                        int direction
//                ) {
//                    int position = viewHolder.getAdapterPosition();
//                    Note note = notesAdapter.getNotes().get(position);
//                    database.remove(note.getId());
//                    showNotes();
//                }
//            });
//
//            buttonAddNote.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = AddNoteActivity.newIntent(MainActivity.this);
//                    startActivity(intent);
//                }
//            });
//
//            itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
//        }
//
//        @Override
//        protected void onResume() {
//            super.onResume();
//            showNotes();
//        }
//
//        private void initViews() {
//            recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
//            buttonAddNote = findViewById(R.id.buttonAddNote);
//        }
//
//        private void showNotes() {
//            notesAdapter.setNotes(database.getNotes());
//        }
//    }