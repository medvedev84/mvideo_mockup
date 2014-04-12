package com.terralink.android.mvideo.mockup;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.terralink.android.mvideo.mockup.map.RoutesDrawer;
import com.terralink.android.mvideo.mockup.model.Route;
import com.terralink.android.mvideo.mockup.adapter.MapRoutesExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoutesMapFragment
        extends Fragment
        implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {


    /*
     * Define a request code to send to Google Play services
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    protected MapView mapView;
    protected GoogleMap map;
    private LocationClient mLocationClient;

    private List<String> routes;
    private HashMap<String, List<Route>> routesCollection;

    private ProgressDialog progressInitMap;

    private View lastColored;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        progressInitMap = new ProgressDialog(getActivity());
        progressInitMap.setMessage("Initializing map...");
        progressInitMap.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressInitMap.setCancelable(false);
        progressInitMap.show();

        map = mapView.getMap();

        mLocationClient = new LocationClient(getActivity(), this, this);
        configureMap(map);

        LinearLayout routesControl = (LinearLayout) rootView.findViewById(R.id.routesControl);
        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.map_route_list);
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (lastColored != null)
                    lastColored.setBackgroundColor(Color.WHITE);
                lastColored = null;
            }
        });
        expListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String[] addresses = new String[] {
                                "12/3, Kutuzovsky prospekt, Moscow, Russia",
                                "2, Radialnaya 9 St., Moscow, Russia",
                                "9, Kolomenskaya St., Moscow, Russia",
                                "15Б, Хачатуряна ул., Москва, Россия",
                                "15, Ленинградское ш., Москва, Россия",
                                "1Г, Чертановская ул., Москва, Россия",
                                "7, Планерная ул., Москва, Россия"
                        };
                        RoutesDrawer drawer = new RoutesDrawer(getActivity(), map, addresses);
                        drawer.draw();

                        if (lastColored != null)
                            lastColored.setBackgroundColor(Color.WHITE);

                        v.setBackgroundColor(Color.MAGENTA);
                        lastColored = v;

                        return false;
                    }
                }
        );
        // preparing list data
        prepareData();
        expListView.setAdapter(
                new MapRoutesExpandableListAdapter(
                        getActivity(),
                        routes,
                        routesCollection
                )
        );
        Button showRoutesBtn = (Button) rootView.findViewById(R.id.showRoutesBtn);
        showRoutesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                if (lastColored != null)
                    lastColored.setBackgroundColor(Color.WHITE);
                lastColored = null;
            }
        });

        routesControl.bringToFront();

        return rootView;
    }

    private void prepareData() {
        routes = new ArrayList<String>();
        routesCollection = new HashMap<String, List<Route>>();

        // Adding routes
        routes.add("Routes");

        // Adding orders
        List<Route> orders1 = new ArrayList<Route>();
        orders1.add(new Route("Route #16119021"));
        orders1.add(new Route("Route #38119021"));
        orders1.add(new Route("Route #76119021"));

        routesCollection.put(routes.get(0), orders1);
    }


    private void configureMap(GoogleMap map)
    {
        if (map == null)
            return; // Google Maps not available
        try {
            MapsInitializer.initialize(getActivity());
        }
        catch (GooglePlayServicesNotAvailableException e) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
    }

    private void moveAndZoomCamera(LatLng point, int zoomValue){
        CameraUpdate camera = CameraUpdateFactory.newLatLng(point);
        map.moveCamera(camera);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomValue);
        map.animateCamera(zoom);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }

    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
        Location location = mLocationClient.getLastLocation();
        LatLng initPoint = new LatLng(location.getLatitude(), location.getLongitude());
        moveAndZoomCamera(initPoint, 5);
        progressInitMap.dismiss();
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(getActivity(), "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (progressInitMap != null)
            progressInitMap.dismiss();
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the user with the error.
             */
            int errorCode = connectionResult.getErrorCode();

            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                errorDialog.show();
            }
        }
    }
}