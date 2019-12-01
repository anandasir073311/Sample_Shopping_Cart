package com.finserv.shopping_cart.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
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
    int mCartItemCount = 0;
    TextView textCartItemCount;

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
    public void onResume() {
        super.onResume();
        shoppingCartPresenterImpl.fetchProductInfo();
    }

    @Override
    public void updateProductList(HashMap<String, Vector<ProductMasterBO>> productList) {
        ProductHeaderAdapter adapter = new ProductHeaderAdapter(getContext(), productList , (MainActivity)getActivity());
        mRecylerViewProductList.setAdapter(adapter);
    }

    @Override
    public void updateProductCount(Vector<ProductMasterBO> productList) {
        for(ProductMasterBO productMasterBO : productList){
            if(productMasterBO.getCount() != 0){
                mCartItemCount = mCartItemCount + 1;
            }
        }
        getActivity().invalidateOptionsMenu();
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

        final MenuItem menuItem = menu.findItem(R.id.shoppingcart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackButtonClick();
            return true;
        } else if (i == R.id.shoppingcart) {
            ((MainActivity)getActivity()).onShoppingCartClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onBackButtonClick(){
        ((MainActivity)getActivity()).replaceFragment(this);
    }
}
