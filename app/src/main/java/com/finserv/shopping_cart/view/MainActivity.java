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
import com.finserv.shopping_cart.db.datamembers.DataMembers;

public class MainActivity extends AppCompatActivity implements ProductListAdapter.productSelectedListener {

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

//        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        content_frame = findViewById(R.id.root);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout, /* DrawerLayout object */
//                R.string.open, R.string.close) {
//            public void onDrawerClosed(View view) {
//                supportInvalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                supportInvalidateOptionsMenu();
//            }
//
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//
//                float moveFactor = (drawerView.getWidth() * slideOffset);
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null && imm.isActive())
//                    imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
//
//                content_frame.setTranslationX(moveFactor);
//            }
//        };
//
//        mDrawerToggle.syncState();
//        drawerLayout.addDrawerListener(mDrawerToggle);

        fragmentContainer = findViewById(R.id.fragment_content);

        Fragment productFragment = new ProductListFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, productFragment, DataMembers.fragment_ProductList)
                .commit();

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

    public void replaceFragment(Fragment fragment) {

        if(fragment.getTag().equals(DataMembers.fragment_ShoppingCart)){
            Fragment frag = new ProductDetailFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, frag, frag.getClass().getName())
                    .commit();
        } else if(fragment.getTag().equals(DataMembers.fragment_ProductDetail)) {
            Fragment frag = new ProductListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, frag, frag.getClass().getName())
                    .commit();
        } else {
            finish();
        }

    }
}
