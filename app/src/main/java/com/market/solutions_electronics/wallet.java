package com.market.solutions_electronics;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class wallet extends AppCompatActivity implements OnCompleteListener<DocumentSnapshot>, View.OnClickListener {


    private FirebaseFirestore db;
    private Session session;
    TextView show_amount;
    int wallet=0;
    Button add;
    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        db = FirebaseFirestore.getInstance();
        session = new Session(this);
        add=findViewById(R.id.add_amount);
        add.setOnClickListener(this);
        show_amount=findViewById(R.id.show_amount);
        db.collection("User").document(session.getemail())
                .get()
                .addOnCompleteListener(this);

    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                try{

                    wallet=parseInt((document.getData()).get("wallet").toString());
                    show_amount.setText("Rs. "+wallet);

                }
                catch (Exception e)
                {
                    show_amount.setText("Rs. 0");
                }
            } else {

            }
        } else {
            show_amount.setText("error");
        }

    }

    @Override
    public void onClick(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_amount, null);
        builder.setView(dialogView);

        builder.setTitle("Add amount");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                amount=dialogView.findViewById(R.id.amount);
                wallet=wallet+parseInt(amount.getText().toString());
                show_amount.setText("Rs. "+wallet);
                Map<String, Object> user_wallet = new HashMap<>();
                user_wallet.put("wallet", wallet);

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
                                show_amount.setText("error");
                            }
                        });

            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        }

    }

