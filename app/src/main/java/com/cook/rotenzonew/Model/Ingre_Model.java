package com.cook.rotenzonew.Model;

/**
 * Created by Raghvendra Sahu on 09/12/2019.
 */
public class Ingre_Model {

    String id;
    String ingredients;

    public Ingre_Model(String id, String ingredients) {
        this.id=id;
        this.ingredients=ingredients;

    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
