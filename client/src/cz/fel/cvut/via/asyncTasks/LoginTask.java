package cz.fel.cvut.via.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import cz.fel.cvut.via.entities.User;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.SendAndReceive;

public class LoginTask extends AsyncTask<User, Void, Void> {

	// user login
	@Override
	protected Void doInBackground(User... params) {
		try {
			String token = null;
			token = SendAndReceive.doInputOutput("/users/login", params[0], "POST");
	
			if (token == null) {
				throw new Exception();		
			}
			
			token = token.split(":")[1].replace("\"", "").replace("}", "");
			
	
			User user = params[0];
			user.setToken(token);
			Login.setLoggedUser(user);
			Log.d(LoginTask.class.getCanonicalName(), "User zalogovan");
		} catch (Exception e) {
			Log.e(LoginTask.class.getName(), "Chyba pri loginu!");
		}
		return null;

	}
	
	

}
