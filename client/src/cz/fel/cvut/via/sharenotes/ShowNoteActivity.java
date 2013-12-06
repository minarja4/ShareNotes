package cz.fel.cvut.via.sharenotes;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cz.fel.cvut.via.asyncTasks.DeleteNoteTask;
import cz.fel.cvut.via.entities.Note;

public class ShowNoteActivity extends Activity {

	private Note actualNote = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_note);
		
		Note n  = (Note) getIntent().getSerializableExtra("note");
		actualNote = n;
		TextView name = (TextView) findViewById(R.id.showNoteName);
		TextView desc = (TextView) findViewById(R.id.showNoteDesc);
		
		name.setText(n.getName());
		desc.setText(n.getNote());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_note, menu);
		return true;
	}

	
	//kliknuti na button v Actionbaru
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.delete_note_menu:
	        	Log.d(Notes.class.getName(), "Deleting note");
	            
	        	DeleteNoteTask task = null;
	      	  
	      	    //mazene
	      		  task = new DeleteNoteTask();
	      		  task.execute(actualNote);
	      		  
	      		  Intent i = new Intent(this,Notes.class);
	      		  startActivity(i);
	      		  
	            return true;
	            
	        case R.id.share_note_menu:
	        	Intent in = new Intent(this, ShareNoteActivity.class);
	        	in.putExtra("note", actualNote);
	        	startActivity(in);
	        	return true;
	        case R.id.edit_note_menu:
	        	Intent inte = new Intent(this, EditNoteActivity.class);
	        	inte.putExtra("note", actualNote);
	        	startActivity(inte);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
		
	
}
