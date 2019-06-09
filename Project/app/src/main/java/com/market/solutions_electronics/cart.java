package com.market.solutions_electronics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class cart extends AppCompatActivity {
Fragment fragment;
    FirebaseFirestore db;
    private Session session;
    cartrecycle adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fragment = new cartfragment();

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentTransaction ft=fragmentManager.beginTransaction();
            fragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragment).commit();


        }
        db = FirebaseFirestore.getInstance();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.cartmenu, menu);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deletedata) {
            session = new Session(cart.this);
            final String doc =session.getemail();
            db.collection("User").document(doc).collection("MyCart")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("999", document.getId() + " => " + document.getData());
                                    //productinfo data1=document.getData();
                                    String docid=document.getId();

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


            fragment = new emptyfragment();




            /*
            ArrayList<String> qty2 = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> pric = new ArrayList<>();
            ArrayList<String> url2 = new ArrayList<>();



            adapter=new cartrecycle(cart.this,qty2,name,pric,url2);
            adapter.nam.clear();
            adapter.price.clear();
            adapter.url.clear();
            adapter.qty.clear();
            adapter.notifyDataSetChanged();
            */

            if(fragment!=null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                //FragmentTransaction ft=fragmentManager.beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragment).commit();


            }


        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
