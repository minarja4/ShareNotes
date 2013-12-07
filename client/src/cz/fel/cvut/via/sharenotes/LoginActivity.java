package cz.fel.cvut.via.sharenotes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.security.auth.login.LoginException;

import cz.fel.cvut.via.asyncTasks.LoginTask;
import cz.fel.cvut.via.entities.User;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true; 
	}

	
	 //zalogovani uzivatele
    public void login(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	EditText user = (EditText) findViewById(R.id.username);
    	EditText pass = (EditText) findViewById(R.id.password);
    	
    	User u = new User();
    	u.setUsername(user.getText().toString());
    	u.setPassword(Utils.getSHA256(pass.getText().toString()));    	
    	
//    	Toast.makeText(this, "Overuji uzivatele...", Toast.LENGTH_SHORT).show();
    	
    	try {
    		LoginTask loginTask = new LoginTask();
    		loginTask.execute(u);
    		loginTask.get(); //wait for login to finish
    		
    		if (Login.getLoggedUser().getToken() == null)
    			throw new LoginException("Chyba");
    		
    		
    		
    		Toast.makeText(this, "User zalogovan: " + Login.getLoggedUser().getToken(), Toast.LENGTH_LONG).show();
    		Intent i = new Intent(this, Notes.class);
    		startActivity(i);
    		
    		
    	} catch (Exception e) {
    		Toast.makeText(this, "Chyba pri loginu", Toast.LENGTH_LONG).show();
    		e.printStackTrace();
    	}
    	
    	user.setText("");
    	pass.setText("");
    	
    }
	
    //registrace usera
    public void register(View view) {
    	Intent i = new Intent(this, RegisterActivity.class);
    	startActivity(i);
    }
}  
