package com.example.android.bookstore.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bookstore.Interfaces.ItemClickListener;
import com.example.android.bookstore.R;

/**
 * Created by hblgdrl on 16.12.2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView book_title;
    public ImageView book_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public BookViewHolder(View itemView) {
        super(itemView);

        book_title = (TextView)itemView.findViewById(R.id.book_title);
        book_image = (ImageView)itemView.findViewById(R.id.book_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.OnClick(view, getAdapterPosition(), false);

    }
}
