package cz.fel.cvut.via.sharenotes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.security.auth.login.LoginException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.LoginTask;
import cz.fel.cvut.via.asyncTasks.RegisterTask;
import cz.fel.cvut.via.entities.User;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.Utils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
               
        
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
    
    //zalogovani uzivatele
    public void login(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	EditText user = (EditText) findViewById(R.id.jmeno);
    	EditText pass = (EditText) findViewById(R.id.heslo);
    	
    	User u = new User();
    	u.setUsername(user.getText().toString());
    	u.setPassword(Utils.getSHA256(pass.getText().toString()));    	
    	
    	Toast.makeText(this, "Overuji uzivatel...", Toast.LENGTH_SHORT).show();
    	
    	try {
    		LoginTask loginTask = new LoginTask();
    		loginTask.execute(u);
    		loginTask.get(); //wait for login to finish
    		
    		if (Login.getLoggedUser().getToken() == null)
    			throw new LoginException("Chyba");
    		
    		Toast.makeText(this, "User zalogovan: " + Login.getLoggedUser().getToken(), Toast.LENGTH_LONG).show();
    		
    	} catch (Exception e) {
    		Toast.makeText(this, "Chyba pri loginu", Toast.LENGTH_LONG).show();;
    	}
    	
    	
    }
    
    
    //vytvoreni noveho uzivatele
    public void register(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	EditText usernameE = (EditText) findViewById(R.id.createUsername);
    	EditText emailE = (EditText) findViewById(R.id.createEmail);
    	EditText passE = (EditText) findViewById(R.id.createPassword);
    	
    	String username = usernameE.getText().toString();
    	String email = emailE.getText().toString();
    	String pass = passE.getText().toString();
    	
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
				Log.e(MainActivity.class.getName(), "Uzivatel NEbyl vytvoren.");
				Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
			}
			
		} catch (InterruptedException e) {
			Log.e(MainActivity.class.getName(), "Vytvareni uzivatele bylo preruseno.");
			Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			Toast.makeText(this, "Chyba pri vytvareni usera", Toast.LENGTH_LONG).show();
			Log.e(MainActivity.class.getName(), "Vytvareni uzivatele nebylo dokonceno.");
		}
    	
    	Toast.makeText(this, "Uzivatel uspesne vytvoren", Toast.LENGTH_SHORT).show();
    	
    }
    
    //prechod na aktivitu s poznamkama
    public void notes(View view) throws Exception {
//    	if (Login.getLoggedUser() == null) {
//    		Toast.makeText(this, "Neni zalogovan uzivatel!", Toast.LENGTH_LONG).show();
//    		return;
//    	}
    	
    	
    	Intent intent = new Intent(this, Notes.class);
    	startActivity(intent);
    	
    }
    
    //zobrazeni aktivity na novou poznamku
    public void add(View view) {
    	Intent i = new Intent(this, AddNote.class);
    	startActivity(i);
    }
    
}
