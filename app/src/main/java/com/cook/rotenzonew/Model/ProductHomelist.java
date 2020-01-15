package com.cook.rotenzonew.Model;

public class ProductHomelist {

String id,cat_name,mn_cat_name,icon;


    public ProductHomelist(String id, String cat_name, String icon) {
        this.id = id;
        this.cat_name = cat_name;
        this.icon=icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getMn_cat_name() {
        return mn_cat_name;
    }

    public void setMn_cat_name(String mn_cat_name) {
        this.mn_cat_name = mn_cat_name;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
