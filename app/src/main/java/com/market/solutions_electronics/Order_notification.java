package com.market.solutions_electronics;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigDecimal;

import javax.annotation.Nullable;
public class Order_notification extends JobService {

    private boolean jobCancelled;
    FirebaseFirestore db;
    Session session ;

    @Override
    public void onCreate() {
        super.onCreate();
        jobCancelled = false;
        db = FirebaseFirestore.getInstance();
        session = new Session(this);

    }



    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    if (jobCancelled) {
                        return;
                    }

                    final CollectionReference docRef = db.collection("User").document(session.getemail()).collection("MyOrder");
                    docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }

                            if ((queryDocumentSnapshots != null)) {
                                try{
                                DocumentSnapshot document=((queryDocumentSnapshots.getDocuments()).get(0));

                                Toast.makeText(Order_notification.this,(new BigDecimal(document.get("Orderno").toString()).toBigInteger())+" "+"is Delivered" ,
                                        Toast.LENGTH_LONG).show();}

                                catch (Exception ee)
                                {

                                }
                            }
                        }
                    });




//                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

}