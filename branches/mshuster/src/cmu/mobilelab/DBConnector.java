package cmu.mobilelab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBConnector {
	private static final String DATABASE_NAME = "incidentReporter.db";
	private static final String DATABASE_TABLE = "incidents";
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, " +
			"column_one text not null);";
	
	private Context context;
	
	public SQLiteDatabase db;
	
	public DBConnector(Context context) {
		this.context = context;
		this.open();
	}
	
	public void open() {
		
		// TODO: open or create the database
		try {
			db = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
			
			db.execSQL(DATABASE_CREATE);
			
			//Toast.makeText(context, "Database and Table created", Toast.LENGTH_SHORT).show();
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void close(){
		try {
			
			db.close();
			
			Toast.makeText(context, "Database and Table created", Toast.LENGTH_SHORT).show();
			
		} catch(Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
/*	
	public void insertReport(IncidentReport report){
		
	}
	
	public IncidentReport[] getReports(int numReports){
		
	}
*/
}
