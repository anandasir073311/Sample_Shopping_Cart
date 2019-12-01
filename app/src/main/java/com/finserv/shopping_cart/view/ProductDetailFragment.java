package com.finserv.shopping_cart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;
import com.finserv.shopping_cart.presenter.ShoppingCartPresenterImpl;

import java.util.HashMap;
import java.util.Vector;

public class ProductDetailFragment extends Fragment implements View.OnClickListener, ShoppingCartContractor.ShoppingCartView {

    ProductMasterBO productMasterBO;
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductDescription;
    ImageView imageView;
    Button btnAddtoCart;
    TextView textCartItemCount;
    Spinner spnQty;
    int mCartItemCount = 0;
    ShoppingCartPresenterImpl shoppingCartPresenterImpl;
    String[] qtyArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);

        if(getArguments() != null){
            productMasterBO = getArguments().getParcelable("Product");
        }
        setHasOptionsMenu(true);

        shoppingCartPresenterImpl = new ShoppingCartPresenterImpl(getActivity());
        shoppingCartPresenterImpl.setView(this);

        txtProductName = view.findViewById(R.id.txtProductName);
        txtProductPrice = view.findViewById(R.id.txtProductPrice);
        txtProductDescription = view.findViewById(R.id.txtProductDescription);
        imageView = view.findViewById(R.id.imageView);
        btnAddtoCart = view.findViewById(R.id.btn_addtocart);
        btnAddtoCart.setOnClickListener(this);
        txtProductName.setText(productMasterBO.getName());
        txtProductPrice.setText(productMasterBO.getPrice() + "");
        txtProductDescription.setText(productMasterBO.getDescription());

        qtyArray = getResources().getStringArray(R.array.qty_array);
        spnQty = (Spinner) view.findViewById(R.id.spnQty);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, qtyArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnQty.setAdapter(dataAdapter);
        spnQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productMasterBO.setQty(Integer.parseInt(qtyArray[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Glide.with(getActivity())
                .load(productMasterBO.getImage())
                .placeholder(R.drawable.ic_photo_camera)
                .dontAnimate()
                .into(imageView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shoppingCartPresenterImpl.fetchProductInfo();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_addtocart){
            if(productMasterBO.getCount() == 0) {
                productMasterBO.setCount(1);
                shoppingCartPresenterImpl.updateProductInfo(productMasterBO);
            } else{
                Toast.makeText(getContext(), "Item Already Added", Toast.LENGTH_LONG).show();
            }

        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackButtonClick();
            return true;
        }
        else if (i == R.id.shoppingcart) {

            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void onBackButtonClick(){
        ((MainActivity)getActivity()).replaceFragment(this);
    }

    @Override
    public void updateProductList(HashMap<String, Vector<ProductMasterBO>> productList) {

    }

    @Override
    public void updateProductCount(Vector<ProductMasterBO> productList) {
        mCartItemCount = 0;
        for(ProductMasterBO productMasterBO : productList){
            if(productMasterBO.getCount() != 0){
                mCartItemCount = mCartItemCount + 1;
            }
        }
        getActivity().invalidateOptionsMenu();
    }
}
