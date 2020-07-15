package com.market.solutions_electronics;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginfrag extends Fragment implements View.OnClickListener {
    EditText email,password;
    Button login;
    SharedPreferences sp;
    SharedPreferences.Editor se;
    int f=1;
    private Session session;//global variable
int i=0;
    FirebaseFirestore db;

    public loginfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_loginfrag, container, false);
        db = FirebaseFirestore.getInstance();
        sp= this.getActivity().getSharedPreferences("flag",MODE_PRIVATE);
        se=sp.edit();

        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);

        login=view.findViewById(R.id.login);
        login.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        if(!TextUtils.isEmpty(email.getText())||!TextUtils.isEmpty(password.getText()) ) {
            if (view.getId() == R.id.login) {
                final String emailget = email.getText().toString();
                final String getpass = password.getText().toString();
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
                                        String pass = document.getString("password");
                                        if (id.equals(emailget) && pass.equals(getpass)) {
                                            se.putString("login","1");
                                            se.commit();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                            f=0;

                                            getActivity().finish();

                                            break;
                                        } else {
                                            i++;
                                        }

                                    }
                                    if (f==1) {
                                        Toast.makeText(getActivity(), "Invalid Username or Password",
                                                Toast.LENGTH_LONG).show();


                                    }

                                    session = new Session(getActivity()); //in oncreate
                                    //and now we set sharedpreference then use this like

                                    session.setemail(email.getText().toString());
                                } else {
                                    Log.d("789", "Error getting documents: ", task.getException());
                                }
                            }
                        });


            }

        }
        else
        {
            Toast.makeText(getActivity(), "All fields are required.",
                    Toast.LENGTH_LONG).show();
        }

    }
}
