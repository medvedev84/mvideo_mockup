package com.terralink.android.mvideo.mockup.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoutesDrawer implements RoutingListener {

    private static final String TAG = "RoutesDrawer";
    public static final float SEGMENT = 30.0f;

    private GoogleMap map;

    private String[] strAddressArray;
    private List<Address> addresses;
    private Context context;

    public RoutesDrawer(Context context, GoogleMap map, String[] addressArray){
        this.context = context;
        this.map = map;
        this.strAddressArray = addressArray;
    }

    public void draw(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            Toast.makeText(context, "Google Play services was not available for some reason", Toast.LENGTH_SHORT).show();
            return;
        }

        addresses = getAddresses(strAddressArray);
        drawRoutes(addresses);
    }

    @Override
    public void onRoutingFailure() {
        // The Routing request failed
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions) {
        PolylineOptions polyoptions = new PolylineOptions();
        polyoptions.color(Color.BLUE);
        polyoptions.width(5);
        polyoptions.addAll(mPolyOptions.getPoints());
        map.addPolyline(polyoptions);
    }

    private void drawRoutes(final List<Address> points){
        final ProgressDialog progressRoutes = new ProgressDialog(context);
        progressRoutes.setMessage("Drawing routes...");
        progressRoutes.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressRoutes.setCancelable(false);
        progressRoutes.show();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < points.size(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    Address address = addresses.get(i - 1);
                    LatLng point1 = new LatLng(address.getLatitude(), address.getLongitude());
                    address = addresses.get(i);
                    LatLng point2 = new LatLng(address.getLatitude(), address.getLongitude());

                    drawRoute(point1, point2);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                progressRoutes.hide();
                for (int i=0; i< addresses.size(); i++) {
                    Address address = addresses.get(i);
                    LatLng point = new LatLng(address.getLatitude(), address.getLongitude());
                    MarkerOptions options = new MarkerOptions();
                    options.position(point);
                    options.title(address.getAddressLine(0));
                    options.icon(BitmapDescriptorFactory.defaultMarker(i * SEGMENT));
                    map.addMarker(options);

                }
                Address address = addresses.get(addresses.size() - 1);
                LatLng point = new LatLng(address.getLatitude(), address.getLongitude());
                moveAndZoomCamera(point, 12);
            }
        }.execute();
    }

    private void drawRoute(LatLng start, LatLng end){
        Routing routing = new Routing(Routing.TravelMode.DRIVING);
        routing.registerListener(this);
        routing.execute(start, end);
    }

    private List<Address> getAddresses(final String[] addresses){
        final List<Address> points = new ArrayList<Address>();
        final ProgressDialog progressLatLng = new ProgressDialog(context);
        progressLatLng.setMessage("Getting coordinates from internet...");
        progressLatLng.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressLatLng.setCancelable(false);
        progressLatLng.show();

        new AsyncTask<Void, Void,  List<Address>>() {
            @Override
            protected List<Address> doInBackground(Void... params) {
                for (String strAddress: addresses){
                    Address point = getAddress(strAddress);
                    if (point != null) {
                        points.add(point);
                    }
                }
                return points;
            }

            @Override
            protected void onPostExecute(final List<Address> points) {
                progressLatLng.hide();
            }
        }.execute();

        return points;
    }

    private Address getAddress(String strAddress){
        Address point = null;
        try {
            List<Address> results = new Geocoder(context).getFromLocationName(strAddress, 1);
            if (results.size() > 0) {
                point = results.get(0);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can not get coordinates for " + strAddress);
        }
        return point;
    }

    private void moveAndZoomCamera(LatLng point, int zoomValue){
        CameraUpdate camera = CameraUpdateFactory.newLatLng(point);
        map.moveCamera(camera);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomValue);
        map.animateCamera(zoom);
    }
}
