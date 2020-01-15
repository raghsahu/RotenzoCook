package com.cook.rotenzonew.Model;

public class Particular_Recipe_Ingredient_Model {
String id,ingredients,ingred_quantity;


    public Particular_Recipe_Ingredient_Model(String id, String ingredients, String ingred_quantity) {
        this.id = id;
        this.ingredients = ingredients;
        this.ingred_quantity = ingred_quantity;
    }


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

    public String getIngred_quantity() {
        return ingred_quantity;
    }

    public void setIngred_quantity(String ingred_quantity) {
        this.ingred_quantity = ingred_quantity;
    }
}
