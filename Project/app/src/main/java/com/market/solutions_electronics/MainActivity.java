package com.market.solutions_electronics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get=findViewById(R.id.get);
        get.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        
    }
}
