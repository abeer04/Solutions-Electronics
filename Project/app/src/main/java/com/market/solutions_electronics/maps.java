package com.market.solutions_electronics;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class maps extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, View.OnClickListener {


    private GoogleMap mMap;
    TextView address;
    private RequestQueue requestQueue;
    JsonObjectRequest addressRes;
    String formatted_address;
    Button done;
    LatLng cLocation;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.home_map);
        mapFragment.getMapAsync(this);
        address = findViewById(R.id.show_address);
        done = findViewById(R.id.done);
        done.setOnClickListener(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cLocation));
        mMap.setMinZoomPreference(10);
        url="https://maps.googleapis.com/maps/api/geocode/json?latlng="+cLocation.latitude+","+cLocation.longitude+"&key=AIzaSyBAyEhcJM_a1riCY88giw-C5DhJRJiokmY";
        Request();
        mMap.setOnCameraIdleListener(this);

    }




    @Override
    public void onCameraIdle() {
        final LatLng mPosition;
        mPosition = mMap.getCameraPosition().target;
        url="https://maps.googleapis.com/maps/api/geocode/json?latlng="+mPosition.latitude+","+mPosition.longitude+"&key=AIzaSyBAyEhcJM_a1riCY88giw-C5DhJRJiokmY";
        Request();


    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.putExtra("address",formatted_address);
        setResult(RESULT_OK, intent);
        this.finish();

    }



    @Override
    public void onBackPressed() {


        Intent intent = new Intent();
        intent.putExtra("address","0");
        setResult(RESULT_OK, intent);
        this.finish();

        super.onBackPressed();
    }


    void Request(){
        requestQueue= Volley.newRequestQueue(this);
        addressRes= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Object responseA;
                    responseA=response.getJSONArray("results").get(0);
                    formatted_address=((JSONObject) responseA).getString("formatted_address");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                address.setText(formatted_address);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Request();
                return;
            }
        });
        requestQueue.add(addressRes);
    }
}
