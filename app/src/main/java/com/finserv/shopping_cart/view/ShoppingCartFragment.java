package com.finserv.shopping_cart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.bo.adapter.ShoppingCartAdapter;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;
import com.finserv.shopping_cart.presenter.ShoppingCartPresenterImpl;

import java.util.HashMap;
import java.util.Vector;

public class ShoppingCartFragment extends Fragment implements ShoppingCartContractor.ShoppingCartView {

    RecyclerView mRecyclerView;
    Button btnSave;
    ShoppingCartPresenterImpl shoppingCartPresenterImpl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        setHasOptionsMenu(true);

        shoppingCartPresenterImpl = new ShoppingCartPresenterImpl(getActivity());
        shoppingCartPresenterImpl.setView(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shoppingCartPresenterImpl.insertShoppingCart()){
                    Toast.makeText(getContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).onHomescreenReturn();
                } else {
                    Toast.makeText(getContext(), getString(R.string.problem_in_saving), Toast.LENGTH_LONG).show();
                }
            }
        });

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

    }

    @Override
    public void updateProductCount(Vector<ProductMasterBO> productList) {
        Vector<ProductMasterBO> shoppingCartList = new Vector<>();
        for(ProductMasterBO product : productList){
            if(product.getCount() > 0){
                shoppingCartList.add(product);
            }
        }

        ShoppingCartAdapter adapter = new ShoppingCartAdapter(getContext(), shoppingCartList, (MainActivity)getActivity(), shoppingCartPresenterImpl);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shoppingcart, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.shoppingcart).setVisible(false);
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
