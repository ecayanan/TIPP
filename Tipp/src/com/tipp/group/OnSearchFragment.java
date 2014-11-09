	

    package com.tipp.group;
     
     
    import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tipp.R;
     
     
public class OnSearchFragment extends ListFragment{
    
	private String searchText = "";
	ArrayAdapter<String> adapter;
	
	public void searchFilterText(String str){
		searchText = str;
		if(adapter != null)
			adapter.getFilter().filter(searchText);
	}
    @Override
     public void onActivityCreated(Bundle savedInstanceState) {
	    final String searchStr;
	    LinearLayout linearLayoutSearch;
	    ArrayList <String> groupList;
	    View view;
  
       //get bundle and get all groups array
       groupList = getArguments().getStringArrayList("groupStringArray");
       super.onActivityCreated(savedInstanceState);
       adapter = new ArrayAdapter<String>(getActivity(),R.layout.light_blue, R.id.gsearchtitle, groupList);
       setListAdapter(adapter);
     }

     @Override
     public void onListItemClick(ListView l, View v, int position, long id) {
       // do something with the data
     }
            private class AddToGroup extends AsyncTask<String,Integer,String> {
            String data = "";
            String Content = "";
            String searchtext = "";
           
            protected void onPreExecute() {
                // NOTE: You can call UI Element here.
                 
                //Start Progress Dialog (Message)
               
                 
                try{
                    // Set Request parameter
                            //searchtext = searchview.getQuery().toString();
                            int currentUserId = 1;
                    data +="&" + URLEncoder.encode("groupid", "UTF-8") + "=" + searchtext + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId;
                         
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 
            }        
            protected String doInBackground(String... urls) {
                JSONObject result = null;
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urls[0]); // Don't do this
                Log.d("JSON Thing","lets get started");
                //test.setText("before try");
                try {
                   
                    // Defined URL  where to send data
                    URL url = new URL(urls[0]);
                       
                   // Send POST data request
         
                   URLConnection conn = url.openConnection();
                   conn.setDoOutput(true);
                   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                   wr.write( data );
                   wr.flush();
               
                   // Get the server response
                     
                   BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   StringBuilder sb = new StringBuilder();
                   String line = null;
                 
                     // Read Server Response
                     while((line = reader.readLine()) != null)
                         {
                                // Append server response in string
                                sb.append(line + "");
                         }
                     
                     // Append Server Response To Content String
                    Content = sb.toString();
                } catch (Exception e) {
                   
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
     
                } // Don't do this
                //return result;
                return Content;
            }
     
            protected void onPostExecute(String result) {
            }
           
     
     
        }          
     
     
    }

