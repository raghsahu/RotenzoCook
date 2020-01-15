package com.cook.rotenzonew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckListModel {
//    String user_id;
//    String ingredients;
//    String ingred_quantity;
//    String ingred_unit;
//
//
//
//    public CheckListModel(String user_id, String ingredients, String ingred_quantity, String ingred_unit) {
//        this.user_id = user_id;
//        this.ingredients = ingredients;
//        this.ingred_quantity = ingred_quantity;
//        this.ingred_unit = ingred_unit;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(String ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public String getIngred_quantity() {
//        return ingred_quantity;
//    }
//
//    public String getIngred_unit() {
//        return ingred_unit;
//    }
//
//    public void setIngred_unit(String ingred_unit) {
//        this.ingred_unit = ingred_unit;
//    }
//
//    public void setIngred_quantity(String ingred_quantity) {
//        this.ingred_quantity = ingred_quantity;
//    }


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<CheckListData> data = null;

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

    public List<CheckListData> getData() {
        return data;
    }

    public void setData(List<CheckListData> data) {
        this.data = data;
}
}
