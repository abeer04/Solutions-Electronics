package com.market.solutions_electronics;

import android.content.Intent;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView product;
    ImageView myorders;
    ImageView contact;
    ImageView wallet;

    FirebaseFirestore db;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        product=findViewById(R.id.products);
        myorders=findViewById(R.id.myorder);
        contact=findViewById(R.id.contact);
        wallet=findViewById(R.id.wallet);

        product.setOnClickListener(this);
        myorders.setOnClickListener(this);
        contact.setOnClickListener(this);
        wallet.setOnClickListener(this);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6-1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);





    }

    @Override
    public void onClick(View view) {

        if(view == product) {

            Intent intent = new Intent(MainActivity.this, products.class);
            startActivity(intent);
        }

        else if(view == myorders) {

            Intent intent = new Intent(MainActivity.this, myorder.class);
            startActivity(intent);
        }

        else if(view == contact) {

            Intent intent = new Intent(MainActivity.this, contactus.class);
            startActivity(intent);
        }
        else if(view == wallet) {

            Intent intent = new Intent(MainActivity.this, wallet.class);
            startActivity(intent);
        }


    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.mainmenu, menu);


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
            Intent intent = new Intent(MainActivity.this, cart.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


