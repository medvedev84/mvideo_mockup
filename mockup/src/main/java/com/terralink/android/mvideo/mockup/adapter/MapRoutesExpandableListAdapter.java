package com.terralink.android.mvideo.mockup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.terralink.android.mvideo.mockup.R;
import com.terralink.android.mvideo.mockup.model.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRoutesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> routes;
    private Map<String, List<Route>> routesCollection;

    public MapRoutesExpandableListAdapter(Context context, List<String> routes, HashMap<String, List<Route>> routesCollection) {
        this.context = context;
        this.routes = routes;
        this.routesCollection = routesCollection;
    }

    @Override
    public int getGroupCount() {
        return this.routes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.routesCollection.get(this.routes.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.routes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.routesCollection.get(this.routes.get(groupPosition)).get(childPosition);
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_group_string, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Route route = (Route) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_details_route, null);
        }

        TextView txtRoute = (TextView) convertView.findViewById(R.id.route_label);
        txtRoute.setText(route.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
