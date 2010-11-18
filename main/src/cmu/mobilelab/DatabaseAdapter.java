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
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.Toast;

public class DatabaseAdapter implements IDataAccessConnector{
	private static final String TAG = "DatabaseAdapter";
	private static final String DATABASE_NAME = "incidentReporter.db";
	private static final int DATABASE_VERSION = 11;
	private static final String DATABASE_INCIDENTS_TABLE = "incidents";
	private static final String DATABASE_IMAGES_TABLE = "images";
	private static final String DATABASE_IMPACTS_TABLE = "impacts";
	private static final String LAST_INSERT_ROW_ID = "SELECT last_insert_rowid();";
	
	private static final String DATABASE_CREATE_INCIDENTS = "create table " + DATABASE_INCIDENTS_TABLE + 
	" (incident_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	"timestamp TEXT, " +
	"reporter_name TEXT, " +
	"reporter_contact TEXT, " + 
	"category INTEGER, " +
	"location_name TEXT, " +
	"location_lat REAL, " +
	"location_long REAL, " +
	"comments TEXT" +
	");";
	private static final String DATABASE_CREATE_IMPACTS= "create table " + DATABASE_IMPACTS_TABLE + 
	" (impact_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	"impact_type INTEGER, " +
	"impact_value INTEGER, " +
	"incident_id INTEGER, " +
	"FOREIGN KEY(incident_id) REFERENCES incidents(incident_id)" +
	");";
	private static final String DATABASE_CREATE_IMAGES = "create table " + DATABASE_IMAGES_TABLE + 
	" (image_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	"incident_id INTEGER, " +
	"image_uri TEXT NOT NULL, " +
	"FOREIGN KEY(incident_id) REFERENCES incidents(incident_id));";
	
	private Context context;
	
