package com.angelhack.hackathong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class InputActivity extends Activity{
	AutoCompleteTextView origin;
	AutoCompleteTextView destination;
	SimpleAdapter adapter;
	Button submit ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		origin = (AutoCompleteTextView) findViewById(R.id.atv_origin);
		destination = (AutoCompleteTextView) findViewById(R.id.atv_destination);
		submit = (Button) findViewById(R.id.submit);
		origin.setThreshold(1);
		destination.setThreshold(1);
		
		origin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence place, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				new PlaceSearch().execute(place.toString());
				origin.setAdapter(adapter);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		destination.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence place, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				new PlaceSearch().execute(place.toString());
	            destination.setAdapter(adapter);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.angelhack.hackathong.MAIN2");
				String[] orides = new String[2];
				orides[0] = origin.getText().toString().trim();
				orides[1] = destination.getText().toString();
				intent.putExtra("origin", orides[0]);
				intent.putExtra("destination", orides[1]);
				startActivity(intent);
			}
			
		});
		
		
	}
	
    private String downloadUrl(String outurl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(outurl);
 
            urlConnection = (HttpURLConnection) url.openConnection();
 
            urlConnection.connect();
 
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        //System.out.println(data);
        return data;
    }
	
	public class PlaceSearch extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... place) {
			// TODO Auto-generated method stub
            String data = "";
 
            String key = "key=AIzaSyAFfQlDS_u_PneUcQ0ZFslD4w3VlMgwl6s";
 
            String input="";
 
            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String types = "types=geocode";
            String sensor = "sensor=false";
            String parameters = input+"&"+types+"&"+sensor+"&"+key;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
 
            try{
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            
            return data;
		}
		
		  @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            ParserTask parserTask = new ParserTask();
	            parserTask.execute(result);
	        }
		
	}
	
	public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
		JSONObject jObject;
		 
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> places = null;
 
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);

                places = placeJsonParser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
 
            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };
            
            adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);
 
        }	
	}
}
