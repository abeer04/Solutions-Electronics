package com.market.solutions_electronics;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class productadapter extends RecyclerView.Adapter<productadapter.ViewHolder> {



    List<String> des;
    List<String> nam;
    List<String> price;
    List<String> url;
    ArrayList<String> qty22 = new ArrayList<>();
    ArrayList<String> name22 = new ArrayList<>();
    ArrayList<String> pric22 = new ArrayList<>();
    ArrayList<String> url22 = new ArrayList<>();
    private Session session;
    private FirebaseFirestore db;
    private LayoutInflater mInflater;
    private Context context2;

    public productadapter(Context context,List<String> data,List<String> data2,List<String> data3,List<String> data4){
        this.mInflater = LayoutInflater.from(context);
        this.context2=context;
            this.des=data;
            this.nam=data2;
            this.price=data3;
            this.url=data4;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.productlistview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        //return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.productlistview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final String desc = des.get(position);
        final String names = nam.get(position);
        final String pric=price.get(position);
        final String uri=url.get(position);

        viewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new Session(context2);
                //Bundle bundle = new Bundle();
                cartrecycle adapter;
                db = FirebaseFirestore.getInstance();
                name22.add(names);
                pric22.add(pric);
                url22.add(uri);
                qty22.add(viewHolder.qty.getText().toString());
                //adapter=new cartrecycle(this,qty22,name22,pric22,url22);
                Map<String, Object> userdata = new HashMap<>();
                userdata.put("Name", viewHolder.name.getText().toString());
                userdata.put("Qty", viewHolder.qty.getText().toString());
                int gp=Integer.parseInt(pric);
                int qty =Integer.parseInt(viewHolder.qty.getText().toString());
                gp=gp*qty;
                String gprice2=String.valueOf(gp);
                userdata.put("Price", gprice2);
                userdata.put("Url", uri);
                String doc =session.getemail();

                db.collection("User").document(doc).collection("MyCart").document()
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

                Toast.makeText(context2, "Added to Cart",
                        Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.name.setText(names);
        viewHolder.price.setText("Price:"+pric+"Rs");
        Glide.with(viewHolder.pimage.getContext()).load(uri).
                into(viewHolder.pimage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context2, productdetail.class);
               intent.putExtra("des", desc);
                intent.putExtra("name", names);
                intent.putExtra("price", pric);
                intent.putExtra("url", uri);
                context2.startActivity(intent);
//                context.startActivity(new Intent(context, productdetail.class));
            }
        });

        /*
            viewHolder.name.setText(productinfos.get(position).getName());
            viewHolder.price.setText(productinfos.get(position).getPrice());
        Glide.with(viewHolder.pimage.getContext()).load(productinfos.get(position).getURL()).
                into(viewHolder.pimage);
                */

    }

    @Override
    public int getItemCount() {
        return nam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price,name;
        ImageView pimage;
        EditText qty;
        Button addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price =itemView.findViewById(R.id.productprice);
            name=itemView.findViewById(R.id.productname);
            pimage=itemView.findViewById(R.id.productimage);
            qty=itemView.findViewById(R.id.quantity);
            addToCart=itemView.findViewById(R.id.add_cart);
            qty.setText("1");


        }


    }
}
