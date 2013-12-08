package cz.fel.cvut.via.db.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cz.fel.cvut.via.db.ShareNotesDBHelper;
import cz.fel.cvut.via.db.Contract.ShareNotesEntry;
import cz.fel.cvut.via.entities.Note;

public class SaveNoteToDB {

	public static void save(Context ctx, Note n) {
		ShareNotesDBHelper helper = new ShareNotesDBHelper(ctx, ShareNotesDBHelper.DATABASE_NAME, null, ShareNotesDBHelper.DATABASE_VERSION);
		
		SQLiteDatabase db  = helper.getWritableDatabase();
		
		ContentValues content = new ContentValues();
		content.put(ShareNotesEntry.COLUMN_NAME_NAME, n.getName());
		content.put(ShareNotesEntry.COLUMN_NAME_NOTE, n.getNote());
		content.put(ShareNotesEntry.COLUMN_NAME_OWNER, n.getOwner());
		content.put(ShareNotesEntry.COLUMN_NAME_VERSION, n.getVersion());
				
		long newRowId = db.insert(ShareNotesEntry.TABLE_NAME, null, content);
		
		Log.d(SaveNoteToDB.class.getName(), "New Row ID: " + newRowId);
		
		db.close();
	}
	
}
