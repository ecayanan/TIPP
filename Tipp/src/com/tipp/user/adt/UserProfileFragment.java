package com.tipp.user.adt;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

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

import com.tipp.R;


public class UserProfileFragment extends ListFragment {
	ArrayAdapter<String> adapter;
    ArrayList <String> groupNames;
    ArrayList <Integer> groupIds;
	private int grp;
	private String currentUserId = "";
	private String userName = "";
	private Bitmap bitmap;

	
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
      }	

    private class GetProfilePicture extends AsyncTask<String,Integer,Bitmap> { 
    	URL imageURL;
    	protected void onPreExecute() {

	    } 	
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

}
