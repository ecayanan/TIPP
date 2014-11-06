	

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

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tipp.R;
     
     
    public class OnSearchFragment extends Fragment{
            //private SearchView searchview;
            private String searchStr;
            LinearLayout linearLayoutSearch;
            ArrayList <String> groupList;
            View view;
           
            public void filterText(String str) {
                    searchStr = str;
                    if(str.length() > 0 && linearLayoutSearch != null){
                            linearLayoutSearch.removeAllViews();
                            if(groupList != null){
                                    for(int i = 0; i < groupList.size(); i++){
                                            if(groupList.toArray()[i].toString().toLowerCase().contains(str.toLowerCase())){
                                                    TextView text = new TextView(view.getContext());
                                                    text.setText(groupList.toArray()[i].toString());
                                                    text.setTextSize(40);
                                                    linearLayoutSearch.addView(text);
                                            }
                                    }
                            }
                    }
            }
            /*
            @Override
            public void onPrepareOptionsMenu(Menu menu) {
                    super.onPrepareOptionsMenu(menu);
                    MenuItem menuItem =   menu.findItem(R.id.action_search);
                    searchview = (SearchView) menuItem.getActionView();
            }
            */
            @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                    view = inflater.inflate(R.layout.fragment_on_search, container, false);
                    int groupSize = getArguments().getInt("groupSize", 0);
                    groupList = getArguments().getStringArrayList("groupStringArray");
                    linearLayoutSearch = (LinearLayout)view.findViewById(R.id.linearlayoutsearch);
                              
                    TextView textArr[] = new TextView[groupSize];
                    for(int i = 0; i < groupSize; i++){
                    	View red;
                    	  //grab red square xml
                        if( i % 2 == 0)
                                red = inflater.inflate(R.layout.light_blue, linearLayoutSearch, false);
                        else
                                red = inflater.inflate(R.layout.blue_com, linearLayoutSearch, false);
                       
                        //text set
                        TextView text = (TextView) red.findViewById(R.id.gsearchtitle);
                        text.setText((CharSequence) groupList.toArray()[i]);
                       
                    //add view to linearLayout
                    linearLayoutSearch.addView(red);
                    }
            return view;
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

