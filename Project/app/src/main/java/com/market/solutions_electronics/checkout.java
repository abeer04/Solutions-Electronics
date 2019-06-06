package com.market.solutions_electronics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class checkout extends AppCompatActivity implements View.OnClickListener {
TextView noitems,totalamount,date;
RadioButton cash,wallet;
EditText ordername,ordernumber,orderadd;
ImageView ordernow;
FirebaseFirestore db;
int tamout;
int titems;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        db = FirebaseFirestore.getInstance();
        noitems=findViewById(R.id.no_of_items);
        totalamount=findViewById(R.id.total_amount);
        date=findViewById(R.id.delivery_date);
        cash=findViewById(R.id.cash);
        cash.setChecked(true);
        wallet=findViewById(R.id.wallet);
        ordername=findViewById(R.id.ordername);
        ordernumber=findViewById(R.id.ordernumber);
        orderadd=findViewById(R.id.orderaddress);

        ordernow=findViewById(R.id.ordernow);
        ordernow.setOnClickListener(this);
        session = new Session(checkout.this);
        String doc =session.getemail();
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if(key.equals("multi")){

            db.collection("User").document(doc).collection("MyCart")
                    .get(Source.SERVER)
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    //productinfo data1=document.getData();
                                    titems++;
                                    String gprice=document.getString("Price");
                                    int gp=Integer.parseInt(document.getString("Price"));
                                    tamout=tamout+gp;
                                    // Log.d("847", String.valueOf(titems));
                                    noitems.setText(String.valueOf(titems));
                                    totalamount.setText(String.valueOf(tamout));

                                    if(titems<10){
                                        date.setText("3 days");
                                    }
                                    else{
                                        date.setText("7 days");
                                    }
                                }

                            } else {
                                Log.d("789", "Error getting documents: ", task.getException());
                            }
                        }
                    });


        }
        else{

            String name = intent.getStringExtra("name");
            String price= intent.getStringExtra("price");
            String qty= intent.getStringExtra("qty");

            int gp=Integer.parseInt(price);
            int qty22 =Integer.parseInt(qty);
            gp=gp*qty22;
            noitems.setText(qty);
            totalamount.setText(String.valueOf(gp));

                date.setText("3 days");



        }










    }

    @Override
    public void onClick(View view) {

        if(view == ordernow) {
            session = new Session(checkout.this);
            String doc =session.getemail();

            db.collection("User").document(doc).collection("MyCart")
                    .get(Source.SERVER)
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("999", document.getId() + " => " + document.getData());
                                    //productinfo data1=document.getData();


                                    Map<String, Object> userdata = new HashMap<>();
                                    userdata.put("Name", document.getString("Name"));
                                    userdata.put("Price", document.getString("Price"));
                                    userdata.put("Qty", document.getString("Qty"));
                                    userdata.put("URL", document.getString("Url"));

                                    String doc =session.getemail();
                                    Log.d("777",doc);

                                    db.collection("User").document(doc).collection("MyOrder").document()
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


                                    Toast.makeText(checkout.this, "Done",
                                            Toast.LENGTH_LONG).show();





                                    //list.add(tt);

                                    //list.add(document.getData());



                                }

                            } else {
                                Log.d("789", "Error getting documents: ", task.getException());
                            }
                        }
                    });






        }

    }
}
