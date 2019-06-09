package com.market.solutions_electronics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class checkout extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<DocumentSnapshot> {
TextView noitems,totalamount,date;
RadioButton cash,wallet;
EditText ordername,ordernumber,orderadd;
ImageView ordernow;
ImageButton map_address;

FirebaseFirestore db;
int tamout;
int titems;
    int gp;
    String key;
    String name,price,qty,url2;
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
        map_address = findViewById(R.id.map_address_checkout);
        map_address.setOnClickListener(this);
        ordernow=findViewById(R.id.ordernow);
        ordernow.setOnClickListener(this);
        session = new Session(checkout.this);
        String doc =session.getemail();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        db.collection("User").document(doc)
                .get(Source.SERVER)
                .addOnCompleteListener(this);
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

            name = intent.getStringExtra("name");
             price= intent.getStringExtra("price");
             qty= intent.getStringExtra("qty");
             url2 = intent.getStringExtra("url");

             gp=Integer.parseInt(price);
            int qty22 =Integer.parseInt(qty);
            gp=gp*qty22;
            noitems.setText(qty);
            totalamount.setText(String.valueOf(gp));
            date.setText("3 days");



        }










    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    @Override
    public void onClick(View view) {


        session = new Session(checkout.this);
        final String doc = session.getemail();

        if (view == ordernow) {
            if(!TextUtils.isEmpty(orderadd.getText())||!TextUtils.isEmpty(ordernumber.getText())||!TextUtils.isEmpty(ordername.getText())){
            final Date currentTime = Calendar.getInstance().getTime();
            if (key.equals("multi")) {
                if (wallet.isChecked()) {
                    db.collection("User")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("999", document.getId() + " => " + document.getData());
                                            //productinfo data1=document.getData();


                                            if (document.getId().equals(doc)) {
                                                Log.d("741", String.valueOf(gp));
                                                // String walletn=document.getString("wallet");

                                                int wl = parseInt((document.getData()).get("wallet").toString());
                                                if (wl > tamout) {
                                                    db.collection("User").document(doc).collection("MyCart")
                                                            .get()
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


                                                                            Log.d("777", doc);

                                                                            db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString()).collection("products").document()
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


                                                                        }

                                                                        Map<String, Object> userinfo = new HashMap<>();
                                                                        userinfo.put("CustomerName", ordername.getText().toString());
                                                                        userinfo.put("Mobile", ordernumber.getText().toString());
                                                                        userinfo.put("Address", orderadd.getText().toString());
                                                                        userinfo.put("Status", "InProgress");
                                                                        userinfo.put("payment", "Wallet");
                                                                        userinfo.put("Orderno", getRandomIntegerBetweenRange(10000000,99999999));
                                                                        userinfo.put("Total", totalamount.getText().toString());

                                                                        db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString())
                                                                                .set(userinfo)
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
                                                                        Toast.makeText(checkout.this, "Order has been placed",
                                                                                Toast.LENGTH_LONG).show();

                                                                    } else {
                                                                        Log.d("789", "Error getting documents: ", task.getException());
                                                                    }
                                                                }
                                                            });

                                                    int cc = wl - tamout;


                                                    Map<String, Object> user_wallet = new HashMap<>();
                                                    user_wallet.put("wallet", String.valueOf(cc));

                                                    db.collection("User").document(session.getemail())
                                                            .set(user_wallet, SetOptions.merge())
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // show_amount.setText("error");
                                                                }
                                                            });

                                                    db.collection("User").document(doc).collection("MyCart")
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                            Log.d("999", document.getId() + " => " + document.getData());
                                                                            //productinfo data1=document.getData();
                                                                            String docid = document.getId();

                                                                            db.collection("User").document(doc).collection("MyCart").document(docid)
                                                                                    .delete()
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            // Log.w(TAG, "Error deleting document", e);
                                                                                        }
                                                                                    });

                                                                            //list.add(tt);

                                                                            //list.add(document.getData());


                                                                        }

                                                                    } else {
                                                                        Log.d("789", "Error getting documents: ", task.getException());
                                                                    }
                                                                }
                                                            });
                                                    destroy();


                                                } else {
                                                    Toast.makeText(checkout.this, "Not Enough money in wallet",
                                                            Toast.LENGTH_LONG).show();
                                                }

                                            }

                                            //list.add(tt);

                                            //list.add(document.getData());


                                        }

                                    } else {
                                        Log.d("789", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                } else {

                    db.collection("User").document(doc).collection("MyCart")
                            .get()
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


                                            Log.d("777", doc);

                                            db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString()).collection("products").document()
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


                                            //list.add(tt);

                                            //list.add(document.getData());


                                        }

                                        Map<String, Object> userinfo = new HashMap<>();
                                        userinfo.put("CustomerName", ordername.getText().toString());
                                        userinfo.put("Mobile", ordernumber.getText().toString());
                                        userinfo.put("Address", orderadd.getText().toString());
                                        userinfo.put("Status", "InProgress");
                                        userinfo.put("payment", "Cash");

                                        userinfo.put("Orderno", getRandomIntegerBetweenRange(10000000,99999999));
                                        userinfo.put("Total", totalamount.getText().toString());

                                        db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString())
                                                .set(userinfo)
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
                                        Toast.makeText(checkout.this, "Order has been placed",
                                                Toast.LENGTH_LONG).show();

                                    } else {
                                        Log.d("789", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                    db.collection("User").document(doc).collection("MyCart")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("999", document.getId() + " => " + document.getData());
                                            //productinfo data1=document.getData();
                                            String docid = document.getId();

                                            db.collection("User").document(doc).collection("MyCart").document(docid)
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Log.w(TAG, "Error deleting document", e);
                                                        }
                                                    });

                                            //list.add(tt);

                                            //list.add(document.getData());
                                            destroy();


                                        }

                                    } else {
                                        Log.d("789", "Error getting documents: ", task.getException());
                                    }
                                }
                            });


                }
            } else {
                if (wallet.isChecked()) {
                    db.collection("User")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("999", document.getId() + " => " + document.getData());
                                            //productinfo data1=document.getData();


                                            if (document.getId().equals(doc)) {
                                                // String walletn=document.getString("wallet");
                                                int wl = parseInt((document.getData()).get("wallet").toString());
                                                if (wl > gp) {
                                                    Map<String, Object> userdata = new HashMap<>();
                                                    userdata.put("Name", name);
                                                    userdata.put("Price", price);
                                                    userdata.put("Qty", qty);
                                                    userdata.put("URL", url2);

                                                    db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString()).collection("products").document()
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

                                                    Map<String, Object> userinfo = new HashMap<>();
                                                    userinfo.put("CustomerName", ordername.getText().toString());
                                                    userinfo.put("Mobile", ordernumber.getText().toString());
                                                    userinfo.put("Address", orderadd.getText().toString());

                                                    db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString())
                                                            .set(userinfo)
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
                                                    Toast.makeText(checkout.this, "Order has been placed",
                                                            Toast.LENGTH_LONG).show();

                                                    int cc = wl - gp;


                                                    Map<String, Object> user_wallet = new HashMap<>();
                                                    user_wallet.put("wallet", String.valueOf(cc));

                                                    db.collection("User").document(session.getemail())
                                                            .set(user_wallet, SetOptions.merge())
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // show_amount.setText("error");
                                                                }
                                                            });

                                                    destroy();


                                                } else {
                                                    Toast.makeText(checkout.this, "Not Enough money in wallet",
                                                            Toast.LENGTH_LONG).show();
                                                }

                                            }

                                            //list.add(tt);

                                            //list.add(document.getData());


                                        }

                                    } else {
                                        Log.d("789", "Error getting documents: ", task.getException());
                                    }
                                }
                            });


                } else {

                    Map<String, Object> userdata = new HashMap<>();
                    userdata.put("Name", name);
                    userdata.put("Price", price);
                    userdata.put("Qty", qty);
                    userdata.put("URL", url2);

                    db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString()).collection("products").document()
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

                    Map<String, Object> userinfo = new HashMap<>();
                    userinfo.put("CustomerName", ordername.getText().toString());
                    userinfo.put("Mobile", ordernumber.getText().toString());
                    userinfo.put("Address", orderadd.getText().toString());

                    db.collection("User").document(doc).collection("MyOrder").document(currentTime.toString())
                            .set(userinfo)
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
                    Toast.makeText(checkout.this, "Order has been placed",
                            Toast.LENGTH_LONG).show();
                    destroy();


                }


            }


        }else {
                Toast.makeText(this, "All fields are required.",
                        Toast.LENGTH_LONG).show();
            }
    }
        else if(view.getId()==R.id.map_address_checkout)
        {
            Intent intent = new Intent(this, maps.class);
            startActivityForResult(intent, 006);
        }


    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == 006 ) {
            if( resultCode == RESULT_OK) {
                if( data != null ) {
                    String map_address = data.getStringExtra("address");
                    if(!map_address.equals("-1"))
                    {
                        orderadd.setText(map_address);
                    }

                }
            }
        }

    }

    private void destroy()
    {
        Intent intent = new Intent();
        intent.putExtra("done_order","1");
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("done_order","0");
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                try{

                    orderadd.setText (document.getData().get("address").toString());

                }
                catch (Exception e)
                {
                    orderadd.setText("");
                }
            } else {

            }
        } else {
            orderadd.setText("error");
        }

    }
}
