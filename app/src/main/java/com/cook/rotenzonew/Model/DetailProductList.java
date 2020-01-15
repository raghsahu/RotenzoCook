package com.cook.rotenzonew.Model;

public class DetailProductList  {


    String id,cat_id,product_brand,product_name,image,video,site_link;


    public DetailProductList(String id, String cat_id, String product_brand, String product_name, String image) {
        this.id = id;
        this.cat_id = cat_id;
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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSite_link() {
        return site_link;
    }

    public void setSite_link(String site_link) {
        this.site_link = site_link;
    }
}
