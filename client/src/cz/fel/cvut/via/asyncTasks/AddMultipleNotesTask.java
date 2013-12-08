package cz.fel.cvut.via.asyncTasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class AddMultipleNotesTask extends AsyncTask<List<Note>,Void, Note> {

	@Override
	protected Note doInBackground(List<Note>... arg0) {
	
	
		try {
			for(Note n : arg0[0]) {				
				SendAndReceive.doInputOutputAuthenticated("/" + Login.getLoggedUser().getUsername() + "/notes/", n, "POST", Login.getLoggedUser().getToken());				
			}
		} catch (Exception e) {
			Log.e(AddNoteTask.class.getName(), "Chyba pri vytvareni poznamky.");
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
