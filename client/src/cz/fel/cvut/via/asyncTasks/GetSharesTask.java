package cz.fel.cvut.via.asyncTasks;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.Share;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class GetSharesTask extends AsyncTask<Note, Void, List<Share>>{

	private Gson gson = new Gson();
	
	@Override
	protected List<Share> doInBackground(Note... params) {
		String ret = null;
		try {			
			ret = SendAndReceive.getSharesToNote(params[0],Login.getLoggedUser().getUsername(), Login.getLoggedUser().getToken());
			System.out.println("RET: " + ret);
			List<Share> list = gson.fromJson(ret, new TypeToken<ArrayList<Share>>()
	                {
	                }.getType());
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ret);
		
		return null;
	}

	
	
}
