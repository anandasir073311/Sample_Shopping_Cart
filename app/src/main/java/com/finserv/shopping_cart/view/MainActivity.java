package com.finserv.shopping_cart.view;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.bo.adapter.ProductListAdapter;
import com.finserv.shopping_cart.bo.adapter.ShoppingCartAdapter;
import com.finserv.shopping_cart.db.datamembers.DataMembers;

public class MainActivity extends AppCompatActivity implements ProductListAdapter.productSelectedListener, ShoppingCartAdapter.productSelectedListener {

    FrameLayout fragmentContainer;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    LinearLayout content_frame;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        fragmentManager = getSupportFragmentManager();
        fragmentContainer = findViewById(R.id.fragment_content);
        onHomescreenReturn();
    }

    @Override
    public void onProductSelected(ProductMasterBO productBO) {
        Toast.makeText(this, "Selected Product " + productBO.getName(), Toast.LENGTH_LONG).show();
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Product", productBO);
        detailFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, detailFragment, DataMembers.fragment_ProductDetail)
                .commit();
    }

    public void onShoppingCartClicked() {
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, shoppingCartFragment, DataMembers.fragment_ShoppingCart)
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        if(fragment.getTag().equals(DataMembers.fragment_ShoppingCart) || fragment.getTag().equals(DataMembers.fragment_ProductDetail)) {
            onHomescreenReturn();
        } else {
            finish();
        }
    }

    public void onHomescreenReturn(){
        Fragment productFragment = new ProductListFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, productFragment, DataMembers.fragment_ProductList)
                .commit();
    }
}
