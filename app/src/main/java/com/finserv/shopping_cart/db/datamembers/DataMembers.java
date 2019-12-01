package com.finserv.shopping_cart.db.datamembers;

public class DataMembers {

    public static final String tbl_productMaster = "ProductMaster";
    public static final String tbl_productMaster_cols = "UID,Name,Description,Image,Price,Category";

    public static final String tbl_orderMaster = "OrderTransaction";
    public static final String tbl_orderMaster_cols = "UID,Date,PID,Qty";

    public static final String fragment_ProductList = "FRAGMENT_PRODUCTLIST";
    public static final String fragment_ProductDetail = "FRAGMENT_PRODUCTDETAIL";
    public static final String fragment_ShoppingCart = "FRAGMENT_SHOPPINGCART";
}
