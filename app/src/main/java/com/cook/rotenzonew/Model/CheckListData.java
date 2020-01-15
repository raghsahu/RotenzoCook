package com.cook.rotenzonew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 05/12/2019.
 */
public class CheckListData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("recipe_id")
    @Expose
    private String recipeId;
    @SerializedName("ingredient_id")
    @Expose
    private String ingredientId;
    @SerializedName("recip_name")
    @Expose
    private String recipName;
    @SerializedName("ingredients")
    @Expose
    private List<MyIngredient> ingredients = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getRecipName() {
        return recipName;
    }

    public void setRecipName(String recipName) {
        this.recipName = recipName;
    }

    public List<MyIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MyIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
