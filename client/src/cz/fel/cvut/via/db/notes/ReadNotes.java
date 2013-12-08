package cz.fel.cvut.via.db.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.fel.cvut.via.db.ShareNotesDBHelper;
import cz.fel.cvut.via.db.Contract.ShareNotesEntry;
import cz.fel.cvut.via.entities.Note;

public class ReadNotes {

	public static List<Note> getAllNotesForUser(String username, Context ctx) {
		ShareNotesDBHelper helper = new ShareNotesDBHelper(ctx, ShareNotesDBHelper.DATABASE_NAME, null, ShareNotesDBHelper.DATABASE_VERSION);
		
		SQLiteDatabase db  = helper.getReadableDatabase();
		
		
		String[] projection = {
			    ShareNotesEntry._ID,
			    ShareNotesEntry.COLUMN_NAME_VERSION,
			    ShareNotesEntry.COLUMN_NAME_NAME,
			    ShareNotesEntry.COLUMN_NAME_NOTE,
			    ShareNotesEntry.COLUMN_NAME_OWNER
			    };
		
		
		Cursor c = db.query(
			    ShareNotesEntry.TABLE_NAME,				  
			    projection,                               
			    ShareNotesEntry.COLUMN_NAME_OWNER + "="+ "\""+username+"\"",
			    null,                            
			    null,                            
			    null,                            
			    null                             
			    );
		
		c.moveToFirst();
		
		List<Note> list = new ArrayList<Note>();
		 while (!c.isAfterLast()) {
		      Note a = new Note();
		      a.setName(c.getString(2));
		      a.setNote(c.getString(3));
		      a.setVersion(c.getShort(1));
		      a.setOwner(c.getString(4));
		      a.setCached(true);		      
		      a.setDbId(c.getLong(0));
		      
		      list.add(a);
		      c.moveToNext();
		    }
		    
		    c.close();
		
		    
		db.close();    
		return list;
		
	}
	
}
