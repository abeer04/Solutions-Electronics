package com.market.solutions_electronics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 */
public class signup2 extends Fragment implements View.OnClickListener, OnCompleteListener<DocumentSnapshot> {

    EditText email,password,conpassword,enter_address;
    Button register;
    ImageButton address;
    Context context;
    Session session;
    LinearLayout formlayout;
    String edit="0";
    ProgressBar progressBar;



    FirebaseFirestore db;
    int i=0;


    public void setData()
    {

    }
    public signup2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_signup2, container, false);
        context=view.getContext();
        db = FirebaseFirestore.getInstance();
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        conpassword=view.findViewById(R.id.confirmpassword);
        address=view.findViewById(R.id.map_address);
        enter_address=view.findViewById(R.id.signup_address);
        progressBar=view.findViewById(R.id.progress);

        address.setOnClickListener(this);
        register=view.findViewById(R.id.register);
        register.setOnClickListener(this);
        formlayout=view.findViewById(R.id.form);
        String str=getArguments().getString("edit");

        if(str.equals("1"))
        {
            edit="1";
            session =new Session(getActivity());
            register.setText("Update Profile");
            progressBar.setVisibility(View.VISIBLE);

            db.collection("User").document(session.getemail())
                    .get()
                    .addOnCompleteListener(this);

        }


        return view;
    }

    @Override
    public void onClick(View view) {


            if (view.getId() == R.id.register) {
        if(!TextUtils.isEmpty(email.getText())||!TextUtils.isEmpty(password.getText()) ||!TextUtils.isEmpty(conpassword.getText()) ||!TextUtils.isEmpty(enter_address.getText()) ) {
                final String emailget = email.getText().toString();
                i = 0;


                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Log.d("123", document.getId() + " => " + document.getData());

                                        String id = document.getId();

                                        if (id.equals(emailget)) {
                                            i++;
                                            if(edit.equals("1"))
                                            {
                                                i--;
                                            }

                                        }

                                    }
                                    if (i > 0) {

                                        Toast.makeText(getActivity(), "Email already used",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        final String getpass = password.getText().toString();
                                        final String getpass2 = conpassword.getText().toString();
                                        final String gete = email.getText().toString();
                                        final String getaddress = enter_address.getText().toString();
                                        if (getpass.equals(getpass2)) {
                                            Map<String, Object> userdata = new HashMap<>();
                                            userdata.put("password", getpass);
                                            userdata.put("email", gete);
                                            userdata.put("address", getaddress);


                                            db.collection("User").document(gete)
                                                    .set(userdata, SetOptions.merge())
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
                                            if(edit.equals("1"))
                                            {
                                                Toast.makeText(getActivity(), "Profile Updated",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(), "Registration completed",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {


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

            else
            {
                Toast.makeText(getActivity(), "All fields are required.",
                        Toast.LENGTH_LONG).show();
            }


        } else if (view == address) {

            Intent intent = new Intent(context, maps.class);
            intent.putExtra("address", enter_address.getText().toString());
            startActivityForResult(intent, 001);

        }
    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if( requestCode == 001 ) {
                if( resultCode == RESULT_OK) {
                    if( data != null ) {
                        String map_address = data.getStringExtra("address");
                        if(!map_address.equals("-1"))
                        {
                            enter_address.setText(map_address);
                        }

                    }
                }
            }

    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            progressBar.setVisibility(View.INVISIBLE);
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                try{
                    email.setText(session.getemail());
                    email.setEnabled(false);
                    enter_address.setText((document.getData()).get("address").toString());
                    password.setText((document.getData()).get("password").toString());
                    conpassword.setText((document.getData()).get("password").toString());
                }
                catch (Exception e)
                {
                    email.setText(session.getemail());
                    enter_address.setText("");
                    password.setText("");
                    conpassword.setText("");
                }
            }
        } else {
            enter_address.setText("error");
        }
    }
}


