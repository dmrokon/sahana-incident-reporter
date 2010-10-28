package cmu.mobilelab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import cmu.mobilelab.Impact.ImpactType;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

public class DBConnector {
	private static final String DATABASE_NAME = "incidentReporter.db";
	private static final String DATABASE_INCIDENTS_TABLE = "incidents";
	private static final String DATABASE_IMAGES_TABLE = "images";
	private static final String DATABASE_IMPACTS_TABLE = "impacts";
	private static final String DATABASE_CREATE = "create table " + DATABASE_INCIDENTS_TABLE + 
		" (incident_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		"timestamp TEXT, " +
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
			this.sqldb = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
			Cursor c = this.sqldb.rawQuery("SELECT COUNT(_id) FROM incidents;", null);
			if (c.getInt(0) < 1) {
				this.sqldb.execSQL(DATABASE_CREATE);
			}
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void close(){
		try {
			
			this.sqldb.close();
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	

	public void insertReport(IncidentReport report){
		Date date = report.getIncidentDate();
		Reporter reporter = report.getIncidentReporter();
		Integer category = (Integer)report.getIncidentCategory().ordinal();
			
		String comments = report.getIncidentComments();
		
		ArrayList<String> photos = report.getPhotoFileLocations();
		
		Map<ImpactType, Integer> impactMap = report.getIncidentImpact().getImpact();
		
		Iterator it = impactMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        Log.i("impact", pairs.getKey() + " = " + (Integer)pairs.getValue());
		}
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		String insertString = "INSERT INTO incidents VALUES (" +
			"NULL,'" +
			date_format.format(date) + "','" + 
			reporter.getReporterName() + "','" + 
			reporter.getContactDetails() + "'," +
			category.toString() + "," +
			"NULL, NULL, NULL,'" +
			comments + "');";

		try {
			
			Log.i("insertString", insertString);
			sqldb.execSQL(insertString);
		} catch(Exception e) {
			Log.i("Error", e.getMessage());
		}
	}

	
	public ArrayList<IncidentReport> getReports(Integer numReports){
		ArrayList<IncidentReport> reports = new ArrayList();
		IncidentReport report = new IncidentReport();
		
		String getString = "SELECT * FROM incidents LIMIT " + numReports.toString() + ";";
		SQLiteCursor c = null;
		
		try {
			Log.i("getString", getString);
			c = (SQLiteCursor)sqldb.rawQuery(getString, null);
		} catch(Exception e) {
			Log.i("Error", e.getMessage());		
		}
		
		while(c.isAfterLast()) {
			Log.i("cursor", c.getString(1));
			c.moveToNext();
		}
		
		reports.add(report);
		
		return reports;
	}
	
	public IncidentReport getReport(){
		ArrayList<IncidentReport> reports = this.getReports(1);
		return reports.get(0);
	}

}
