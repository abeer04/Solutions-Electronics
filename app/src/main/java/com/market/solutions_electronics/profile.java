package com.market.solutions_electronics;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class profile extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signup2 profile_frag = new signup2();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = new Bundle();
        bundle.putString("edit", "1");
        profile_frag.setArguments(bundle);
        FragmentTransaction t=getSupportFragmentManager().beginTransaction();

        t.replace(R.id.profile_container,profile_frag).commit();

    }

}
