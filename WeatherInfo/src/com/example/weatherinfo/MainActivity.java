package com.example.weatherinfo;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends Activity {

	TextView tvHttp, tvJason;
	String result,stringUrl, myjasonString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}
	public void checkNetworkMethod(View v){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo nf=cm.getActiveNetworkInfo();
		if(nf!=null && nf.isConnected()){
			Toast.makeText(this, "network is connected now: "+nf.getTypeName(), Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "No network", Toast.LENGTH_LONG).show();
			
		}
	}

	public void findTempMethod(View v){
		tvHttp=(TextView) findViewById(R.id.textView1);
		GetjasonData gjd = new GetjasonData();

			myjasonString="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20%28select%20woeid%20from%20geo.places%281%29%20where%20text%3D%22Delhi%2C%20IN%22%29&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		
		// myjasonString="http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";
		gjd.execute(myjasonString);
	}
	
	
	class GetjasonData extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return downloadUrl(params[0]);
		}
		
		@Override
		protected void onPreExecute() {
		/*	 progressBar = (ProgressBar)findViewById(R.id.progressbar);
		        progressBar.setVisibility(View.VISIBLE);*/
			   super.onPreExecute();
			    TextView title = (TextView)findViewById(R.id.textView1);
	            title.setText("Loading.......");
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			tvHttp.setText(result);
		}
	}
	private String downloadUrl(String stgUrl){
		InputStream is=null;
		BufferedReader reader;
		String data=null; 
		try{
			URL url=new URL(stgUrl);
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			conn.getResponseCode();
			is=conn.getInputStream();
			reader=new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer=new StringBuffer();
			String line="";
			  String newLine=System.getProperty("line.seperator");
			  while((line=reader.readLine())!=null){
				  buffer.append(line+newLine);
			  }
			  reader.close();
			  data=buffer.toString();
		//	  return data;
			  
			 return getJasonData(data);
			  
		}catch(Exception e){
			return "Error in retriving url";
		}

	}
	private String getJasonData(String stgData){
	
		
		try{
			JSONObject jasonObj = new JSONObject(stgData); 
	        Log.i("abc", "json string = " + jasonObj);
	        
	      //  JASONDataObjectTemp myObj=new JASONDataObjectTemp();
	  
	        
	        JSONObject joQuery=jasonObj.getJSONObject("query");
	        
	    //    myObj.setQuery(query)
	        
	        
	        
	        JSONObject joResult=joQuery.getJSONObject("results");
	        JSONObject joChannel=joResult.getJSONObject("channel");
	       
	        JSONObject joUnits=joChannel.getJSONObject("units");
	        String stgTemperature=joUnits.getString("temperature");
	        
	        	//result=jasonObj.toString();
	        
	        JSONObject joItem=joChannel.getJSONObject("item");
	        JSONObject joCondition=joItem.getJSONObject("condition");
	        
	        String stgTemperatureValue=joCondition.getString("temp");
	        
	   //     (°F  -  32)  x  5/9 = °C
	        
	        double stgF=Double.parseDouble(stgTemperatureValue);
	        double celcius;
	        celcius=(stgF-32)*5/9;
	        
	        String ResultTemp=stgTemperatureValue+stgTemperature+"="+Double.toString(celcius)+"celcius";
	        
	        return ResultTemp;
	        
	        
	        
	        
		/*	JASONDataObject myObj=new JASONDataObject();
			
			
		//	ArrayList<String> userList = new ArrayList<String>(); 
			
			JSONArray jasonArray = jasonObj.getJSONArray("Users");
			for(int i=0;i<jasonArray.length();i++){
				
		       // JSONObject c = jasonArray.getJSONObject(i);
				jasonObj=jasonArray.getJSONObject(i);
                String firstname = jasonObj.getString("firstname");
                String lastname = jasonObj.getString("lastname");
                String username = jasonObj.getString("username");
                
               JSONObject jo=jasonObj.getJSONObject("Users");
               // String stgName=jo.getString("firstname");
               // if(stgName == "Mike"){
                //	result=jasonObj.toString();
                //	return result;
               // }
                Log.e("abc", "firstname: " + firstname 
                        + ", lastname: " + lastname
                        + ", username: " + username);
                
                if(firstname.equalsIgnoreCase("Mike")){
                	result=jasonObj.toString();
                	return result;
                }
                
	
			}
			
	        Log.i("abc", myObj.toString());
	        result=jasonObj.toString();
	        
			return result;*/
	      //  return stgData;
	        
		}catch(JSONException e){

			 throw new RuntimeException(e);
	     
		}

	}


}
