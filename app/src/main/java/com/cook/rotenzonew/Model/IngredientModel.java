package com.cook.rotenzonew.Model;

public class IngredientModel {
    String id,recipe_id,ingredients,ingred_quantity,ingred_unit;
    boolean selected = false;

    public IngredientModel(String id, String recipe_id, String ingredients, String ingred_quantity,
                           String ingred_unit) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.ingredients = ingredients;
        this.ingred_quantity = ingred_quantity;
        this.ingred_unit = ingred_unit;

    }

    public String getIngred_unit() {
        return ingred_unit;
    }

    public void setIngred_unit(String ingred_unit) {
        this.ingred_unit = ingred_unit;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
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
