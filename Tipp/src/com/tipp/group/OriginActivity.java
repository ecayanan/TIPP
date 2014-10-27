package com.tipp.group;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.tipp.R;

public class OriginActivity extends ActionBarActivity {

	private TextView test;
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    // Configure the search info and add any event listeners
	    return super.onCreateOptionsMenu(menu);

	  } 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_origin);
		test = (TextView) findViewById(R.id.text1);
		//test.setText("OnCreate");
        new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php"});		
	}
	
	private class DownloadJSONTask extends AsyncTask<String,Integer,JSONObject> {
        

        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://time.jsontest.com"); // Don't do this
            Log.d("JSON Thing","lets get started");
            //test.setText("before try");
            try {
                //test.setText("before response");

                HttpResponse execute_response = client.execute(httpGet);
                //test.setText("IT SHOWED UP");
        		StatusLine statusLine = execute_response.getStatusLine();
        		int statusCode = statusLine.getStatusCode();
        		if(statusCode == 200){
                    //test.setText("after execute");
                    InputStream content = execute_response.getEntity().getContent();
                    //test.setText("after response");

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

                    String response = "",s = "";

                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                    //test.setText("doinbackground");
                    Log.d("MY_APP",response);
                    result = new JSONObject(response);
        		} else {
        			//test.setText(statusCode);
        		} 

            } catch (Exception e) {
            	
            	StringWriter sw = new StringWriter();
            	e.printStackTrace(new PrintWriter(sw));
            	String exceptionAsString = sw.toString();
            	
            	//test.setText(exceptionAsString);
            } // Don't do this

            return result;
        }

        protected void onPostExecute(JSONObject result) {
        	test.setText("onpostexecute");
            try {
            	String tempArray = "";
            	String temp = "";


            		
            	temp = result.getString("time");
            	test.setText(temp);
            } catch (Exception e) {} // Again don't do this
        }

    }	
}