package com.finserv.shopping_cart.bo;

public class MenuBO {
    private int menuID;
    private String menuName;

    public MenuBO(int menuID, String menuName){
        this.menuID = menuID;
        this.menuName = menuName;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
