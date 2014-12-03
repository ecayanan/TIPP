package com.tipp.user.adt;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.tipp.R;
import com.facebook.Session; 
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;


public class UserProfileFragment extends ListFragment {
	ArrayAdapter<String> adapter;
    ArrayList <String> groupNames;
    ArrayList <Integer> groupIds;
	private int grp;
	private String currentUserId = "";
	private String userName = "";
	private Bitmap bitmap;
	
	//FACEBOOK STUFF
	private static final String TAG = "UserProfileFragment";
	private UiLifecycleHelper uiHelper;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
		currentUserId = getArguments().getString("user_ID");
		userName = getArguments().getString("user_Name");
		TextView nameTxt = (TextView) view.findViewById(R.id.textView1);
		nameTxt.setText(userName);
		//Log.d("USERNAME", userName);
		new GetProfilePicture().execute(new String[] {"https://graph.facebook.com/" + currentUserId + "/picture?type=large"});
		
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		
		View shareButton = view.findViewById(R.id.button_share);
        
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (FacebookDialog.canPresentShareDialog(getActivity(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                            .setName("Check out Tipp!")
                            .setLink("www.tipp.com") //addlink to YOUTUBE VIDEO
                            .setDescription("This app is changing my life!")
                            .setPicture("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-9/15458_1402187463405576_1052654193964303576_n.jpg?oh=ec7c9a4d9e41c7f77ac8e90a204eddd9&oe=551883DB&__gda__=1427161689_6fa2e07236bad814fe8bad4e5f3cf13a")
                            //.setPicture("http://lh3.googleusercontent.com/-P4JBVTv_kSI/AAAAAAAAAAI/AAAAAAAAAAs/bZptjIhkWu4/s265-c-k-no/photo.jpg")
                            .build();
                    uiHelper.trackPendingDialogCall(shareDialog.present());
 
                } 
//                else {
//                    Log.d(TAG_LOG, "Success!");
//                }
            }
        });
		
		return view;
	}
    public void onActivityCreated(Bundle savedInstanceState) {
    	 
        //get bundle and get all groups array
        currentUserId = getArguments().getString("user_ID");
        //Log.d("groupFRagment userid = ", currentUserId);
  	  	groupIds = getArguments().getIntegerArrayList("groupIds");
        groupNames = getArguments().getStringArrayList("groupMemberStringArray");
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.group_view, R.id.gsearchtitle, groupNames);
        setListAdapter(adapter);
        // Facebook Log out & Share
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
	}

    private class GetProfilePicture extends AsyncTask<String,Integer,Bitmap> { 
    	URL imageURL;
    	protected void onPreExecute() {}	
    	protected Bitmap doInBackground(String... urls) {
    	    String urldisplay = urls[0];
    	    Log.d("SHOW CORRECT URL", urldisplay);
    	        Bitmap bitmap = null;
    	    try {
    	      URL url = new URL(urldisplay);
    	      bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    	      HttpURLConnection.setFollowRedirects(true);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	    return bitmap;

    	}

    	protected void onPostExecute(Bitmap result) {
			try {
		    	bitmap = result;
		  		Log.d("BITMAP", bitmap + "");
		  		ImageView userImg = (ImageView) getView().findViewById(R.id.imageView1);
		  		userImg.setImageBitmap(bitmap);
	  		} catch (Exception e) {
	  			Log.d("EXCEPTION", e.getMessage());
		    } 
        }
    }
    
    // FACEBOOK STUFF
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        
        //facebook share start
        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
        //facebook share end
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    

    // FACEBOOK STUFF END
}
