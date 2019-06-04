package com.market.solutions_electronics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class cartrecycle extends RecyclerView.Adapter<cartrecycle.ViewHolder> {

    List<String> qty;
    List<String> nam;
    List<String> price;
    List<String> url;
    List<String> productid;
    private LayoutInflater mInflater;
    private Context context2;
    FirebaseFirestore db;
    private Session session;

    public cartrecycle(Context context, List<String> data, List<String> data2, List<String> data3, List<String> data4,List<String> data5) {
        this.mInflater = LayoutInflater.from(context);
        this.context2=context;
        this.qty=data;
        this.nam=data2;
        this.price=data3;
        this.url=data4;
        this.productid=data5;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.cartviewlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String qty2 = qty.get(position);
        final String names = nam.get(position);
        final String pric=price.get(position);
        final String uri=url.get(position);
        final String proid=productid.get(position);

        viewHolder.name.setText(names);
        viewHolder.qty.setText("Qty:"+qty2);
        viewHolder.price.setText("Price:"+pric+"Rs");
        Glide.with(viewHolder.pimage.getContext()).load(uri).
                into(viewHolder.pimage);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.deletecard) {
                    //Toast.makeText(context2, names,Toast.LENGTH_LONG).show();
                    qty.remove(position);
                    nam.remove(position);
                    price.remove(position);
                    url.remove(position);
                    notifyItemRemoved(position);
                    session = new Session(context2);
                    final String doc =session.getemail();
                    db.collection("User").document(doc).collection("MyCart").document(proid)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Log.w(TAG, "Error deleting document", e);
                                }
                            });


                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return nam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price,name,qty;
        ImageView pimage,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            qty=itemView.findViewById(R.id.qty);
            db = FirebaseFirestore.getInstance();
            price =itemView.findViewById(R.id.price);
            name=itemView.findViewById(R.id.name);
            pimage=itemView.findViewById(R.id.imagecart);
            delete=itemView.findViewById(R.id.deletecard);

        }



    }

}
