package cz.fel.cvut.via.sharenotes;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.AddNoteTask;
import cz.fel.cvut.via.db.notes.SaveNoteToDB;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;

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
		n.setOwner(Login.getLoggedUser().getUsername());
		
		//jsme online?
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
		
		if (isConnected) {		
			AddNoteTask task = new AddNoteTask();
			task.execute(n);			
			task.get();			
			Toast.makeText(this, "Poznamka ulozena na server", Toast.LENGTH_SHORT).show();
		} else{
			SaveNoteToDB.save(this, n);			
			Toast.makeText(this, "Poznamka ulozena LOKALNE", Toast.LENGTH_SHORT).show();
		}
		
		finish();
	}
	                                                                                                                                                                                                                                                  
	@Override
	public void onBackPressed() {		
		finish();
	}
	
}
