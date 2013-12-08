package cz.fel.cvut.via.db.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cz.fel.cvut.via.db.ShareNotesDBHelper;
import cz.fel.cvut.via.db.Contract.ShareNotesEntry;
import cz.fel.cvut.via.entities.Note;

public class DeleteNoteFromDB {

	public static void deleteFromDBByLocalId(Note n, Context ctx) {
		
		ShareNotesDBHelper helper = new ShareNotesDBHelper(ctx, ShareNotesDBHelper.DATABASE_NAME, null, ShareNotesDBHelper.DATABASE_VERSION);
		
		SQLiteDatabase db  = helper.getWritableDatabase();
		
		
		String selection = ShareNotesEntry._ID + " LIKE ?";
		
		String[] selectionArgs = { String.valueOf(n.getDbId()) };
		
		db.delete(ShareNotesEntry.TABLE_NAME, selection, selectionArgs);
		
		db.close();
	}
	
}
