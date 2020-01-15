package com.cook.rotenzonew.Model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuRecipeData implements Serializable {
    @SerializedName("menu_id")
    @Expose
    private String menuId;

    @SerializedName("menu_name")
    @Expose private String menuName;

    @SerializedName("menu_img")
    @Expose private String menu_image;



    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("recipe")
    @Expose
    private List<RecipeDetailsModelData> recipe = null;

    public String getMenuId() {
        return menuId;
    }


    public String getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(String menu_image) {
        this.menu_image = menu_image;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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
