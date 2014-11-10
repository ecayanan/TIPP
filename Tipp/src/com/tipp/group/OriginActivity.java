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
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tipp.R;
import com.tipp.group.adt.GroupManager;
     
public class OriginActivity extends Activity{
 
      private OnSearchListFragment searchFragment;
      private GroupManager groupManager;
      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php?userid=1"});
    }
       
    private class DownloadJSONTask extends AsyncTask<String,Integer,JSONObject> {
       
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);
            Log.d("JSON Thing","lets get started");
            try {
                HttpResponse execute_response = client.execute(httpGet);
                        StatusLine statusLine = execute_response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        if(statusCode == 200){
                    InputStream content = execute_response.getEntity().getContent();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String response = "",s = "";
 
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                    Log.d("MY_APP",response);
                    result = new JSONObject(response);
                        } else {
                                
                        }
            } catch (Exception e) {
               
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            return result;
        }
 
        protected void onPostExecute(JSONObject result) {
            try {
            	groupManager = new GroupManager(result);
                startGroupFragment(groupManager.getGroupJoinedBundle());
            } catch (Exception e) {
                Log.d("EXCEPTION", e.getMessage());
            } 
        }
       
    
 
    }
    public void startGroupFragment(Bundle bundle){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.origin_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
    public void startOnSearchFragment(Bundle bundle){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        searchFragment = new OnSearchListFragment();
        searchFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.origin_container, searchFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }   
    
    public void startOnSearchListFragment(Bundle bundle){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        OnSearchFragment searchFragment = new OnSearchFragment();
        searchFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.origin_container, searchFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }   
    
    
    public void startGroupFragment(View view){
    	new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php?userid=1"});
    }
    
    public void startOnSearchFragment(View view){
    	startOnSearchListFragment(groupManager.getGroupNotJoinedBundle());
    }
}

