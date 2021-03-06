package cz.fel.cvut.via.sharenotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.DeleteNoteTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.SharedNote;

public class ShowNoteActivity extends Activity {

	private Note actualNote = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_note);
		
		
		actualNote = (Note) getIntent().getSerializableExtra("note");
		
		setContent();
		
	}

	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {				
		return super.onPrepareOptionsMenu(menu);
	}



	private void setContent() {
		TextView name = (TextView) findViewById(R.id.showNoteName);
		TextView desc = (TextView) findViewById(R.id.showNoteDesc);
		
		name.setText(actualNote.getName());
		desc.setText(actualNote.getNote());
		
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
	      		  
	      		  finish();
	      		  
	            return true;
	            
	        case R.id.share_note_menu:
	        	Intent in = new Intent(this, ShareNoteActivity.class);
	        	in.putExtra("note", actualNote);
	        	startActivityForResult(in,11);
	        	return true;
	        case R.id.edit_note_menu:
	        	Intent inte = new Intent(this, EditNoteActivity.class);
	        	inte.putExtra("note", actualNote);
	        	startActivityForResult(inte, 1);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {			
			actualNote = (Note) data.getSerializableExtra("editedNote");
			Log.d(ShowNoteActivity.class.getName(), "New note name: " + actualNote.getName());
			setContent();					
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		setResult(22);
		finish();
	}
	
	
	
}
