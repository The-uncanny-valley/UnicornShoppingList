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