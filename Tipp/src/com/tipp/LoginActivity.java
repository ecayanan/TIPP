package com.tipp;

//import android.app.DownloadManager.Request;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.tipp.group.OriginActivity;


public class LoginActivity extends FragmentActivity {
	private static final int SPLASH = 0;
	private static final int SELECTION = 1;
	private static final int FRAGMENT_COUNT = SELECTION +1;

	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private static final String TAG = "LoginActivity";
	private String user_ID;
	private String profileName;
	private boolean isResumed = false;
	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.tipp", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    } 
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_login);

	    FragmentManager fm = getSupportFragmentManager();
	    fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
	    fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);

	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();
	}
	
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (int i = 0; i < fragments.length; i++) {
	        if (i == fragmentIndex) {
	            transaction.show(fragments[i]);
	        } else {
	            transaction.hide(fragments[i]);
	        }
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    transaction.commit();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	            // If the session state is open:
	            // Show the authenticated fragment
	            
	        	//showFragment(SELECTION, false);
	        	
	        	 //Go to origin activity if state is opened
	        	killActivity();
	        	Intent intent = new Intent(getApplicationContext(), OriginActivity.class);
	    		startActivity(intent); 
	        	
	        	//showFragment(SELECTION, false);
	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(SPLASH, false);
	        	
	        	/* Go to origin activity if state is closed
	        	killActivity();
	        	Intent intent = new Intent(getApplicationContext(), OriginActivity.class);
	    		startActivity(intent); */
	        }
	    }
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    final Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	        //showFragment(SELECTION, false);
	        
	        /* Go straight to origin activity */
	        	Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	                // If the response is successful
	                if (session == Session.getActiveSession()) {
	                    if (user != null) {
	                        user_ID =  user.getId();//user id
	                        profileName = user.getName();//user's profile name
	                        //userNameView.setText(user.getName());
	                        Log.d("user_ID",user_ID);
	                        Log.d("profileName", profileName);
	            	   	 	new AddUser().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/addUser.php"});

	                    }   
	                }   
	            }   
	        }); 
	        Request.executeBatchAsync(request);	  
	        killActivity();
	    	//intent = new Intent(getApplicationContext(), OriginActivity.class);
	    	//intent.putExtra("SESSION_ID", user_ID);
			//startActivity(intent);
			
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
	    	
	        showFragment(SPLASH, false);
        	
        	
        	/* Go straight to origin activity
        	killActivity();
        	Intent intent = new Intent(getApplicationContext(), OriginActivity.class);
    		startActivity(intent);*/
	    }
	}
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	void killActivity()
	{
	    finish();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}*/
	

    private class AddUser extends AsyncTask<String,Integer,String> {
    String data = "";
    String Content = "";
    String searchtext = "";
   
    protected void onPreExecute() {
        // NOTE: You can call UI Element here.
         
        //Start Progress Dialog (Message)
       
         
        try{
            // Set Request parameter
                    //searchtext = searchview.getQuery().toString();
            data +="?" + URLEncoder.encode("userid", "UTF-8")+ "=" + user_ID + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(profileName,"UTF-8") ;  
            Log.d("DATA",data);
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
	    	intent = new Intent(getApplicationContext(), OriginActivity.class);
	    	intent.putExtra("SESSION_ID", user_ID);
	    	startActivity(intent);
	    }
	}	
}
