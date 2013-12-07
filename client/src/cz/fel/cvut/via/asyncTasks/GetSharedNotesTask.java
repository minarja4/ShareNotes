package cz.fel.cvut.via.asyncTasks;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.SharedNote;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class GetSharedNotesTask extends AsyncTask<String, Void, List<SharedNote>>{

	private Gson gson = new Gson();
	
	@Override
	protected List<SharedNote> doInBackground(String... params) {
		String ret = null;
		try {
			ret = SendAndReceive.getAllSharedNotes(Login.getLoggedUser().getUsername(), Login.getLoggedUser().getToken());
			
			List<SharedNote> list = gson.fromJson(ret, new TypeToken<ArrayList<SharedNote>>()
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
