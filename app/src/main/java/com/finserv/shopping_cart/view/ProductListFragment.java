package com.finserv.shopping_cart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.presenter.ShoppingCartPresenterImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductListFragment extends Fragment implements ShoppingCartContractor.ShoppingCartView {

    RecyclerView mRecylerViewProductList;
    ShoppingCartPresenterImpl shoppingCartPresenterImpl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        shoppingCartPresenterImpl = new ShoppingCartPresenterImpl(getActivity());
        shoppingCartPresenterImpl.setView(this);

        shoppingCartPresenterImpl.parseandInsertProducts(loadJSONFromAsset());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void updateProductList(ArrayList<ProductMasterBO> productList) {

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
}
