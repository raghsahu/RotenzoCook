package com.cook.rotenzonew.Model;

public class FavouriteModel {

    String user_id,recipe_id,recip_name,image,recip_time,recip_kcal;


    public FavouriteModel(String user_id, String recipe_id, String recip_name, String image,
                          String recip_time, String recip_kcal) {
        this.user_id = user_id;
        this.recipe_id = recipe_id;
        this.recip_name = recip_name;
        this.image = image;
        this.recip_time = recip_time;
        this.recip_kcal = recip_kcal;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecip_name() {
        return recip_name;
    }

    public String getRecip_kcal() {
        return recip_kcal;
    }

    public void setRecip_kcal(String recip_kcal) {
        this.recip_kcal = recip_kcal;
    }

    public String getRecip_time() {
        return recip_time;
    }

    public void setRecip_time(String recip_time) {
        this.recip_time = recip_time;
    }

    public void setRecip_name(String recip_name) {
        this.recip_name = recip_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
