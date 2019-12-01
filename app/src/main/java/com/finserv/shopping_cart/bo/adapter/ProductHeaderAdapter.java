package com.finserv.shopping_cart.bo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;

import java.util.HashMap;
import java.util.Vector;

public class ProductHeaderAdapter extends RecyclerView.Adapter<ProductHeaderAdapter.ViewHolder>{

    Context context;
    private HashMap<String, Vector<ProductMasterBO>> listdata;
    ProductListAdapter.productSelectedListener selectionListener;

    // RecyclerView recyclerView;       
    public ProductHeaderAdapter(Context context, HashMap<String, Vector<ProductMasterBO>> listdata, ProductListAdapter.productSelectedListener selectionListener) {
        this.context = context;
        this.listdata = listdata;
        this.selectionListener = selectionListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_catalogheader, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String myListData = (String)listdata.keySet().toArray()[position];
        holder.textView.setText(myListData);
        final Vector<ProductMasterBO> myList = listdata.get(myListData);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.recyclerViewList.setLayoutManager(mLayoutManager);
        holder.recyclerViewList.setItemAnimator(new DefaultItemAnimator());

        ProductListAdapter adapter = new ProductListAdapter(context, myList, selectionListener);
        holder.recyclerViewList.setAdapter(adapter);
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RecyclerView recyclerViewList;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tvTitle);
            this.recyclerViewList = itemView.findViewById(R.id.mRecyclerViewList);

        }
    }
}  