	private SQLiteDatabase sqldb;
	
	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(DATABASE_CREATE_INCIDENTS);
	        db.execSQL(DATABASE_CREATE_IMPACTS);
	        db.execSQL(DATABASE_CREATE_IMAGES);
	        //Log.i("database_create", DATABASE_CREATE);
	    }
	
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_INCIDENTS_TABLE + ";");
	        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_IMAGES_TABLE + ";");
	        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_IMPACTS_TABLE + ";");
	        onCreate(db);
	    }
	}
	
	private OpenHelper openHelper;
	
	DatabaseAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public void open() {
		this.openHelper  = new OpenHelper(this.context);
		this.sqldb = openHelper.getWritableDatabase();
		
		/*if (this.sqldb.isOpen() == false) {
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
		}*/
	}
	
	@Override
	public void close(){
		try {
			openHelper.close();
			//this.sqldb.close();
			
		} catch(Exception e) {
			//Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void insertReport(IncidentReport report){
		Integer incident_id = null;
		Date date = report.getIncidentDate();
		Reporter reporter = report.getIncidentReporter();
		Integer category = (Integer)report.getIncidentCategory().ordinal();
		IncidentLocation location = report.getIncidentLocation(); 
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();
		
		String comments = report.getIncidentComments();
		
		ArrayList<String> photos = report.getPhotoFileLocations();
		
		Map<ImpactType, Integer> impactMap = report.getIncidentImpact().getImpact();
		Iterator impactIterator = impactMap.entrySet().iterator();
		
		//SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		
		String incidentInsertString = "INSERT INTO incidents VALUES (" +
			"NULL,'" +
			date.toGMTString() + "','" + 
			reporter.getReporterName() + "','" + 
			reporter.getContactDetails() + "'," +
			category.toString() + ",'" +
			location.getLocationName() + "', " +
			latitude.toString() + "," +
			longitude.toString() + ",'" +
			comments + "');";

		try {
			Log.i("incidentInsertString", incidentInsertString);
			sqldb.execSQL(incidentInsertString);
		} catch(Exception e) {
			Log.i("Error", e.getMessage());
		}
		
		try {
			SQLiteCursor c = (SQLiteCursor)sqldb.rawQuery(LAST_INSERT_ROW_ID, null);
			c.moveToFirst();
			incident_id = c.getInt(0);
			c.close();
			Log.i("incident_id", incident_id.toString());
		} catch(Exception e) {
			Log.i("Error", e.getMessage());		
		}
		
	    while (impactIterator.hasNext()) {
	    	String impactInsertString;
	        Map.Entry pairs = (Map.Entry)impactIterator.next();
	        Log.i("impact", pairs.getKey() + " = " + (Integer)pairs.getValue());
	        
	        Impact.ImpactType impactType = ImpactType.valueOf(pairs.getKey().toString());
	        Integer impactValue = (Integer)pairs.getValue();
	        
	        impactInsertString = "INSERT INTO impacts VALUES (" +
			"NULL," + 
			impactType.ordinal() + "," +
			(Integer)pairs.getValue() + "," +
			incident_id +
			"); ";
	        
			try {
				Log.i("impactInsertString", impactInsertString);
				sqldb.execSQL(impactInsertString);
			} catch(Exception e) {
				Log.i("Error", e.getMessage());
			}
		}
		
	}

	@Override
	public ArrayList<IncidentReport> getReports(int numReports){
		ArrayList<IncidentReport> reports = new ArrayList<IncidentReport>();
		IncidentReport report = new IncidentReport();
		Integer intNumReports = numReports;
		String getIncidentString = "SELECT * FROM incidents LIMIT " + intNumReports.toString() + ";";
		SQLiteCursor c = null;
		
		try {
			Log.i("incidentString", getIncidentString);
			c = (SQLiteCursor)sqldb.rawQuery(getIncidentString, null);
			//Cursor cursor = sqldb.query("incidents", null, null, null, null, null, null, "1");
			//Integer num_rows = (Integer)c.getCount();
			//Log.i("getString", );
		} catch(Exception e) {
			Log.i("Error", e.toString());//e.getMessage());		
		}
		
		c.moveToFirst();
		while(!(c.isAfterLast())) {
			Log.i("cursor", c.getColumnName(1));
			Reporter newReporter = new Reporter();
			IncidentLocation newLocation = new IncidentLocation();
			for (int column = 0; column < c.getColumnCount(); column++) {
				String column_name = c.getColumnName(column);
				Log.i("column", column_name);
				int incident_id;
				
				if (column_name.equals("_id")) {
					incident_id = c.getInt(column);
				}
				else if (column_name.equals("timestamp")) {
					String date_string = c.getString(column);
					Date date = new Date(Date.parse(date_string));
					report.setIncidentDate(date);
				}
				else if (column_name.equals("reporter_name")) {
					newReporter.setReporterName(c.getString(column));
				}
				else if (column_name.equals("reporter_contact")) {
					newReporter.setContactDetails(c.getString(column));					
				}
				else if (column_name.equals("location_name")) {
					newLocation.setLocationName(c.getString(column));
				}
				else if (column_name.equals("location_lat")) {
					newLocation.setLongitude(c.getDouble(column));
				}
				else if (column_name.equals("location_long")) {
					newLocation.setLatitude(c.getDouble(column));
				}
				else if (column_name.equals("comments")) {
					report.setIncidentComments(c.getString(column));
				}
				else if (column_name.equals("category")) {
					IncidentReport.Category newCategory = IncidentReport.Category.values()[c.getInt(column)];
					Log.i("category", newCategory.toString());
					report.setIncidentCategory(newCategory);
					//report.setIncidentCategory(incidentCategory)
				}
			}
			
			/*
			String getImpactString = "SELECT impact_type, impact_value FROM incidents, impacts WHERE incidents.incident_id = impacts.incident_id LIMIT " + intNumReports.toString() + ";";
			SQLiteCursor c_impact = (SQLiteCursor)sqldb.rawQuery(getImpactString, null);
			
			try {
				Log.i("impactString", getImpactString);
				c = (SQLiteCursor)sqldb.rawQuery(getImpactString, null);
				Integer count = (Integer)c.getCount();
				Log.i("count", count.toString());
			} catch(Exception e) {
				Log.i("Error", e.getMessage());		
			}

			c_impact.moveToFirst();
			
			Impact newImpact = new Impact();
			while(!(c_impact.isAfterLast())) {
				Impact.ImpactType impact_type = null;
				int impact_value = 0;
				//Log.i("cursor", c.getString(1));
				for (int impact_column = 0;impact_column < c_impact.getColumnCount(); impact_column +=1) {
					int impact_type_int;
					for (int column = 0; column < c.getColumnCount(); column++) {
						String column_name = c.getColumnName(column);
						Log.i("column", column_name);
						if (column_name.equals("impact_type")) {
							impact_type_int = c_impact.getInt(impact_column);
							impact_type = Impact.ImpactType.values()[impact_type_int];
							
						}
						else if (column_name.equals("impact_value")) {
							impact_value = c_impact.getInt(impact_column);
						}
					}
					
				}
				newImpact.setImpact(impact_type, impact_value);
				c_impact.moveToNext();
			};
			report.setIncidentImpact(newImpact);
			Log.i("impact", newImpact.toString());
			*/
			
			report.setIncidentLocation(newLocation);
			report.setIncidentReporter(newReporter);

			
			ArrayList<String> photos = new ArrayList<String>();
			photos.add("None");
			report.setPhotoFileLocations(photos);

			
			reports.add(report);
			c.moveToNext();
		}
		
		return reports;
	}
	
	public IncidentReport getReport(){
		ArrayList<IncidentReport> reports = this.getReports(1);
		return reports.get(0);
	}

}