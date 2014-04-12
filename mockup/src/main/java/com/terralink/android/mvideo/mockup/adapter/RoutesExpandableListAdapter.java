package com.terralink.android.mvideo.mockup.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.terralink.android.mvideo.mockup.R;
import com.terralink.android.mvideo.mockup.model.Order;
import com.terralink.android.mvideo.mockup.model.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Route> routes;
    private Map<Route, List<Order>> routesCollection;
    private View.OnClickListener listener = null;

    public RoutesExpandableListAdapter(Context context, List<Route> routes, HashMap<Route, List<Order>> routesCollection, View.OnClickListener listener) {
        this.context = context;
        this.routes = routes;
        this.routesCollection = routesCollection;
        this.listener = listener;
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
        Route route = (Route) getGroup(groupPosition);
        String headerTitle = route.getName();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_group_route, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.routeName);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Order order = (Order) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_details_address, null);
        }

        TextView txtAddress = (TextView) convertView.findViewById(R.id.address);
        txtAddress.setText(order.getAddress());

        TextView txtStatus = (TextView) convertView.findViewById(R.id.status);
        txtStatus.setText("PENDING");
        txtStatus.setOnClickListener(listener);

        TextView txtNumber = (TextView) convertView.findViewById(R.id.number);
        txtNumber.setText("№123456");

        TextView txtDeadline = (TextView) convertView.findViewById(R.id.deadline);
        txtDeadline.setText("until 12:30");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
