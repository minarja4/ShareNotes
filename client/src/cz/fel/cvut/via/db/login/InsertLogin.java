package cz.fel.cvut.via.db.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cz.fel.cvut.via.db.Contract.LoginEntry;
import cz.fel.cvut.via.db.ShareNotesDBHelper;
import cz.fel.cvut.via.entities.User;

public class InsertLogin {

	public static void save(Context ctx, User u) {
		ShareNotesDBHelper helper = new ShareNotesDBHelper(ctx, ShareNotesDBHelper.DATABASE_NAME, null, ShareNotesDBHelper.DATABASE_VERSION);
		
		SQLiteDatabase db  = helper.getWritableDatabase();
		
		ContentValues content = new ContentValues();
		content.put(LoginEntry.COLUMN_NAME_EMAIL, u.getEmail());
		content.put(LoginEntry.COLUMN_NAME_PASS, u.getPassword());
		content.put(LoginEntry.COLUMN_NAME_TOKEN, u.getToken());
		content.put(LoginEntry.COLUMN_NAME_USERNAME, u.getUsername());
		
				
		long newRowId = db.insert(LoginEntry.TABLE_NAME, null, content);
		
		Log.d(InsertLogin.class.getName(), "New Row ID: " + newRowId);
		
		db.close();
	}
	
}
