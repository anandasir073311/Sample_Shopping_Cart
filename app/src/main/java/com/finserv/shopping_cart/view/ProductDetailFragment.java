package com.finserv.shopping_cart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;

public class ProductDetailFragment extends Fragment {

    ProductMasterBO productMasterBO;
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductDescription;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);

        if(getArguments() != null){
            productMasterBO = getArguments().getParcelable("Product");
        }
        setHasOptionsMenu(true);
        txtProductName = view.findViewById(R.id.txtProductName);
        txtProductPrice = view.findViewById(R.id.txtProductPrice);
        txtProductDescription = view.findViewById(R.id.txtProductDescription);
        imageView = view.findViewById(R.id.imageView);

        txtProductName.setText(productMasterBO.getName());
        txtProductPrice.setText(productMasterBO.getPrice() + "");
        txtProductDescription.setText(productMasterBO.getDescription());

        Glide.with(getActivity())
                .load(productMasterBO.getImage())
                .placeholder(R.drawable.ic_photo_camera)
                .dontAnimate()
                .into(imageView);

        return view;
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
        else if (i == R.id.shoppingcart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onBackButtonClick(){
        ((MainActivity)getActivity()).replaceFragment(this);
    }
}
