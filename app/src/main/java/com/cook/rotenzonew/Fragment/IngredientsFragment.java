package com.cook.rotenzonew.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.IngredientAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.IngredientModel;
import com.cook.rotenzonew.Model.RecipeProcess_Model;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cook.rotenzonew.Adapter.IngredientAdapter.list;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {
View view;
    private ArrayList<IngredientModel> countryList;
    String mStrUser_id,  recipe_id;
    RequestQueue queue;
    ArrayList<IngredientModel> ingredientModels=new ArrayList<>();
    RecyclerView recycler_ingre;
    IngredientAdapter ingredientAdapter=null;
    Button button;
    ImageView iv_minus,iv_add;
    TextView tv_count_number;


    private List<IngredientModel> ingredientslist = new ArrayList<>();
    String ing_qty1;


    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        recycler_ingre=view.findViewById(R.id.recyclerView);
        button=view.findViewById(R.id.button);
        tv_count_number=view.findViewById(R.id.tv_count_number);
        iv_minus=view.findViewById(R.id.iv_minus);
        iv_add=view.findViewById(R.id.iv_add);

        queue= Volley.newRequestQueue(getActivity());
        if (getArguments()!=null){
              recipe_id =getArguments().getString("recipe_id");
            System.out.println("recpiy id is    "+recipe_id);
            getRecipeDetails(recipe_id);
        }

        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("");
                List<String> countryList = list;
                for(int i=0;i<countryList.size();i++){
                    String country = list.get(i);
                        responseText.append(country + ","  );
                }
                try {
                    String mStrIngredientSelectedList = responseText.substring(0, responseText.lastIndexOf(","));
                    mFunSubmitIngredientdata(mStrUser_id,recipe_id,mStrIngredientSelectedList);
                   // Toast.makeText(getActivity(),mStrIngredientSelectedList, Toast.LENGTH_SHORT).show();


                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), R.string.select_ingre, Toast.LENGTH_SHORT).show();

                }
            }
        });

        //***********************************

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String int_qty=tv_count_number.getText().toString();

                    try {
                        int qty = Integer.valueOf(tv_count_number.getText().toString());
                        qty = qty + 1;

                        tv_count_number.setText(String.valueOf(qty));

//                        for (int i=0; i<ingredientslist.size();i++){
//                            ing_qty1=ingredientslist.get(i).getIngred_quantity();
//
//                        }

                        Log.e("ingre_size", ""+ingredientModels.size());
                        for (int k=0; k<ingredientModels.size();k++){
                            String ing_qty=ingredientslist.get(k).getIngred_quantity();

                            double totalamount=Double.parseDouble(tv_count_number.getText().toString())
                                    * Double.valueOf(ing_qty);

                            Log.e("ingre_total", ""+totalamount);

                            ingredientModels.get(k).setIngred_quantity(String.valueOf(totalamount));
                            ingredientAdapter.notifyDataSetChanged();
                        }


                    }catch (Exception e){
                        Log.e("qty",""+" is not a number");
                    }


            }
        });

        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int qty = Integer.valueOf(tv_count_number.getText().toString());
                    if (qty > 1) {
                        qty = qty - 1;
                        tv_count_number.setText(String.valueOf(qty));

                        Log.e("ingre_size", ""+ingredientModels.size());
                        for (int k=0; k<ingredientModels.size();k++){
                            String ing_qty=ingredientslist.get(k).getIngred_quantity();

                            double totalamount=Double.parseDouble(tv_count_number.getText().toString())
                                    * Double.valueOf(ing_qty);

                            Log.e("ingre_total", ""+totalamount);

                            ingredientModels.get(k).setIngred_quantity(String.valueOf(totalamount));
                            ingredientAdapter.notifyDataSetChanged();
                        }



                    }

                }catch (Exception e){
                    Log.e("qty",""+" is not a number");
                }

            }
        });



   return view;

    }

    private void mFunSubmitIngredientdata( final String user_id ,final String recipe_id,final String ingredient_id) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"user_add_ingredient",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response_ingre_details", response);

                        try {
                            ingredientModels.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String result=jsonObject.getString("result");


                            if (result.equalsIgnoreCase("true")) {
                                String msg=jsonObject.getString("msg");

                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                                Fragment fragment5 = new Particular_Recipe_Ingredient_Fragment();
                                Bundle args = new Bundle();
                                args.putString("recipe_id",recipe_id);
                                fragment5.setArguments(args);
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.containt_main_frame, fragment5);
                                transaction.addToBackStack(null);
                                transaction.commit();


                            }

                           else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("recipe_id", recipe_id);
                params.put("ingredient_id", ingredient_id);
                return params;
            }
        };
        queue.add(postRequest);

    }


    private void getRecipeDetails( final String recipe_id) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"recipeDetail",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response_Pro_details1", response);

                        try {
                            ingredientModels.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String result=jsonObject.getString("result");

                            RecipeProcess_Model  recipeProcess_model=new RecipeProcess_Model();

                            if (result.equalsIgnoreCase("true")) {

                                JSONObject jsonObject1=jsonObject.getJSONObject("data");


                                recipeProcess_model.id=jsonObject1.getString("id");
                                recipeProcess_model.product_id=jsonObject1.getString("product_id");
                                recipeProcess_model.image=jsonObject1.getString("image");
                                recipeProcess_model.recip_name=jsonObject1.getString("recip_name");
                                recipeProcess_model.recip_kcal=jsonObject1.getString("recip_kcal");
                                recipeProcess_model.recip_time=jsonObject1.getString("recip_time");
                                recipeProcess_model.recip_description=jsonObject1.getString("recip_description");

                                JSONArray jsonArray=jsonObject1.getJSONArray("ingredients");

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String id1 = jsonObject2.getString("id");
                                    String recipe_id = jsonObject2.getString("recipe_id");
                                    String ingredients = jsonObject2.getString("ingredients");
                                   // String mn_ingredients = jsonObject2.getString("mn_ingredients");
                                   // String mn_ingred_quantity = jsonObject2.getString("mn_ingred_quantity");
                                    String ingred_quantity = jsonObject2.getString("ingred_quantity");
                                    String ingred_unit = jsonObject2.getString("ingred_unit");


                                    ingredientModels.add(i,new IngredientModel(id1,recipe_id,ingredients,ingred_quantity,ingred_unit));

                                    ingredientslist.add(i,new IngredientModel(id1,recipe_id,ingredients,ingred_quantity,ingred_unit));

                                }


                            }else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }

                            ingredientAdapter = new IngredientAdapter(getActivity(),ingredientModels,recipeProcess_model);
                            recycler_ingre.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recycler_ingre.setAdapter(ingredientAdapter);
                            ingredientAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("recipe_id", recipe_id);
                params.put("user_id", mStrUser_id);

                return params;
            }
        };
        queue.add(postRequest);

    }


}
