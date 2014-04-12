package com.terralink.android.mvideo.mockup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.terralink.android.mvideo.mockup.R;
import com.terralink.android.mvideo.mockup.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> orders;
    private Map<String, List<Product>> ordersCollection;

    public ProductsExpandableListAdapter(Context context, List<String> orders, HashMap<String, List<Product>> ordersCollection) {
        this.context = context;
        this.orders = orders;
        this.ordersCollection = ordersCollection;
    }

    @Override
    public int getGroupCount() {
        return this.orders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.ordersCollection.get(this.orders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.orders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.ordersCollection.get(this.orders.get(groupPosition)).get(childPosition);
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
            convertView = inflater.inflate(R.layout.list_row_group_order_string, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Product product = (Product) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_details_order_product, null);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        txtName.setText(product.getName());

        TextView txtPrice = (TextView) convertView.findViewById(R.id.price);
        txtPrice.setText(String.valueOf(product.getPrice()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
