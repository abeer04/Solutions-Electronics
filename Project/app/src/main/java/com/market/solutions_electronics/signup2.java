package com.market.solutions_electronics;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class signup2 extends Fragment implements View.OnClickListener {

    EditText email,password,conpassword;
    Button register,address;
    Context context;
    LinearLayout formlayout;
    boolean mLocationPermissionGranted=false;


    FirebaseFirestore db;
    int i=0;
    public signup2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_signup2, container, false);
        context=view.getContext();
        db = FirebaseFirestore.getInstance();
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        conpassword=view.findViewById(R.id.confirmpassword);
        address=view.findViewById(R.id.map_address);
        address.setOnClickListener(this);
        register=view.findViewById(R.id.register);
        register.setOnClickListener(this);
        formlayout=view.findViewById(R.id.form);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.register) {
            final String emailget = email.getText().toString();
            i = 0;


            db.collection("User")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.d("123", document.getId() + " => " + document.getData());

                                    String id = document.getId();

                                    if (id.equals(emailget)) {
                                        i++;

                                    }


                                }
                                if (i > 0) {

                                    Toast.makeText(getActivity(), "Email already used",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    final String getpass = password.getText().toString();
                                    final String getpass2 = conpassword.getText().toString();
                                    final String gete = email.getText().toString();
                                    if (getpass.equals(getpass2)) {
                                        Map<String, Object> userdata = new HashMap<>();
                                        userdata.put("password", getpass);
                                        userdata.put("email", gete);


                                        db.collection("User").document(gete)
                                                .set(userdata)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //Log.w(TAG, "Error writing document", e);
                                                    }
                                                });
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    } else {


                                        Toast.makeText(getActivity(), "Password do not match",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            } else {
                                Log.d("789", "Error getting documents: ", task.getException());
                            }
                        }
                    });


        } else if (view.getId() == R.id.map_address) {
            if(mLocationPermissionGranted){
            Intent intent = new Intent(context, maps.class);
            intent.putExtra("address", address.getText().toString());
            startActivityForResult(intent, 001);}
            else
            {
                getLocationPermission();
            }
        }

    }



    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, 002);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;


        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    003);
        }
    }

    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available,004 );
            dialog.show();
        }else{
            Toast.makeText(context, "Update/Install Google Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 003: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(checkMapServices())
        {
            if(mLocationPermissionGranted)
            {

            }
            else
            {
                getLocationPermission();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if( requestCode == 001 ) {
                if( resultCode == RESULT_OK) {
                    if( data != null ) {
                        String map_address = data.getStringExtra("address");
                        if(map_address.equals("0"))
                        {

                        }
                        else{
                            address.setText(map_address);
                        }
                    }
                }
            }
            else if(requestCode==002)
            {
                if(mLocationPermissionGranted){


                }
                else {
                    getLocationPermission();
                }
            }

        }

    }

