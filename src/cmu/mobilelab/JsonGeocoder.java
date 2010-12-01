package cmu.mobilelab;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cmu.mobilelab.IncidentLocation;

import com.google.android.maps.GeoPoint;

public class JsonGeocoder {

	// http://code.google.com/p/android/issues/detail?id=8816&q=geocoder&colspec=ID%20Type%20Status%20Owner%20Summary%20Stars
	public static JSONObject getLocationInfo(String address) {

		HttpGet httpGet = new HttpGet("http://maps.google."
				+ "com/maps/api/geocode/json?address=" + address
				+ "ka&sensor=false");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static JSONObject getLocationInfo(IncidentLocation latlong) {

		HttpGet httpGet = new HttpGet("http://maps.google."
				+ "com/maps/api/geocode/json?latlng=" + latlong.getLatitude()
				+ "," + latlong.getLongitude() + "&sensor=false");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static IncidentLocation getLocation(JSONObject jsonObject) {

		Double lon = new Double(0);
		Double lat = new Double(0);

		try {

			lon = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");

			lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new IncidentLocation(lat, lon);

	}

	public static String getAddress(JSONObject jsonObject) {

		String address = "";
		try {

			JSONArray addrComp = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONArray("address_components");

			for (int i = 0; i < addrComp.length(); i++) {
				try {
					if (addrComp.getJSONObject(i).getJSONArray("types")
							.getString(0).equals("administrative_area_level_3")) {
						address += addrComp.getJSONObject(i).getString(
								"short_name")
								+ ", ";
						break;
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}

			for (int i = 0; i < addrComp.length(); i++) {

				try {
					if (addrComp.getJSONObject(i).getJSONArray("types")
							.getString(0).equals("administrative_area_level_2")) {
						address += addrComp.getJSONObject(i).getString(
								"short_name")
								+ ", ";
						break;
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}

			for (int i = 0; i < addrComp.length(); i++) {
				try {
					if (addrComp.getJSONObject(i).getJSONArray("types")
							.getString(0).equals("administrative_area_level_1")) {
						address += addrComp.getJSONObject(i).getString(
								"short_name")
								+ ", ";
						break;
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}

			for (int i = 0; i < addrComp.length(); i++) {
				try {

					if (addrComp.getJSONObject(i).getJSONArray("types")
							.getString(0).equals("country")) {
						address += addrComp.getJSONObject(i).getString(
								"short_name")
								+ ", ";
						break;
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return address;
		// return new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));

	}
}
