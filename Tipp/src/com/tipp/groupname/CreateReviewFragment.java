package com.tipp.groupname;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.tipp.R;

public class CreateReviewFragment extends ListFragment {
	private String currentUserId;
	private int groupid;
	private String memberid;
	private String review = "";
	private ArrayAdapter adapter;
	private ArrayList<String>reviewList = new ArrayList<String>();;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_review, container, false);
		currentUserId = getArguments().getString("user_ID");
		//currentUserId = getArguments().getInt("userId");
		groupid = getArguments().getInt("groupId");
		memberid = getArguments().getString("memberId");
		//adapter = new ArrayAdapter<String>(getActivity(),R.layout.light_blue, R.id.gsearchtitle, reviewList);
		final EditText text = (EditText) view.findViewById(R.id.message);
		Button btnSend = (Button) view.findViewById(R.id.btnSendMessage);
		btnSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				review = text.getText().toString();
				if(review != ""){
					text.setText("");
					Log.d("message is ", review);
					reviewList.add(review);
					adapter = new ArrayAdapter<String>(getActivity(),R.layout.review_view, R.id.gsearchtitle, reviewList);
					setListAdapter(adapter);
					new SendReview().execute(new String[]{"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/createReview.php"});
				}
			}
		});
		new obtainReviews().execute(new String[]{"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/oldReviews.php"});
		return view;
	}
	
	private class SendReview extends AsyncTask<String,Integer,String> {
	    String data = "";
	    String Content = "";
	    String searchtext = "";
	   
	    protected void onPreExecute() {
	        // NOTE: You can call UI Element here.
	         
	        //Start Progress Dialog (Message)
	       
	         
	        try{
	            // Set Request parameter
	                    //searchtext = searchview.getQuery().toString();
	            data +="?" + URLEncoder.encode("groupid", "UTF-8") + "=" + groupid + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId + "&" + URLEncoder.encode("receiver","UTF-8") + "=" + memberid + "&" + URLEncoder.encode("content","UTF-8") + "=" + URLEncoder.encode(review,"UTF-8");
	            Log.d("CRF user_ID = ", currentUserId);
	            Log.d("CRF member_ID = ",""+memberid);
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
	            URL url = new URL(urls[0] + data);
	               
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
	
	private class obtainReviews extends AsyncTask<String,Integer,JSONArray> {
	    String data = "";
	    String Content = "";
	    String searchtext = "";
	   
	    protected void onPreExecute() {
	        // NOTE: You can call UI Element here.
	         
	        //Start Progress Dialog (Message)
	       
	         
	        try{
	            // Set Request parameter
	                    //searchtext = searchview.getQuery().toString();
	            data +="?" + URLEncoder.encode("groupid", "UTF-8") + "=" + groupid + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId + "&" + "receiver=" + memberid;
	                 
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	         
	    }        
	    protected JSONArray doInBackground(String... urls) {
	        JSONArray result = null;
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(urls[0]+data);
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
	                result = new JSONArray(response);
	                    } else {
	                            
	                    }
	        } catch (Exception e) {
	           
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	        }
	        return result;
	    }

	    protected void onPostExecute(JSONArray result) {
	    	try{
		        //JSONArray reviews = result;
			       
		        //Log.d("ONPOSTEXECUTE", "starting post execute");
		        for(int i = 0; i < result.length(); i++)
		        {
		        	JSONObject review = result.getJSONObject(i);
		        	String reviewString = review.getString("content");
		        	Log.d("Review String " + i + " ", reviewString);
		        	reviewList.add(reviewString);
		        }
	    	}
	    	catch(Exception e){
	    		
	    	}
	        adapter = new ArrayAdapter<String>(getActivity(),R.layout.review_view, R.id.gsearchtitle, reviewList);
	        setListAdapter(adapter);
	    }
	}	

}



