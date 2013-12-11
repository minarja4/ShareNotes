package cz.fel.cvut.via.sharenotes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import cz.fel.cvut.via.asyncTasks.RegisterTask;
import cz.fel.cvut.via.entities.User;
import cz.fel.cvut.via.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	//vytvoreni noveho uzivatele
    public void register(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	EditText usernameE = (EditText) findViewById(R.id.createUsername);
    	EditText emailE = (EditText) findViewById(R.id.createEmail);
    	EditText passE = (EditText) findViewById(R.id.createPassword);
    	EditText passCheckE = (EditText) findViewById(R.id.createCheckPassword);
    	
    	String username = usernameE.getText().toString();
    	String email = emailE.getText().toString();
    	String pass = passE.getText().toString();
    	String passCheck = passCheckE.getText().toString();
    	
    	if(!pass.equals(passCheck)){
			Toast.makeText(this, "Zadaná hesla nebyla stejná", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	User u = new User();
    	u.setEmail(email);
    	u.setPassword(Utils.getSHA256(pass));
    	u.setUsername(username);
    	
    	Toast.makeText(this, "Vytvarim uzivatele...", Toast.LENGTH_SHORT).show();
    	
    	RegisterTask task = new RegisterTask();
    	AsyncTask<User, String, User> t = task.execute(u);
    	
    	try {
			User result = t.get();
			
			if (result == null) {
				Log.e(RegisterActivity.class.getName(), "Uzivatel NEbyl vytvoren.");
				Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
			}
		
			
			Toast.makeText(this, "Uzivatel uspesne vytvoren", Toast.LENGTH_SHORT).show();
			finish();
			
		} catch (InterruptedException e) {
			Log.e(RegisterActivity.class.getName(), "Vytvareni uzivatele bylo preruseno.");
			Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
			Log.e(RegisterActivity.class.getName(), "Vytvareni uzivatele nebylo dokonceno.");
		}
    	
    	
    
    	
    	
    }

	@Override
	public void onBackPressed() {
		setResult(22);
		finish();
	}
    
}
