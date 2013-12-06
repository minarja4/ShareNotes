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
