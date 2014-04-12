package com.terralink.android.mvideo.mockup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.terralink.android.mvideo.mockup.model.Order;
import com.terralink.android.mvideo.mockup.model.OrderStatus;
import com.terralink.android.mvideo.mockup.model.Route;
import com.terralink.android.mvideo.mockup.adapter.RoutesExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoutesListFragment extends Fragment {

    private List<Route> routes;
    private HashMap<Route, List<Order>> routesCollection;
    private int selectedStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route_list, container, false);
        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.route_list);
        // preparing list data
        prepareData();
        expListView.setAdapter(
                new RoutesExpandableListAdapter(
                        getActivity(),
                        routes,
                        routesCollection,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showSelectStatusDialog();
                            }
                        }
                )
        );


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ((MainDrawerActivity) getActivity()).showAddressCard(childPosition);
                return false;
            }
        });

        expListView.expandGroup(0);

        return rootView;
    }

    private void prepareData() {
        routes = new ArrayList<Route>();
        routesCollection = new HashMap<Route, List<Order>>();

        // Adding routes
        routes.add(new Route("Route №00001"));
        routes.add(new Route("Route №00002"));
        routes.add(new Route("Route №00003"));

        // Adding orders
        List<Order> orders1 = new ArrayList<Order>();
        orders1.add(new Order("16, Leo Tolstoy St., Moscow 119021, Russia", OrderStatus.Cancelled));
        orders1.add(new Order("38, Lenina St., Moscow 119021, Russia", OrderStatus.Cancelled));
        orders1.add(new Order("76, Main St., Moscow 119021, Russia", OrderStatus.Cancelled));

        List<Order> orders2 = new ArrayList<Order>();
        orders2.add(new Order("12, Kutuzovsky prospekt, Moscow 121248, Russia", OrderStatus.Cancelled));
        orders2.add(new Order("43, Main St., Moscow 121248, Russia", OrderStatus.Cancelled));
        orders2.add(new Order("98, River St., Moscow 121248, Russia", OrderStatus.Cancelled));

        List<Order> orders3 = new ArrayList<Order>();
        orders3.add(new Order("2, Radialnaya 9 St., Moscow 115404, Russia", OrderStatus.Cancelled));
        orders3.add(new Order("7, Mira St., Moscow 115404, Russia", OrderStatus.Cancelled));
        orders3.add(new Order("11, Pushkinskaya St., Moscow 115404, Russia", OrderStatus.Cancelled));

        routesCollection.put(routes.get(0), orders1);
        routesCollection.put(routes.get(1), orders3);
        routesCollection.put(routes.get(2), orders3);
    }

    private void showSelectStatusDialog(){
        final String[] mStatuses =  getResources().getStringArray(R.array.dialog_select_status_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_select_status_title);
        builder.setSingleChoiceItems(mStatuses, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedStatus = which;
            }
        });
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedStatus == 3)
                    showSelectDelayDialog();
                else if (selectedStatus == 4)
                    showAddCommentDialog();
                else
                    showConfirmationDialog();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_confirm_title);
        builder.setMessage(R.string.dialog_confirm_message);
        builder.setPositiveButton(R.string.dialog_yes, null);
        builder.setNegativeButton(R.string.dialog_no, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showSelectDelayDialog(){
        final String[] mStatuses =  getResources().getStringArray(R.array.dialog_select_delay_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_select_delay_title);
        builder.setSingleChoiceItems(mStatuses, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // some logic here
            }
        });
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showConfirmationDialog();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAddCommentDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_reject_title);
        builder.setTitle(R.string.dialog_reject_body);

        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showConfirmationDialog();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
