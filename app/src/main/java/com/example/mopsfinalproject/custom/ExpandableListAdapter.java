package com.example.mopsfinalproject.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.mopsfinalproject.R;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private String[] values;

    public ExpandableListAdapter(Context context,List<String> listDataHeader,HashMap<String,List<String>> listHashMap, String[] values)
    {
        this.context=context;
        this.listDataHeader=listDataHeader;
        this.listHashMap=listHashMap;
        this.values = values;
    }
    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String header=(String)getGroup(groupPosition);
        if(convertView==null)
        {
//it gives power to embed list_group view into our xml file.
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.profile_group,null);
        }
//fetch the parent value and set the value to Textview
        TextView lbiListHeader= (TextView)convertView.findViewById(R.id.groupHeader);
        lbiListHeader.setTypeface(null, Typeface.BOLD);
        lbiListHeader.setText(header);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childtext=(String)getChild(groupPosition,childPosition);
        final String childval = this.values[childPosition];

        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.profile_list_profile,null);
        }
        TextView txtlistchild= (TextView)convertView.findViewById(R.id.listLabel);
        TextView txtvalue = (TextView)convertView.findViewById(R.id.listValue);

        txtlistchild.setText(childtext);
        txtvalue.setText(childval);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
