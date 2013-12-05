package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import cz.fel.cvut.via.entities.User;
import cz.fel.cvut.via.utils.SendAndReceive;

public class RegisterTask extends AsyncTask<User, String, User> {

	private Gson gson = new Gson();
	
	@Override
	protected User doInBackground(User... users) {
		User u = null;
		try {
			String res = SendAndReceive.doInputOutput("/users/", users[0], "POST");
			u = gson.fromJson(res, User.class);
			u.setPassword(users[0].getPassword());
			Log.d(RegisterTask.class.getName(), "Vytvoren uzivatel id: " + u.getId());
			
		} catch (Exception e) {
			Log.e(RegisterTask.class.getName(), "Chyba pri registraci uzivatele!");
			e.printStackTrace();
		}
				
		return u;
	}
		
	
}
