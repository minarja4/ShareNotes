package cz.fel.cvut.via.asyncTasks;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class GetMySharedNotesTask extends AsyncTask<String, Void, List<Note>>{

	private Gson gson = new Gson();
	
	@Override
	protected List<Note> doInBackground(String... params) {
		String ret = null;
		try {			
			ret = SendAndReceive.getAllMySharedNotes(Login.getLoggedUser().getUsername(), Login.getLoggedUser().getToken());
			
			List<Note> list = gson.fromJson(ret, new TypeToken<ArrayList<Note>>()
	                {
	                }.getType());
//			Log.d(GetMySharedNotesTask.class.getName(), "JSON: " + ret);
//			Log.d(GetMySharedNotesTask.class.getName(), "Pocet sdilenych poznamek: " + list.size());
			
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ret);
		
		return null;
	}

	
	
}
