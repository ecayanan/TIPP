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
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
     
import com.tipp.R;
import com.tipp.group.adt.GroupManager;
     
public class OriginActivity extends ActionBarActivity{
 
        private OnSearchFragment searchFragment;
        private GroupManager groupManager;
        
        @Override
          public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mainmenu, menu);
            MenuItem menuItem = menu.findItem(R.id.action_search);
            SearchView sv = (SearchView) menuItem.getActionView();
           
            menuItem.setOnActionExpandListener(new OnActionExpandListener(){
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                searchFragment = new OnSearchFragment();
                                searchFragment.setArguments(groupManager.getGroupNotJoinedBundle());
                                fragmentTransaction.replace(R.id.origin_container, searchFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                return true;
                        }
                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                                new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php?userid=1"});           
                                return true;
                        }
               
            });
           
            sv.setOnQueryTextListener(new OnQueryTextListener(){
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                                //if(searchFragment != null)
                                        searchFragment.searchFilterText(query);
                                return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                        	//if(searchFragment != null){
                                searchFragment.searchFilterText(newText);
                              //  return true;
                        	//}
                            return false;
                        }
            });
            // Configure the search info and add any event listeners
            return super.onCreateOptionsMenu(menu);
        }
       
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
       
        public void startGroupFragment(Bundle bundle){
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                GroupFragment fragment = new GroupFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.origin_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
        }
 
    }
       
}

