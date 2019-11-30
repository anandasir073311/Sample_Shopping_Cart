package com.finserv.shopping_cart.model;

import android.content.Context;

import com.finserv.shopping_cart.R;
import com.finserv.shopping_cart.bo.ProductMasterBO;
import com.finserv.shopping_cart.db.DBUtil;
import com.finserv.shopping_cart.db.datamembers.DataMembers;

import org.json.JSONException;

import java.util.Vector;

public class ProductHelper {

    private static ProductHelper instance = null;
    private Vector<ProductMasterBO> productMasterBO = new Vector<>();
    private Context context;

    public static ProductHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ProductHelper(context);
        }
        return instance;
    }

    private ProductHelper(Context context) {
        this.context = context;
    }

    public Vector<ProductMasterBO> getProductMasterBO() {
        return productMasterBO;
    }

    public void setProductMasterBO(Vector<ProductMasterBO> productMasterBO) {
        this.productMasterBO = productMasterBO;
    }

    public void downloadProducts(){

    }

    public boolean insertProducts(ProductMasterBO productBO){
        DBUtil db;
        try {
            db = new DBUtil(context, context.getString(R.string.db_name));
            db.createDataBase();
            db.openDataBase();
            String values = getStringQueryParam(productBO.getUid()) + ","
                    + getStringQueryParam(productBO.getName()) + ","
                    + getStringQueryParam(productBO.getDescription()) + ","
                    + getStringQueryParam(productBO.getImage()) + ","
                    + productBO.getPrice() + ","
                    + getStringQueryParam(productBO.getCategory());
            db.insertSQL(DataMembers.tbl_productMaster, DataMembers.tbl_productMaster_cols,
                    values);
            db.closeDB();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return true;
    }

    public static String getStringQueryParam(String data) {
        if (data != null)
            return "'" + data + "'";
        else
            return null;
    }
}
