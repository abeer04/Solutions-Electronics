package com.market.solutions_electronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class productdetail extends AppCompatActivity implements View.OnClickListener {
    ImageView productimage;
    TextView name,price,des,bcart,buy;
    EditText quantity;
    Button incre,decre;
    String gdes,gname,gprice,gurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            incre=findViewById(R.id.increment);
            decre=findViewById(R.id.decrement);
            quantity=findViewById(R.id.quantity);
            productimage=findViewById(R.id.productimage);
            name=findViewById(R.id.productname);
            price=findViewById(R.id.productprice);
            des=findViewById(R.id.productdesc);
            bcart=findViewById(R.id.add_to_cart);
            buy =findViewById(R.id.buy_now);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            gdes = b.getString("des");
            gname =b.getString("name");
            gprice=b.getString("price");
            gurl=b.getString("url");

            name.setText(gname);
            des.setText(gdes);
            price.setText(gprice);

            Glide.with(productimage.getContext()).load(gurl).
                    into(productimage);


        }

            bcart.setOnClickListener(this);
            buy.setOnClickListener(this);
            incre.setOnClickListener(this);
            decre.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buy_now) {


        }
        if(view.getId() == R.id.add_to_cart) {

        }
        if(view.getId() == R.id.increment) {

            int c=Integer.parseInt(quantity.getText().toString());
            c++;
            if(c<20)
            quantity.setText(String.valueOf(c));



        }
        if(view.getId() == R.id.decrement) {

            int c=Integer.parseInt(quantity.getText().toString());
            c--;
            if(c>0)
                quantity.setText(String.valueOf(c));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.products, menu);
        getMenuInflater().inflate(R.menu.products, menu);
        return true;
    }
}
