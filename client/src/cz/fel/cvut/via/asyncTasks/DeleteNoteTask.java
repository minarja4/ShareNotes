package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class DeleteNoteTask extends AsyncTask<Note, Void, Void> {

	@Override
	protected Void doInBackground(Note... params) {
		try {
			SendAndReceive.deleteNote(params[0], Login.getLoggedUser().getToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
}
