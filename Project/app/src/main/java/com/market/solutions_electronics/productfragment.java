package com.market.solutions_electronics;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class productfragment extends Fragment {

    ArrayList<productinfo> list;
    FirebaseFirestore db;
    ArrayList<String> des2 = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> pric = new ArrayList<>();
    ArrayList<String> url2 = new ArrayList<>();
    public productfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productfragment, container, false);
        //return inflater.inflate(R.layout.fragment_productfragment, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView userList=view.findViewById(R.id.productlist);
        userList.setLayoutManager(new GridLayoutManager(c, 2));
        String strtext = getArguments().getString("edttext");
        db = FirebaseFirestore.getInstance();
        db.collection(strtext)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("123", document.getId() + " => " + document.getData());
                                //productinfo data1=document.getData();
                                String des=document.getString("Description");
                                des2.add(des);
                                String Nam=document.getString("Name");
                                name.add(Nam);
                                String pri=document.getString("Price");
                                pric.add(pri);
                                String url=document.getString("URL");
                                url2.add(url);
                                String tt=des+","+Nam+","+pri+","+url;
                                //list.add(tt);

                                //list.add(document.getData());
                                userList.setAdapter(new productadapter(c,des2,name,pric,url2));


                            }

                        } else {
                            Log.d("789", "Error getting documents: ", task.getException());
                        }
                    }
                });


        userList.setAdapter(new productadapter(c,des2,name,pric,url2));


        return view;
    }





}
