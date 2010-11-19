package cmu.mobilelab;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataHelper {


	private static final String DATABASE_NAME = "sony.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "pictures";
	
	private Context context;
	private SQLiteDatabase db;
	private OpenHelper openHelper;
	
	private static final String INSERT = "insert into " + TABLE_NAME + "(incident_id, image_uri) values (?, ?)";
	
	
	public DataHelper(Context context) {
		this.context 			= context;
		this.openHelper 		= new OpenHelper(this.context);
		this.db 				= openHelper.getWritableDatabase();
	}
	
	
	public void insert(int incidentID, String imageURI) {
		
		SQLiteStatement insertStmt = this.db.compileStatement(INSERT);
		insertStmt.bindLong(1, incidentID);
		insertStmt.bindString(2, imageURI);
		insertStmt.executeInsert();
		
		this.openHelper.close();
	}
      
	public void deleteAll() {
		this.db.delete(TABLE_NAME, null, null);
		this.openHelper.close();
	}
	
	public String select(String incidentID) {
		
		String result = new String();
		
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "image_uri" }, "incident_id=" + incidentID, null, null, null, null);

		/* Guaranteed to get only one result */
		if(cursor.moveToFirst()) {
			do {
				result = cursor.getString(0);
			} while(cursor.moveToNext());
		}
		
		if((cursor != null) && (!cursor.isClosed())) {
			cursor.close();
		}
		
		this.openHelper.close();
		
		if(result.length() > 0) {
			return result;
		} else {
			return null;
		}
		
	}
   
	public ArrayList<String> selectAll(int incidentID) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "image_uri" }, "incident_id=" + incidentID, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while(cursor.moveToNext());
		}
		
		if((cursor != null) && (!cursor.isClosed())) {
			cursor.close();
		}
		
		this.openHelper.close();
		
		return list;
		
	}
	
	
	private static class OpenHelper extends SQLiteOpenHelper {
		
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (image_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					"incident_id INTEGER, " +
					"image_uri TEXT NOT NULL)");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
	
}
