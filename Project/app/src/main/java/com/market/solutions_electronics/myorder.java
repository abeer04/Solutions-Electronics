package com.market.solutions_electronics;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class myorder extends AppCompatActivity implements OnCompleteListener<QuerySnapshot>, orderRvadapter.ItemClickListener {

    private Session session;
    FirebaseFirestore db;
    ProgressBar progressBar;
    ArrayList<Order> porders;
    orderRvadapter adapter;
    ArrayList<Order> corders;
    RecyclerView rv;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.inProcess:
                    rv(porders);
                    return true;
                case R.id.complete:
                    rv(corders);

                    return true;

            }

            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        getSupportActionBar().setTitle("My Orders");
        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.prog);
        porders=new ArrayList<>();
        corders=new ArrayList<>();

        BottomNavigationView navView = findViewById(R.id.orders_nav);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        session=new Session(this);
        rv=findViewById(R.id.orders_recy);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("User").document(session.getemail()).collection("MyOrder")
                .get()
                .addOnCompleteListener(this);




    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        progressBar.setVisibility(View.INVISIBLE);
        for (QueryDocumentSnapshot document : task.getResult()) {
            Order order=new Order();
            order.order_time=document.getId();
            order.status=document.getString("Status");
            order.contact=document.getString("Mobile");
            order.address=document.getString("Address");
            order.customer_name=document.getString("CustomerName");
            order.totalamount=document.getString("Total");
//            order.orderno=document.getString("Orderno");
            order.payment_type=document.getString("payment");
            if(order.status.equals("InProgress"))
            {porders.add(order);}
            else {
                corders.add(order);
            }

        }

        rv(porders);

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void rv(ArrayList<Order> orders)
    {
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new orderRvadapter(myorder.this, orders);
        adapter.setClickListener(myorder.this);
        rv.setAdapter(adapter);

    }
}
