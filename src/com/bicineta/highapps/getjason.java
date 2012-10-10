package com.bicineta.highapps;
/*clase para obtener los datos del punto*/
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getjason {

	HttpClient client = new DefaultHttpClient();
	final static String URL = "http://bicineta.herokuapp.com/points.json";
	StringBuilder informacion;
	String info;
	
	public StringBuilder info() 
    		throws ClientProtocolException, IOException, JSONException{
        HttpGet get = new HttpGet(URL.toString());
        HttpResponse r = client.execute(get);
        StringBuilder puntos = new StringBuilder();
        int status = r.getStatusLine().getStatusCode();
        if (status==200){
        	
        	HttpEntity e = r.getEntity();
        	String data = EntityUtils.toString(e);
        	JSONArray timeline =new JSONArray(data);
        	for(int i=0;i<timeline.length();i++){
	        	JSONObject last = timeline.getJSONObject(i);
	        	if(last.getString("latitude")!="null" || last.getString("longitude")!="null")
	        	{
	        		puntos.append(last.getString("place")+ "+" );
	        		puntos.append(last.getString("address")+ "+" );
	        		puntos.append(last.getString("latitude")+ "+" );
	        		puntos.append(last.getString("longitude")+ "+" );
	        		
	        	}
        	}
        	return puntos;
        }else{
    	    return null;
        }
        	
	}
}

