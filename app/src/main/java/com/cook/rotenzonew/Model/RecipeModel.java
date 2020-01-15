package com.cook.rotenzonew.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("total_recipe")
    @Expose
    private Integer totalRecipe;
    @SerializedName("data")
    @Expose
    private List<RecipeDetailsModelData> data = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotalRecipe() {
        return totalRecipe;
    }

    public void setTotalRecipe(Integer totalRecipe) {
        this.totalRecipe = totalRecipe;
    }

    public List<RecipeDetailsModelData> getData() {
        return data;
    }

    public void setData(List<RecipeDetailsModelData> data) {
        this.data = data;
    }
}
