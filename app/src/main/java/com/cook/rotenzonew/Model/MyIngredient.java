package com.cook.rotenzonew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 05/12/2019.
 */
public class MyIngredient {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ingredients")
    @Expose
    private String ingredients;
    @SerializedName("ingred_quantity")
    @Expose
    private String ingredQuantity;
    @SerializedName("ingred_unit")
    @Expose
    private String ingredUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredQuantity() {
        return ingredQuantity;
    }

    public void setIngredQuantity(String ingredQuantity) {
        this.ingredQuantity = ingredQuantity;
    }

    public String getIngredUnit() {
        return ingredUnit;
    }

    public void setIngredUnit(String ingredUnit) {
        this.ingredUnit = ingredUnit;
    }
}
