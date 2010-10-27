package cmu.mobilelab;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBConnector {
	private static final String DATABASE_NAME = "incidentReporter.db";
	private static final String DATABASE_INCIDENTS_TABLE = "incidents";
	private static final String DATABASE_IMAGES_TABLE = "images";
	private static final String DATABASE_IMPACTS_TABLE = "impacts";
	private static final String DATABASE_CREATE = "create table " + DATABASE_INCIDENTS_TABLE + 
		" (incident_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		"timestamp DATETIME, " +
		"reporter_name TEXT, " +
		"reporter_contact TEXT, " + 
		"category INTEGER, " +
		"location_name TEXT, " +
		"location_lat REAL, " +
		"location_long REAL, " +
		"comments TEXT" +
		");" +
			"create table " + DATABASE_IMAGES_TABLE + 
		" (image_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		"incident_id INTEGER, " +
		"image_uri TEXT NOT NULL, " +
		"FOREIGN KEY(incident_id) REFERENCES incidents(incident_id));" +
			"create table " + DATABASE_IMPACTS_TABLE + 
		" (impact_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		"impact_type INT, " +
		"impact_value INT, " +
		"incident_id INTEGER, " +
		"FOREIGN KEY(incident_id) REFERENCES incidents(incident_id)" +
		");";
	
	private Context context;
	
	public SQLiteDatabase sqldb;
	
	public DBConnector(Context context) {
		this.context = context;
		this.open();
	}
	
	public void open() {
		
		// TODO: open or create the database
		try {
			sqldb = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
			
			sqldb.execSQL(DATABASE_CREATE);
			
			//Toast.makeText(context, "Database and Table created", Toast.LENGTH_SHORT).show();
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void close(){
		try {
			
			sqldb.close();
			
			//Toast.makeText(context, "Database and Table created", Toast.LENGTH_SHORT).show();
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	

	public void insertReport(IncidentReport report){
		
	}

	/*
	public IncidentReport[] getReports(int numReports){
		
	}
	
	public IncidentReport getReport(){
		return this.getReports(1);
	}
*/
}
