package cz.fel.cvut.via.sharenotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.EditNoteTask;
import cz.fel.cvut.via.entities.Note;

public class EditNoteActivity extends Activity {

	private EditText name = null, desc = null;
	private Note editedNote = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		
		editedNote = (Note) getIntent().getSerializableExtra("note");		
		
		name = (EditText) findViewById(R.id.editName);
		desc = (EditText) findViewById(R.id.editDesc);
		
		name.setText(editedNote.getName());
		desc.setText(editedNote.getNote());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_note, menu);
		return true;
	}
	
	
	public void edit(View view) {
		String nam = name.getText().toString();
		String des = desc.getText().toString();
		
		editedNote.setName(nam);
		editedNote.setNote(des);
		
		EditNoteTask task = new EditNoteTask();
		task.execute(editedNote);
		
		Toast.makeText(this, "Poznamka upravena", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, Notes.class);
		i.putExtra("editedNote", editedNote);
//		startActivity(i);
		setResult(1, i);
		finish();
		
	}

}
