package com.market.solutions_electronics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class orderRvadapter extends RecyclerView.Adapter<orderRvadapter.ViewHolder>{


    List<Order> mData = Collections.emptyList();
    Context context2;


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    // data is passed into the constructor
    public orderRvadapter(Context context, List<Order> data) {

        this.mInflater = LayoutInflater.from(context);
        context2=context;
        this.mData = data;

    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_rov, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Order order = mData.get(position);

        holder.id.setText(order.order_time);
        holder.time.setText(order.order_time);


    }
    // total number of rows
    @Override
    public int getItemCount(){
        try{
            return mData.size();}
        catch (Exception e){
            return -1;
        }
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        public TextView id,time,price;


        public ViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.orderid);
            time = itemView.findViewById(R.id.order_time);
            price = itemView.findViewById(R.id.price);

        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view,
                    getAdapterPosition());

        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
