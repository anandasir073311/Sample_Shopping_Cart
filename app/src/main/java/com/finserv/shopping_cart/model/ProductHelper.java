package com.finserv.shopping_cart.model;

import android.content.Context;

import com.finserv.shopping_cart.bo.ProductMasterBO;

import java.util.Vector;

public class ProductHelper {

    private static ProductHelper instance = null;
    Vector<ProductMasterBO> productMasterBO = new Vector<>();

    public static ProductHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ProductHelper(context);
        }
        return instance;
    }

    private ProductHelper(Context context) {

    }

    public Vector<ProductMasterBO> getProductMasterBO() {
        return productMasterBO;
    }

    public void setProductMasterBO(Vector<ProductMasterBO> productMasterBO) {
        this.productMasterBO = productMasterBO;
    }

    public void downloadProducts(){

    }
}
