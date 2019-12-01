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
        void updateProductInfo(ProductMasterBO productBO);
        void fetchProductInfo();
    }

    interface ShoppingCartView {
        void updateProductList(HashMap<String, Vector<ProductMasterBO>> productList);
        void updateProductCount(Vector<ProductMasterBO> productList);
    }
}
