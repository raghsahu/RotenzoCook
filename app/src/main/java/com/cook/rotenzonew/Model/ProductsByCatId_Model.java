package com.cook.rotenzonew.Model;

public class ProductsByCatId_Model {

    String id;
    String cat_id;
    String product_brand,product_name;
    String image,video;
    String site_link;
    String model;
    String type;
    String shape;
    String capacity;


    public ProductsByCatId_Model(String id, String cat_id, String product_brand, String product_name, String image,
                                 String video, String site_link, String model,
                                 String type, String shape, String capacity) {
        this.id = id;
        this.cat_id = cat_id;
        this.product_brand = product_brand;
        this.product_name = product_name;
        this.image = image;
        this.video = video;
        this.site_link = site_link;
        this.capacity = capacity;
        this.shape = shape;
        this.type = type;
        this.model = model;
    }


    public String getProduct_brand() {
        return product_brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getImage() {
        return image;
    }

    public String getSite_link() {
        return site_link;
    }

    public void setSite_link(String site_link) {
        this.site_link = site_link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
