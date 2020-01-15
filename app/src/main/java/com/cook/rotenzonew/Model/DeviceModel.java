package com.cook.rotenzonew.Model;



public class DeviceModel {

    String id,  product_brand, product_name, image;


    public DeviceModel(String id,  String product_brand, String product_name,String image) {


        this.id = id;
        this.product_brand = product_brand;
        this.product_name = product_name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}