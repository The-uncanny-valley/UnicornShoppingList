package com.example.unicornshoppinglist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteCheckedChangeListener onNoteCheckedChangeListener;

    public void setOnNoteCheckedChangeListener(OnNoteCheckedChangeListener onNoteCheckedChangeListener) {
        this.onNoteCheckedChangeListener = onNoteCheckedChangeListener;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());

        int colorResId;

        if (note.getColor().equals("pink")) {
            colorResId = R.color.surface_default;
        } else if (note.getColor().equals("blue")) {
            colorResId = R.color.surface_uranian_blue;
        } else {
            colorResId = R.color.surface_tropical_indigo;
        }

        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.textViewNote.setBackgroundColor(color);
        viewHolder.linearLayoutNote.setBackgroundColor(color);

        // --- Checkbox state ---
        viewHolder.checkBoxNote.setOnCheckedChangeListener(null); // avoid old listeners firing
        viewHolder.checkBoxNote.setChecked(note.isChecked());

        // Strike-through text if checked
        if (note.isChecked()) {
            viewHolder.textViewNote.setPaintFlags(
                    viewHolder.textViewNote.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            viewHolder.textViewNote.setPaintFlags(
                    viewHolder.textViewNote.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }


        // Listen for changes
        viewHolder.checkBoxNote.setOnCheckedChangeListener((buttonView, isChecked) -> {
            note.setChecked(isChecked);

            // Update text appearance immediately
            if (isChecked) {
                viewHolder.textViewNote.setPaintFlags(
                        viewHolder.textViewNote.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                );
            } else {
                viewHolder.textViewNote.setPaintFlags(
                        viewHolder.textViewNote.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
                );
            }

            // Notify the listener (Activity/Fragment) to update DB
            if (onNoteCheckedChangeListener != null) {
                onNoteCheckedChangeListener.onNoteCheckedChanged(note, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNote;
        private final LinearLayout linearLayoutNote;
        private final CheckBox checkBoxNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
            linearLayoutNote = itemView.findViewById(R.id.linearLayoutNote);
            checkBoxNote = itemView.findViewById(R.id.checkBoxNote);
        }
    }

    public interface OnNoteCheckedChangeListener {
        void onNoteCheckedChanged(Note note, boolean isChecked);
    }
}
