package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.SharesNoteCarry;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class EditMySharesTask extends AsyncTask<SharesNoteCarry, Void, Void> {

	@Override
	protected Void doInBackground(SharesNoteCarry... params) {
		try {
			SendAndReceive.updateMyShares(params[0].getNote(), params[0].getList(), Login.getLoggedUser().getToken(), Login.getLoggedUser().getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
