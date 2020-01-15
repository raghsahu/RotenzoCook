package com.cook.rotenzonew.Utils;


import com.cook.rotenzonew.Model.CheckListModel;
import com.cook.rotenzonew.Model.MenuRecipe_Model;
import com.cook.rotenzonew.Model.RecipeModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api_Call {

//    @GET(Base_Url.menus_listBy_productId)
//    Call<City_Model> get_city();


    @FormUrlEncoded
    @POST(Base_Url.menus_listBy_productId)
    Call<MenuRecipe_Model> MenuRecipe_Api(
            @Field("product_id") String prod_id,
             @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Base_Url.RecipesByProductId)
    Call<RecipeModel> HomeRecipe_Api(
            @Field("product_id") String id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Base_Url.viewed_recipeDetail)
    Call<RecipeModel> LastRecipe_Api(
            @Field("user_id") String id);

    @FormUrlEncoded
    @POST(Base_Url.user_ingredient)
    Call<CheckListModel> CheckMyIngre(
            @Field("user_id") String mStrUser_id);


    /*@FormUrlEncoded
    @POST(Base_Url.menus_listBy_productId)
    Call<MenuRecipe_Model> MenuRecipe_Api(
            @Field("product_id") String prod_id);
*/


}
