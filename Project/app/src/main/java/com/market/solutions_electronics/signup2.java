package com.market.solutions_electronics;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class signup2 extends Fragment implements View.OnClickListener {

    EditText email,password,conpassword;
    Button register;

    FirebaseFirestore db;
    int i=0;
    public signup2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_signup2, container, false);
        db = FirebaseFirestore.getInstance();
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        conpassword=view.findViewById(R.id.confirmpassword);

        register=view.findViewById(R.id.register);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.register) {
            final String emailget=email.getText().toString();
            i=0;


            db.collection("User")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.d("123", document.getId() + " => " + document.getData());

                                    String id=document.getId();

                                    if (id.equals(emailget)){
                                        i++;

                                    }


                                }
                                if(i>0){

                                    Toast.makeText(getActivity(), "Email already used",
                                            Toast.LENGTH_LONG).show();
                                }
                                else{
                                    final String getpass=password.getText().toString();
                                    final String getpass2=conpassword.getText().toString();
                                    final String gete=email.getText().toString();
                                    if(getpass.equals(getpass2)){
                                        Map<String, Object> userdata = new HashMap<>();
                                        userdata.put("password", getpass);
                                        userdata.put("email", gete);


                                        db.collection("User").document(gete)
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
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{


                                        Toast.makeText(getActivity(), "Password do not match",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            } else {
                                Log.d("789", "Error getting documents: ", task.getException());
                            }
                        }
                    });


        }

    }
}
