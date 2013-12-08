package cz.fel.cvut.via.db;

import android.provider.BaseColumns;

public final class Contract {

	public Contract(){}
	
	public static abstract class ShareNotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "shareNotes";
        public static final String COLUMN_NAME_VERSION = "version";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_OWNER = "owner";                       
    }
	
	public static abstract class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASS = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_TOKEN = "token";                       
    }
	
}
