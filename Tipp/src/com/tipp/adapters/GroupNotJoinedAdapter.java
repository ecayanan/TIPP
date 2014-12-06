package com.tipp.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.group.adt.GroupJoined;
import com.tipp.group.adt.GroupNotJoined;

public class GroupNotJoinedAdapter extends ArrayAdapter<GroupNotJoined> {
  private final Context context;
  private final ArrayList<GroupNotJoined> values;

  public GroupNotJoinedAdapter(Context context, ArrayList<GroupNotJoined> values) {
    super(context, R.layout.light_blue, values);
    this.context = context;
    this.values = values;
  }
  
  @Override
  public GroupNotJoined getItem(int position)
  {
	GroupNotJoined gnj = values.get(position);
	return gnj;
	  
  }
  
  @Override
  public void remove(GroupNotJoined gnj)
  {
	  values.remove(gnj);
	  notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.light_blue, parent, false);
    TextView nameText = (TextView) rowView.findViewById(R.id.gsearchtitle);
    
    GroupNotJoined group = values.get(position);
    nameText.setText(group.getName());
    //Log.d("BEFORE GETGROUPRATING","BEFORE GROUPRATING");

    //Log.d("AFTER GETGROUPRATING", "AFTER GROUPRATING");
    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    //textView.setText(values[position]);
    // change the icon for Windows and iPhone
    //String s = values[position];
    //if (s.startsWith("iPhone")) {
      //imageView.setImageResource(R.drawable.no);
    //} else {
      //imageView.setImageResource(R.drawable.ok);
    //}

    return rowView;
  }
  
} 