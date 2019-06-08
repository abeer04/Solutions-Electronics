package com.market.solutions_electronics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class profile extends AppCompatActivity implements View.OnClickListener {
    signup2 profile_frag = new signup2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FragmentTransaction t=getSupportFragmentManager().beginTransaction();

        t.add(R.id.profile_container,profile_frag);
        t.commit();
//
//
        profile_frag.register.setText("Update Profile");
        profile_frag.register.setOnClickListener(this);
//
//
//
//
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.profile_container, profile_frag);
//        ft.commit();

    }

    @Override
    public void onClick(View v) {
        profile_frag.register.setText("DOne!!");
    }
}
