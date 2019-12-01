package com.finserv.shopping_cart.presenter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.db.DBUtil;
import com.finserv.shopping_cart.model.ProductHelper;
import com.finserv.shopping_cart.mvpcontractor.ShoppingCartContractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

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
            JSONArray obj = new JSONArray(jsonString);
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jo_inside = obj.getJSONObject(i);
                String uid = jo_inside.getString("uid");
                String image = jo_inside.getString("image");
                String name = jo_inside.getString("name");
                String description = jo_inside.getString("description");
                int price = jo_inside.getInt("price");
                String category = jo_inside.getString("category");
                productHelper.insertProducts(new ProductMasterBO(uid, name, description,
                        image, price, category));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void fetchProductList() {
        try{
            productHelper.downloadProducts();
            HashMap<String,Vector<ProductMasterBO>> productMap = new HashMap<>();
            Vector<ProductMasterBO> pList = new Vector<>();
            for(ProductMasterBO product : productHelper.getProductMasterBO()){
                if(!productMap.containsKey(product.getCategory())) {
                    pList = new Vector<>();
                    productMap.put(product.getCategory(), new Vector<ProductMasterBO>());
                }
                pList.add(product);
                productMap.put(product.getCategory(), pList);
            }
            shoppingCartView.updateProductList(productMap);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void fetchProductInfo(String productID) {

    }
}
