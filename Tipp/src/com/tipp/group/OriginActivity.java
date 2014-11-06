	

    package com.tipp.group;
     
    import java.io.BufferedReader;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.io.StringWriter;
    import java.util.ArrayList;
    import java.util.List;
     
    import org.apache.http.HttpResponse;
    import org.apache.http.StatusLine;
    import org.apache.http.client.methods.HttpGet;
    import org.apache.http.impl.client.DefaultHttpClient;
    import org.json.JSONArray;
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
     
    public class OriginActivity extends ActionBarActivity{
     
            private Bundle sBundle = new Bundle();
            private OnSearchFragment searchFragment;
           
            @Override
              public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.mainmenu, menu);
                MenuItem menuItem = menu.findItem(R.id.action_search);
                SearchView sv = (SearchView) menuItem.getActionView();
               
                // Idea:
                // Listener onTextChanged --> fragment.filterText
                menuItem.setOnActionExpandListener(new OnActionExpandListener(){
                            @Override
                            public boolean onMenuItemActionExpand(MenuItem item) {
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    searchFragment = new OnSearchFragment();
                                    searchFragment.setArguments(sBundle);
                                    searchFragment.filterText("input goes here");
                                    fragmentTransaction.replace(R.id.origin_container, searchFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    return true;
                            }
                            @Override
                            public boolean onMenuItemActionCollapse(MenuItem item) {
                                    //fragmentTransaction.remove(searchFragment);
                                    new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php"});           
                                    return true;
                            }
                   
                });
               
                sv.setOnQueryTextListener(new OnQueryTextListener(){
     
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                    //if(searchFragment != null)
                                            searchFragment.filterText(query);
                                    return false;
                            }
     
                            @Override
                            public boolean onQueryTextChange(String newText) {
                                    // TODO Auto-generated method stub
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
            new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php?id="});               
     
            }
           
            private class DownloadJSONTask extends AsyncTask<String,Integer,JSONObject> {
            List <String> groupList = new ArrayList<String>();
            List <String> groupMemberList = new ArrayList<String>();
           
            protected JSONObject doInBackground(String... urls) {
                JSONObject result = null;
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urls[0]); // Don't do this
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
     
                } // Don't do this
                return result;
            }
     
            protected void onPostExecute(JSONObject result) {
                    //test.setText("onpostexecute");
                try {
                    JSONArray groupJSON = result.getJSONArray("groups");
                    JSONArray groupMembersJSON = result.getJSONArray("group_members");
                   
                    //Log.d("ONPOSTEXECUTE", "starting post execute");
                    for(int i = 0; i < groupJSON.length(); i++)
                    {
                            JSONObject childGroupJSON = groupJSON.getJSONObject(i);
                            groupList.add(childGroupJSON.getString("name"));                       
                    }
                    //Log.d("GROUPS_LIST", tempArray);
                    for(int i = 0; i < groupMembersJSON.length(); i++)
                    {
                            JSONObject childGroupJSON = groupJSON.getJSONObject(i);
                            groupMemberList.add(childGroupJSON.getString("name"));                         
                    }
                    sBundle.putInt("groupSize", groupJSON.length());
                    sBundle.putStringArrayList("groupStringArray", (ArrayList<String>) groupList);
                    Bundle gBundle = new Bundle();
                    gBundle.putInt("groupMemberSize", groupMembersJSON.length());
                    gBundle.putStringArrayList("groupMemberStringArray", (ArrayList<String>) groupList);
                    startGroupFragment(gBundle);
                } catch (Exception e) {
                    Log.d("EXCEPTION", e.getMessage());
                } // Again don't do this
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

