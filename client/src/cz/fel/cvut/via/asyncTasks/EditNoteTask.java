package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class EditNoteTask extends AsyncTask<Note, Void, Void> {

	@Override
	protected Void doInBackground(Note... arg0) {
		try {
			SendAndReceive.editNote(arg0[0], Login.getLoggedUser().getToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
