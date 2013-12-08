package cz.fel.cvut.via.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import cz.fel.cvut.via.db.Contract.LoginEntry;
import cz.fel.cvut.via.db.Contract.ShareNotesEntry;

public class ShareNotesDBHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String NUMBER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + ShareNotesEntry.TABLE_NAME + " (" +
        ShareNotesEntry._ID + " INTEGER PRIMARY KEY," +
        ShareNotesEntry.COLUMN_NAME_VERSION + NUMBER_TYPE + COMMA_SEP +
        ShareNotesEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
        ShareNotesEntry.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP +
        ShareNotesEntry.COLUMN_NAME_OWNER + TEXT_TYPE +       
        " )";

    private static final String SQL_CREATE_ENTRIES_LOGIN =
            "CREATE TABLE " + LoginEntry.TABLE_NAME + " (" +
            LoginEntry._ID + " INTEGER PRIMARY KEY," +
            LoginEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
            LoginEntry.COLUMN_NAME_PASS + TEXT_TYPE + COMMA_SEP +
            LoginEntry.COLUMN_NAME_TOKEN + TEXT_TYPE + COMMA_SEP +
            LoginEntry.COLUMN_NAME_USERNAME + TEXT_TYPE +       
            " )";

    
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ShareNotesEntry.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_LOGIN = "DROP TABLE IF EXISTS " + LoginEntry.TABLE_NAME;
    
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ShareNotes.db";
    
	
	
	public ShareNotesDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		db.execSQL(SQL_CREATE_ENTRIES_LOGIN);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		db.execSQL(SQL_DELETE_ENTRIES_LOGIN);
        onCreate(db);
	}
    
	
}
