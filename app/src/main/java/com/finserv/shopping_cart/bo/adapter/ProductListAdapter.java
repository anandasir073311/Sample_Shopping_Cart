package com.finserv.shopping_cart.bo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;

import java.util.Vector;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private Vector<ProductMasterBO> listdata;
    Context context;
    productSelectedListener selectionListener;

    public ProductListAdapter(Context context, Vector<ProductMasterBO> listdata, productSelectedListener selectionListener) {
        this.context = context;
        this.listdata = listdata;
        this.selectionListener = selectionListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_cataloglist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductMasterBO myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        Glide.with(context)
                .load(listdata.get(position).getImage())
                .placeholder(R.drawable.ic_photo_camera)
                .dontAnimate()
                .into(holder.imageView);
        System.out.print("");
        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.onProductSelected(myListData);
                //Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ConstraintLayout productLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            this.textView = (TextView) itemView.findViewById(R.id.tvTitle);
            productLayout = (ConstraintLayout) itemView.findViewById(R.id.productLayout);
        }
    }

    public interface productSelectedListener {
        void onProductSelected(ProductMasterBO productBO);
    }
}  
