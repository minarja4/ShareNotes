package cz.fel.cvut.via.asyncTasks;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class GetMyNotesTask extends AsyncTask<String, Void, List<Note>>{

	private Gson gson = new Gson();
	
	@Override
	protected List<Note> doInBackground(String... params) {
		String ret = null;
		try {
			boolean mine = true;
			
			if (params[0].equals("shared")) mine = false;
			
			ret = SendAndReceive.getAllNotes(Login.getLoggedUser().getUsername(), Login.getLoggedUser().getToken(), mine);
			
			List<Note> list = gson.fromJson(ret, new TypeToken<ArrayList<Note>>()
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
