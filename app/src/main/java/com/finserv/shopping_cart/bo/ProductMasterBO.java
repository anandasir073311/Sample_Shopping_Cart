package com.finserv.shopping_cart.bo;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductMasterBO implements Parcelable {

    private String uid, name, description, image, category;
    private int price;

    public ProductMasterBO() {

    }

    public ProductMasterBO(String uid, String name, String description, String image, int price, String category) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Parcelling part
    public ProductMasterBO(Parcel in) {
        uid = in.readString();
        name = in.readString();
        description = in.readString();
        description = in.readString();
        image = in.readString();
        price = in.readInt();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeInt(price);
        dest.writeString(category);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ProductMasterBO createFromParcel(Parcel in) {
            return new ProductMasterBO(in);
        }

        public ProductMasterBO[] newArray(int size) {
            return new ProductMasterBO[size];
        }
    };

    private int count , qty;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
