package com.example.android.bookstore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstore.Interfaces.ItemClickListener;
import com.example.android.bookstore.R;

/**
 * Created by hblgdrl on 17.12.2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.OnClick(view, getAdapterPosition(), false);

    }
}
