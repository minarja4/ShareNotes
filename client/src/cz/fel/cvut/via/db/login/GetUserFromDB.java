package cz.fel.cvut.via.db.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.fel.cvut.via.db.Contract.LoginEntry;
import cz.fel.cvut.via.db.ShareNotesDBHelper;
import cz.fel.cvut.via.entities.User;

public class GetUserFromDB {

	
	//vraci token z lokalni DB uzivatelu
	public static String getAllNotesForUser(String username, String password, Context ctx) {
		ShareNotesDBHelper helper = new ShareNotesDBHelper(ctx, ShareNotesDBHelper.DATABASE_NAME, null, ShareNotesDBHelper.DATABASE_VERSION);
		
		SQLiteDatabase db  = helper.getReadableDatabase();
				
		String[] projection = {
			   LoginEntry.COLUMN_NAME_TOKEN
			    };
		
		
		Cursor c = db.query(
			    LoginEntry.TABLE_NAME,				  
			    projection,                               
			    LoginEntry.COLUMN_NAME_USERNAME + "="+ "\""+username+"\" AND " + LoginEntry.COLUMN_NAME_PASS + "=" + "\"" +password + "\"",
			    null,                            
			    null,                            
			    null,                            
			    null                             
			    );
		
		c.moveToFirst();
		
		String token = "";
		try {
			token = c.getString(0);
		} catch (Exception e) {
			token = "";
		}
		
		c.close();
				
		    
		db.close();    

		return token;
		
	}
	
}
