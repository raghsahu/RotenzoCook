package com.cook.rotenzonew.Fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.Particular_Recipe_ing_Adapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.Particular_Recipe_Ingredient_Model;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Particular_Recipe_Ingredient_Fragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    String mStrUser_id,recipe_id;
    Particular_Recipe_Ingredient_Model particularRecipeIngredientModel;
    Particular_Recipe_ing_Adapter  particular_rec_ing_adapter;
    RequestQueue queue;

    private List<Particular_Recipe_Ingredient_Model> particularRecipeIngredientModelArrayList = new ArrayList<>();

    public Particular_Recipe_Ingredient_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_particular__recipe__ingredient_, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        queue= Volley.newRequestQueue(getActivity());
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        if (getArguments() != null)
            {
                recipe_id = getArguments().getString("recipe_id");
               // Toast.makeText(getActivity(), " " + recipe_id, Toast.LENGTH_SHORT).show();
            }

        mFunChecklistApi(mStrUser_id,recipe_id);
            return view;

        }


    public void mFunChecklistApi(final String user_id,final String recipe_id)
    {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"get_user_ingredients",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("check list response", response);

                        try {
                            particularRecipeIngredientModelArrayList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id = jsonObject1.getString("id");
                                    String user_id = jsonObject1.getString("user_id");
                                    String recipe_id = jsonObject1.getString("recipe_id");
                                    String recip_name = jsonObject1.getString("recip_name");
                                    String image = jsonObject1.getString("image");
                                    String ingredient_id = jsonObject1.getString("ingredient_id");

                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("result");
                                  for (int j=0;j<jsonArray1.length();j++)
                                  {
                                      JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                      String mStrid = jsonObject2.getString("id");
                                      String mStringredients = jsonObject2.getString("ingredients");
                                      String mStringred_quantity = jsonObject2.getString("ingred_quantity");
                                      particularRecipeIngredientModelArrayList.add(j, new Particular_Recipe_Ingredient_Model(mStrid, mStringredients, mStringred_quantity));
                                  }


                                }



                                particular_rec_ing_adapter = new Particular_Recipe_ing_Adapter(particularRecipeIngredientModelArrayList, getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                recyclerView.setAdapter(particular_rec_ing_adapter);
                                particular_rec_ing_adapter.notifyDataSetChanged();


                            } else {
                                Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        queue.add(postRequest);

    }


}
