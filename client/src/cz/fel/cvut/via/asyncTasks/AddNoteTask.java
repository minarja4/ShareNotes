package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class AddNoteTask extends AsyncTask<Note,Void, Note> {

	@Override
	protected Note doInBackground(Note... arg0) {
	
		try {
			SendAndReceive.doInputOutputAuthenticated("/" + Login.getLoggedUser().getUsername() + "/notes/", arg0[0], "POST", Login.getLoggedUser().getToken());
		} catch (Exception e) {
			Log.e(AddNoteTask.class.getName(), "Chyba pri vytvareni poznamky.");
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
