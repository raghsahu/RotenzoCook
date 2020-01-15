package com.cook.rotenzonew.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
public final class RecipeDetailsModelData implements Serializable, Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("recip_name")
    @Expose
    private String recipName;
    @SerializedName("recip_kcal")
    @Expose
    private String recipKcal;

    @SerializedName("recip_time")
    @Expose
    private String recipTime;

    @SerializedName("recipe_advantage")
    @Expose
    private String recipe_Advantage;

    @SerializedName("recipe_protein")
    @Expose
    private String recipe_Protein;

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("image")
    @Expose
    private String image;

    public RecipeDetailsModelData(Parcel in) {
        id = in.readString();
        recipName = in.readString();
        recipe_Advantage = in.readString();
        recipKcal = in.readString();
        recipTime = in.readString();
        productId = in.readString();
        menuId = in.readString();
        image = in.readString();
        recipe_Protein=in.readString();

    }

    public RecipeDetailsModelData() {

    }




    public String getRecipe_Protein() {
        return recipe_Protein;
    }

    public void setRecipe_Protein(String recipe_Protein) {
        this.recipe_Protein = recipe_Protein;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipName() {
        return recipName;
    }


    public String getRecipe_Advantage() {
        return recipe_Advantage;
    }

    public void setRecipe_Advantage(String recipe_Advantage) {
        this.recipe_Advantage = recipe_Advantage;
    }



    public void setRecipName(String recipName) {
        this.recipName = recipName;
    }

    public String getRecipKcal() {
        return recipKcal;
    }

    public void setRecipKcal(String recipKcal) {
        this.recipKcal = recipKcal;
    }

    public String getRecipTime() {
        return recipTime;
    }

    public void setRecipTime(String recipTime) {
        this.recipTime = recipTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }



    public static final Creator<RecipeDetailsModelData> CREATOR = new Creator<RecipeDetailsModelData>() {
        @Override
        public RecipeDetailsModelData createFromParcel(Parcel in) {
            return new RecipeDetailsModelData(in);
        }

        @Override
        public RecipeDetailsModelData[] newArray(int size) {
            return new RecipeDetailsModelData[size];
        }
    };
}
