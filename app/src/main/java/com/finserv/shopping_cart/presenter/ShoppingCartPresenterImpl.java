package com.finserv.shopping_cart.presenter;

import android.content.Context;
import android.util.Log;

import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.model.ProductHelper;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartPresenterImpl implements ShoppingCartContractor.ShoppingCartPresenter {

    ShoppingCartContractor.ShoppingCartView shoppingCartView;
    private Context context;
    private ProductHelper productHelper;

    @Override
    public void setView(ShoppingCartContractor.ShoppingCartView shoppingCartView) {
        this.shoppingCartView = shoppingCartView;
    }

    public ShoppingCartPresenterImpl(Context context) {
        this.context = context;
        productHelper = ProductHelper.getInstance(context);
    }

    @Override
    public void parseandInsertProducts(String jsonString) {
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray m_jArry = obj.getJSONArray("formules");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("formule");
                String url_value = jo_inside.getString("url");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("formule", formula_value);
                m_li.put("url", url_value);

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchProductList(ArrayList<ProductMasterBO> productList) {

    }

    @Override
    public void fetchProductInfo(String productID) {

    }
}
