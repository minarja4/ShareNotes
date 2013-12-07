package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.Carry;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class ShareNoteTask extends AsyncTask<Carry, Void, Void>{

	@Override
	protected Void doInBackground(Carry... params) {
		
		try {
			SendAndReceive.shareNote(params[0].getNote(), params[0].getUsername(), params[0].isReadOnly(), Login.getLoggedUser().getToken(), Login.getLoggedUser().getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
