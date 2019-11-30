package com.finserv.shopping_cart.mvpcontractor;

import com.finserv.shopping_cart.bo.ProductMasterBO;

import java.util.ArrayList;

public interface ShoppingCartContractor {

    interface ShoppingCartPresenter {
        void setView(ShoppingCartView shoppingCartView);
        void parseandInsertProducts(String json);
        void fetchProductList(ArrayList<ProductMasterBO> productList);
        void fetchProductInfo(String productID);
    }

    interface ShoppingCartView {
        void updateProductList(ArrayList<ProductMasterBO> productList);
        void updateProductInfo(ProductMasterBO productBO);
    }
}
