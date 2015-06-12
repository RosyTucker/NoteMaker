package uk.co.iceroad.notemaker;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private ArrayList<String> noteList = new ArrayList<>();
    private ListView noteLayout;
    private EditText edit;
    private AlertDialog newNoteDialog;
    private AlertDialog menuDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        noteLayout = (ListView)findViewById(R.id.listView1);
        Button newNote = (Button)findViewById(R.id.newNote);

        load();
        newNote.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                showNewNoteDialog(0, false);
            }
        });
        update();

        noteLayout.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                showMenuDialog(position);
                return true;
            }

        });
    }

    protected void save() {
        Log.d("setting", "Saving Notes");
        SharedPreferences prefs = this.getSharedPreferences("iceroad.notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putInt("number", noteList.size());
        for(int i = 0; i< noteList.size(); i++){
            editor.putString("note"+i, noteList.get(i));
        }
        editor.apply();
    }

    private void load() {
        SharedPreferences prefs = getSharedPreferences("iceroad.notes", MODE_PRIVATE);
        int listSize = prefs.getInt("number", 0);
        if(listSize > 0){
            for(int i = 0; i< listSize; i++){
                noteList.add(i,prefs.getString("note"+i, "Sample Note"));
            }
        }
        update();
    }

    private void showNewNoteDialog(final int position, final boolean isEdit) {
        Log.d("clocked","clicked");
        AlertDialog.Builder builder;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.customdialog,
                (ViewGroup) findViewById(R.id.layout_root));

        builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        newNoteDialog = builder.create();
        newNoteDialog.show();

        edit = (EditText) newNoteDialog.findViewById(R.id.editBox);
        if(isEdit){
            edit.setText(noteList.get(position));
        }
        Button okButton =(Button) newNoteDialog.findViewById(R.id.okButton);
        Button cancelButton = (Button) newNoteDialog.findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (edit.getText().length() > 0) {
                    if (!isEdit) {
                        noteList.add(position, edit.getText().toString());
                    } else {
                        noteList.set(position, edit.getText().toString());
                    }
                    update();
                }
                if (newNoteDialog != null) {
                    newNoteDialog.cancel();
                    newNoteDialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                edit.setText("");
                if (newNoteDialog != null) {
                    newNoteDialog.cancel();
                    newNoteDialog.dismiss();
                }
            }
        });
    }

    private void showMenuDialog(final int position) {
        AlertDialog.Builder builder;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custommenudialog,
                (ViewGroup) findViewById(R.id.dia_root));

        builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        menuDialog = builder.create();
        menuDialog.show();

        Button deleteButton =(Button) menuDialog.findViewById(R.id.deleteButton);
        Button editButton = (Button) menuDialog.findViewById(R.id.editButton);
        deleteButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                noteList.remove(position);
                update();
                menuDialog.cancel();
                menuDialog.dismiss();
            }
        });

        editButton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                menuDialog.cancel();
                menuDialog.dismiss();
                showNewNoteDialog(position,true);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        closeDialogs();
    }

    @Override
    public void onStop(){
        super.onStop();
        closeDialogs();
    }

    private void closeDialogs() {
        if(menuDialog!= null){
            menuDialog.cancel();
            menuDialog.dismiss();
        }
        if(newNoteDialog!= null){
            newNoteDialog.cancel();
            newNoteDialog.dismiss();
        }
    }

    private void update() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.menuitem, R.id.item, noteList);
        noteLayout.setAdapter(adapter);
        save();
    }
}