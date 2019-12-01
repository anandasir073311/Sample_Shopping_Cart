package com.finserv.shopping_cart.bo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.presenter.ShoppingCartPresenterImpl;

import java.util.Vector;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{

    private Vector<ProductMasterBO> listdata;
    Context context;
    productSelectedListener selectionListener;
    ShoppingCartPresenterImpl shoppingCartPresenterImpl;

    public ShoppingCartAdapter(Context context, Vector<ProductMasterBO> listdata, productSelectedListener selectionListener,
                               ShoppingCartPresenterImpl shoppingCartPresenterImpl){
        this.context = context;
        this.listdata = listdata;
        this.selectionListener = selectionListener;
        this.shoppingCartPresenterImpl = shoppingCartPresenterImpl;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_shoppingcart, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductMasterBO myListData = listdata.get(position);
        holder.textView.setText(myListData.getName());
        holder.subTextView.setText(context.getString (R.string.price) + " : " + (myListData.getPrice() * myListData.getQty()));
        Glide.with(context)
                .load(listdata.get(position).getImage())
                .placeholder(R.drawable.ic_photo_camera)
                .dontAnimate()
                .into(holder.imageView);
        holder.txtCounter.setText(myListData.getQty() + "");
        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.onProductSelected(myListData);
            }
        });
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListData.setQty((myListData.getQty() - 1) <= 0 ? 0 : (myListData.getQty() - 1));
                myListData.setCount(myListData.getQty() > 0 ? 1 : 0);
                shoppingCartPresenterImpl.updateProductInfo(myListData);
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListData.setQty(myListData.getQty() + 1);
                shoppingCartPresenterImpl.updateProductInfo(myListData);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListData.setQty(0);
                myListData.setCount(0);
                shoppingCartPresenterImpl.updateProductInfo(myListData);
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
        public TextView subTextView;
        public Button btnDecrease;
        public Button btnIncrease;
        public Button btnDelete;
        public TextView txtCounter;
        public ConstraintLayout productLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            this.textView = (TextView) itemView.findViewById(R.id.tvTitle);
            this.subTextView = (TextView) itemView.findViewById(R.id.tvPrice);
            this.btnDecrease = (Button) itemView.findViewById(R.id.decrease);
            this.btnIncrease = (Button) itemView.findViewById(R.id.increase);
            this.txtCounter = (TextView) itemView.findViewById(R.id.integer_number);
            this.btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            productLayout = (ConstraintLayout) itemView.findViewById(R.id.productLayout);
        }
    }

    public interface productSelectedListener {
        void onProductSelected(ProductMasterBO productBO);
    }
}  
