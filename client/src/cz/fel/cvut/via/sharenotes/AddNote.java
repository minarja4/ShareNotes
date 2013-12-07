package cz.fel.cvut.via.sharenotes;

import java.util.concurrent.ExecutionException;

import cz.fel.cvut.via.asyncTasks.AddNoteTask;
import cz.fel.cvut.via.entities.Note;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddNote extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_note, menu);
		return true;
	}

	
	public void add(View view) throws InterruptedException, ExecutionException {
		EditText nameE = (EditText) findViewById(R.id.newName);
		EditText noteE = (EditText) findViewById(R.id.newNote);
		
		String name = nameE.getText().toString();
		String note = noteE.getText().toString();
		
		Note n = new Note();
		n.setName(name);
		n.setNote(note);
		
		AddNoteTask task = new AddNoteTask();
		task.execute(n);
		
		task.get();
		
		finish();
	}
	
	@Override
	public void onBackPressed() {		
		finish();
	}
	
}
