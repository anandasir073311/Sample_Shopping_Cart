package com.finserv.shopping_cart.mvpcontractor;

import com.finserv.shopping_cart.bo.ProductMasterBO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public interface ShoppingCartContractor {

    interface ShoppingCartPresenter {
        void setView(ShoppingCartView shoppingCartView);
        void parseandInsertProducts(String json);
        void fetchProductList();
        void fetchProductInfo(String productID);
    }

    interface ShoppingCartView {
        void updateProductList(HashMap<String, Vector<ProductMasterBO>> productList);
        void updateProductInfo(ProductMasterBO productBO);
    }
}
