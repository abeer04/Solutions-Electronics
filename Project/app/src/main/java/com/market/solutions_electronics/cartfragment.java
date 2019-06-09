package com.market.solutions_electronics;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class cartfragment extends Fragment implements View.OnClickListener {
    ArrayList<String> qty2 = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> pric = new ArrayList<>();
    ArrayList<String> url2 = new ArrayList<>();
    ArrayList<String> productid = new ArrayList<>();
    productdetail productdetail=new productdetail();


    FirebaseFirestore db;
    cartrecycle adapter_forChange;
    Button checkout;
    private Session session;
    ProgressBar progressBar;
    public cartfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_cartfragment, container, false);
        checkout=view.findViewById(R.id.checkout);
        checkout.setOnClickListener(this);
        final FragmentActivity c = getActivity();
        progressBar=view.findViewById(R.id.progres);

        /*
        String name22 = getArguments().getString("name2");
        String qty33 = getArguments().getString("qty");
        String price22 = getArguments().getString("price");
        String url44 = getArguments().getString("url");
        */

        final RecyclerView userList=view.findViewById(R.id.cartrecycle22);
        userList.setLayoutManager(new LinearLayoutManager(c));

        final cartrecycle adapter= new cartrecycle(c,qty2,name,pric,url2,productid);
        adapter_forChange=adapter;
        db = FirebaseFirestore.getInstance();
/*
        name=productdetail.getname();
        qty2=productdetail.getQty();
        pric=productdetail.getPric();
        url2=productdetail.getUrl();*/
        session = new Session(getActivity());
        String doc =session.getemail();
        progressBar.setVisibility(View.VISIBLE);
        db.collection("User").document(doc).collection("MyCart")
                .get(Source.SERVER)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("999", document.getId() + " => " + document.getData());
                                //productinfo data1=document.getData();
                                String name22=document.getString("Name");
                                name.add(name22);
                                String pri=document.getString("Price");
                                pric.add(pri);
                                String qty=document.getString("Qty");
                                qty2.add(qty);
                                String url=document.getString("Url");
                                url2.add(url);
                                productid.add(document.getId());
                                userList.setAdapter(adapter);
                                //list.add(tt);

                                //list.add(document.getData());



                            }
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Log.d("789", "Error getting documents: ", task.getException());
                        }
                    }
                });



        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.checkout) {
            Intent intent = new Intent(getActivity(), checkout.class);
            intent.putExtra("key","multi");
            startActivityForResult(intent,005);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 005 ) {
            if( resultCode == RESULT_OK) {
                if( data != null ) {
                    String last = data.getStringExtra("done_order");
                    if(last.equals("1"))
                    {
                        getActivity().onBackPressed();
                    }



                }
            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
