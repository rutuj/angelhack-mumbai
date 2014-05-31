package com.angelhack.hackathong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	String[] orides=new String[2];
	List<LatLng> ll;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        Bundle extras = getIntent().getExtras();
        orides[0] = extras.getString("origin");
        orides[1] = extras.getString("destination");
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMyLocationEnabled(true);
        new getGeoPoints().execute(orides);
    }
    public class getGeoPoints extends AsyncTask<String[],String[],Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Marker origin = googleMap.addMarker(new MarkerOptions().position(ll.get(0)).title("Origin"));
			Marker destination = googleMap.addMarker(new MarkerOptions().position(ll.get(1)).title("Destination"));
		}

		@Override
		protected Void doInBackground(String[]... arg0) {
			// TODO Auto-generated method stub
			if(Geocoder.isPresent()){
			    try {
			        String location_origin = orides[0];
			        String location_destination = orides[1];
			        Geocoder gc = new Geocoder(getBaseContext());
			        List<Address> addresses1= gc.getFromLocationName(location_origin, 5); 
			        List<Address> addresses2= gc.getFromLocationName(location_destination, 5);
			        ll = new ArrayList<LatLng>(addresses1.size()); 
			        for(Address a : addresses1){
			            if(a.hasLatitude() && a.hasLongitude()){
			                ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
			            } 
		            for(Address b : addresses2){
			            if(b.hasLatitude() && b.hasLongitude()){
			                ll.add(new LatLng(b.getLatitude(), b.getLongitude()));
			            	} 
			        	}
			        }
			    } catch (IOException e) {
			         // handle the exception
			    }
			}
			
			return null;
		}
		
		
		
		
    	
    }
}

