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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginfrag extends Fragment implements View.OnClickListener {
    EditText email,password;
    Button login;
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
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);

        login=view.findViewById(R.id.login);
        login.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            final String emailget=email.getText().toString();
            final String getpass=password.getText().toString();
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
                                    String pass=document.getString("password");
                                    if (id.equals(emailget) && pass.equals(getpass)){
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    else{
                                        i++;


                                    }

                                }
                                if(i>0){
                                    Toast.makeText(getActivity(), "InValid Username or Password",
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
}
