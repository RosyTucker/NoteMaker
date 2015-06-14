package uk.co.iceroad.notemaker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ArrayList<String> noteList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView noteListView = (ListView) findViewById(R.id.noteListView);

        loadSavedNotes();
        updateNoteListView();

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                showEditNoteDialog(position);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add) {
            showAddNoteDialog();
            return true;
        }
        return false;
    }

    private void showAddNoteDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_note_dialog, (ViewGroup) findViewById(R.id.dialog));

        final AlertDialog newNoteDialog =  new AlertDialog.Builder(this).setView(layout).create();
        newNoteDialog.show();

        final EditText noteEditText = (EditText) newNoteDialog.findViewById(R.id.noteEditField);
        Button saveButton =(Button) newNoteDialog.findViewById(R.id.saveButton);
        Button cancelButton = (Button) newNoteDialog.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (noteEditText.getText().length() > 0) {
                    noteList.add(0, noteEditText.getText().toString());
                    updateNoteListView();
                }
                closeDialog(newNoteDialog);
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                closeDialog(newNoteDialog);
            }
        });

    }

    private void showEditNoteDialog(final int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.edit_note_dialog, (ViewGroup) findViewById(R.id.dialog));
        final AlertDialog editNoteDialog = new AlertDialog.Builder(this).setView(layout).create();
        editNoteDialog.show();

        final EditText editText = (EditText) editNoteDialog.findViewById(R.id.noteEditField);
        Button deleteButton =(Button) editNoteDialog.findViewById(R.id.deleteButton);
        Button editButton = (Button) editNoteDialog.findViewById(R.id.editButton);
        Button cancelButton = (Button) editNoteDialog.findViewById(R.id.cancelButton);

        editText.setText(noteList.get(position));
        editText.setSelection(editText.getText().length());

        editButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                noteList.set(position, editText.getText().toString());
                updateNoteListView();
                closeDialog(editNoteDialog);
            }
        });

        deleteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                noteList.remove(position);
                updateNoteListView();
                closeDialog(editNoteDialog);
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                closeDialog(editNoteDialog);
            }
        });
    }

    private void closeDialog(AlertDialog dialog) {
        if(dialog!= null){
            dialog.cancel();
            dialog.dismiss();
        }
    }

    private void updateNoteListView() {
        ListView noteListView = (ListView) findViewById(R.id.noteListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.note_list_item, R.id.noteTextView, noteList);
        noteListView.setAdapter(adapter);
        saveNewNote();
    }

    private void saveNewNote() {
        SharedPreferences prefs = this.getSharedPreferences("iceroad.notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putInt("number", noteList.size());
        for(int i = 0; i< noteList.size(); i++){
            editor.putString("note"+i, noteList.get(i));
        }
        editor.apply();
    }

    private void loadSavedNotes() {
        SharedPreferences prefs = getSharedPreferences("iceroad.notes", MODE_PRIVATE);
        int listSize = prefs.getInt("number", 0);
        if(listSize > 0){
            for(int i = 0; i< listSize; i++){
                noteList.add(i,prefs.getString("note"+i, "Sample Note"));
            }
        }
        updateNoteListView();
    }
}