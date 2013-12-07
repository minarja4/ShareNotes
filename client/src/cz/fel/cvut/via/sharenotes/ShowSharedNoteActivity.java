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
import cz.fel.cvut.via.entities.SharedNote;

public class ShowSharedNoteActivity extends Activity {

	private SharedNote actualNote = null;
	private boolean readOnly = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_note);
		
		actualNote = (SharedNote) getIntent().getSerializableExtra("note");
		readOnly = actualNote.isReadonly();
		
		setContent();
		
	}

	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {		
		menu.removeItem(R.id.share_note_menu);
		if (readOnly)
			menu.removeItem(R.id.edit_note_menu);
		
		return super.onPrepareOptionsMenu(menu);
	}



	private void setContent() {
		TextView name = (TextView) findViewById(R.id.showNoteName);
		TextView desc = (TextView) findViewById(R.id.showNoteDesc);
		
		name.setText(actualNote.getName());
		desc.setText(actualNote.getNote());
			
		Toast.makeText(this, actualNote.isReadonly()+ " readonly", Toast.LENGTH_LONG).show();
	
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
	      	  
	      	    //mazeme
	      		  task = new DeleteNoteTask();
	      		  task.execute(actualNote);
	      		  
	      		  Intent i = new Intent(this,Notes.class);
	      		  startActivity(i);
	      		  
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
			actualNote = (SharedNote) data.getSerializableExtra("editedNote");
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
