package com.market.solutions_electronics;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class bottom extends AppCompatActivity {
    private TextView mTextMessage;
    Fragment fragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.login:
                    fragment = new loginfrag();
                    if(fragment!=null){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        //FragmentTransaction ft=fragmentManager.beginTransaction();
                        fragmentManager.beginTransaction().replace(R.id.flContent2, fragment).commit();


                    }
                   // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.register:
                    fragment = new signup2();
                    if(fragment!=null){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        //FragmentTransaction ft=fragmentManager.beginTransaction();
                        fragmentManager.beginTransaction().replace(R.id.flContent2, fragment).commit();


                    }
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;

            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment = new loginfrag();

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentTransaction ft=fragmentManager.beginTransaction();
            fragmentManager.beginTransaction().replace(R.id.flContent2, fragment).commit();


        }

    }

}
