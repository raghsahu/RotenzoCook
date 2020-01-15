package com.cook.rotenzonew.Model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

public class RecipeModelNavigation implements Serializable {


    String  menu_id,menu_name,mn_menu_name,menu_img,count;


 /*   id,recip_name,mn_recip_name,recip_kcal,
            recip_time,product_id,menu_id,image;*/
 private List<RecipeDetailsModelData> recipe = null;

    public RecipeModelNavigation(String menu_id, String menu_name, String mn_menu_name, String menu_img, String count, JSONArray recipe) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.mn_menu_name = mn_menu_name;
        this.menu_img=menu_img;
        this.count = count;
        this.recipe= (List<RecipeDetailsModelData>) recipe;
    }


    public String getMenu_img() {
        return menu_img;
    }

    public void setMenu_img(String menu_img) {
        this.menu_img = menu_img;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMn_menu_name() {
        return mn_menu_name;
    }

    public void setMn_menu_name(String mn_menu_name) {
        this.mn_menu_name = mn_menu_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }





    public List<RecipeDetailsModelData> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<RecipeDetailsModelData> recipe) {
        this.recipe = recipe;
    }





}
