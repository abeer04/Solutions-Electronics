package com.market.solutions_electronics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    int bool=1;
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
        ImageView profile_pic;
        TextView mail;
        Session session=new Session(this);
        mail=findViewById(R.id.show_email);


        profile_pic=findViewById(R.id.profile_pic);
        getMenuInflater().inflate(R.menu.productmenu, menu);
        Glide
                .with(this)   // pass Context
                .load("https://cdn2.iconfinder.com/data/icons/circle-avatars-1/128/050_girl_avatar_profile_woman_suit_student_officer-512.png")    // pass the image url
                .override(300, 300)
                .centerCrop() // optional scaletype
                .crossFade() //optional - to enable image crossfading
                .into(profile_pic);
        mail.setText(session.getemail());

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
            Intent intent = new Intent(products.this, cart.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.grid)

        {
            final RecyclerView userList=findViewById(R.id.productlist);

            if(bool==0) {
                userList.setLayoutManager(new GridLayoutManager(this, 2));
                bool=1;
            }
            else{
                userList.setLayoutManager(new LinearLayoutManager(this));
                bool=0;
            }

           // userList.setLayoutManager(new GridLayoutManager(products.this, 2));

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences sp;
        SharedPreferences.Editor se;
        sp= getSharedPreferences("flag",MODE_PRIVATE);
        se=sp.edit();
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.smartwatch) {
            Bundle bundle = new Bundle();
            bundle.putString("edttext", "Smart-Watch");
            fragment = new productfragment();
            fragment.setArguments(bundle);

            // Handle the camera action
        } else if (id == R.id.speaker) {


            Bundle bundle = new Bundle();
            bundle.putString("edttext", "speaker");
            fragment = new productfragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.headset) {


            Bundle bundle = new Bundle();
            bundle.putString("edttext", "headset");
            fragment = new productfragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.camera) {


            Bundle bundle = new Bundle();
            bundle.putString("edttext", "camera");
            fragment = new productfragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.hdmi) {


            Bundle bundle = new Bundle();
            bundle.putString("edttext", "Hdmi");
            fragment = new productfragment();
            fragment.setArguments(bundle);
        }
        else if(id==R.id.logout)
        {
            se.putString("login","0");
            se.apply();
            Intent intent = new Intent(this, bottom.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
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
