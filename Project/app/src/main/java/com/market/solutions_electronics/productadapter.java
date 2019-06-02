package com.market.solutions_electronics;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public  class productadapter extends RecyclerView.Adapter<productadapter.ViewHolder> {

    Context context;
    ArrayList<productinfo> productinfos;
    List<String> des;
    List<String> nam;
    List<String> price;
    List<String> url;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final String desc = des.get(position);
        final String names = nam.get(position);
        final String pric=price.get(position);
        final String uri=url.get(position);

        viewHolder.name.setText(names);
        viewHolder.price.setText(pric);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price =itemView.findViewById(R.id.productprice);
            name=itemView.findViewById(R.id.productname);
            pimage=itemView.findViewById(R.id.productimage);
        }

    }
}
