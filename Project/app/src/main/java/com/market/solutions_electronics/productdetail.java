package com.market.solutions_electronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class productdetail extends AppCompatActivity implements View.OnClickListener {
    ImageView productimage;
    TextView name,price,des;
    EditText quantity;
    Button incre,decre,bcart,buy;
    String gdes,gname,gprice,gurl;
    Fragment fragment;
    FirebaseFirestore db;
    private Session session;

    ArrayList<String> qty22 = new ArrayList<>();
    ArrayList<String> name22 = new ArrayList<>();
    ArrayList<String> pric22 = new ArrayList<>();
    ArrayList<String> url22 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
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
            db = FirebaseFirestore.getInstance();

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
            price.setText("Price"+gprice+"Rs");

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
            Intent intent = new Intent(productdetail.this, checkout.class);
            intent.putExtra("key","single");
            intent.putExtra("name",gname);
            intent.putExtra("price",gprice);
            intent.putExtra("qty",quantity.getText().toString());


            startActivity(intent);

        }
        if(view.getId() == R.id.add_to_cart) {

            session = new Session(productdetail.this);
            //Bundle bundle = new Bundle();
            cartrecycle adapter;
            name22.add(name.getText().toString());
            pric22.add(gprice);
            url22.add(gurl);
            qty22.add(quantity.getText().toString());
            //adapter=new cartrecycle(this,qty22,name22,pric22,url22);
            Map<String, Object> userdata = new HashMap<>();
            userdata.put("Name", name.getText().toString());
            userdata.put("Qty", quantity.getText().toString());
            int gp=Integer.parseInt(gprice);
            int qty =Integer.parseInt(quantity.getText().toString());
            gp=gp*qty;
            String gprice2=String.valueOf(gp);
            userdata.put("Price", gprice2);
            userdata.put("Url", gurl);
            String doc =session.getemail();
            Log.d("777",doc);

            db.collection("User").document(doc).collection("MyCart").document()
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

            Toast.makeText(productdetail.this, "Added to Cart",
                    Toast.LENGTH_LONG).show();





















           // adapter=new cartrecycle(this,qty22,name22,pric22,url22);


            //int pp;
            /*
            bundle.putString("name2", name.getText().toString());
            bundle.putString("qty", quantity.getText().toString());
            bundle.putString("price", price.getText().toString());
            bundle.putString("url", gurl);
            fragment = new cartfragment();
            fragment.setArguments(bundle);
            */
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


    public ArrayList<String> getname() {
        return name22;
    }
    public ArrayList<String> getQty() {
        return qty22;
    }
    public ArrayList<String> getPric() {
        return pric22;
    }
    public ArrayList<String> getUrl() {
        return url22;
    }
}
