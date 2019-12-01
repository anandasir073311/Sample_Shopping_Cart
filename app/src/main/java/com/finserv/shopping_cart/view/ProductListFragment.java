package com.finserv.shopping_cart.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.adapter.ProductHeaderAdapter;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.presenter.ShoppingCartPresenterImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class ProductListFragment extends Fragment implements ShoppingCartContractor.ShoppingCartView {

    RecyclerView mRecylerViewProductList;
    ShoppingCartPresenterImpl shoppingCartPresenterImpl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.product_list));
        }

        shoppingCartPresenterImpl = new ShoppingCartPresenterImpl(getActivity());
        shoppingCartPresenterImpl.setView(this);

        mRecylerViewProductList = view.findViewById(R.id.mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecylerViewProductList.setLayoutManager(mLayoutManager);
        mRecylerViewProductList.setItemAnimator(new DefaultItemAnimator());

        shoppingCartPresenterImpl.parseandInsertProducts(loadJSONFromAsset());
        shoppingCartPresenterImpl.fetchProductList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void updateProductList(HashMap<String, Vector<ProductMasterBO>> productList) {
        ProductHeaderAdapter adapter = new ProductHeaderAdapter(getContext(), productList , (MainActivity)getActivity());
        mRecylerViewProductList.setAdapter(adapter);
    }

    @Override
    public void updateProductInfo(ProductMasterBO productBO) {

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(getString(R.string.json_filename));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shoppingcart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackButtonClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onBackButtonClick(){
        ((MainActivity)getActivity()).replaceFragment(this);
    }
}
