package com.market.solutions_electronics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class products extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    FirebaseFirestore db;
    ArrayList<String> des2 = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> pric = new ArrayList<>();
    ArrayList<String> url2 = new ArrayList<>();
    productfragment productfragment = new productfragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "Smart-Watch");
        fragment = new productfragment();
        fragment.setArguments(bundle);
        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentTransaction ft=fragmentManager.beginTransaction();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.products, menu);
        getMenuInflater().inflate(R.menu.productmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.smartwatch) {
            Bundle bundle = new Bundle();
            bundle.putString("edttext", "Smart-Watch");
            fragment = new productfragment();
            fragment.setArguments(bundle);

            // Handle the camera action
        } else if (id == R.id.hdmi) {


            Bundle bundle = new Bundle();
            bundle.putString("edttext", "Hdmi");
            fragment = new productfragment();
            fragment.setArguments(bundle);
        }
        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentTransaction ft=fragmentManager.beginTransaction();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
