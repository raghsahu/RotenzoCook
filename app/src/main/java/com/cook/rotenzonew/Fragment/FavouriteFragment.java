package com.cook.rotenzonew.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.FavoriteAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;

import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.FavouriteModel;
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
public class FavouriteFragment extends Fragment {
  View view;
    RequestQueue queue;
    FavoriteAdapter favoriteAdapter;
     RecyclerView recyclerView;
    String mStrUser_id;
    LinearLayout ll_empty_list;


    private List<FavouriteModel> favouriteModelArrayList = new ArrayList<>();

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favourite, container, false);
       getActivity().setTitle(R.string.favourite);

        queue= Volley.newRequestQueue(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ll_empty_list =  view.findViewById(R.id.ll_empty_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();
        Log.e("check favourite user id",mStrUser_id);

        getRecipeDetails(mStrUser_id);

    return  view;
    }


    private void getRecipeDetails( final String user_id) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"all_favrt_list",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response_Pro_fav", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                ll_empty_list.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String user_id = jsonObject1.getString("user_id");
                                    String recipe_id = jsonObject1.getString("recipe_id");
                                    String recip_name = jsonObject1.getString("recip_name");
                                    String image = jsonObject1.getString("image");
                                    String recip_time = jsonObject1.getString("recip_time");
                                    String recip_kcal = jsonObject1.getString("recip_kcal");


                                    favouriteModelArrayList.add(i, new FavouriteModel(user_id, recipe_id, recip_name, image,recip_time,recip_kcal));

                                }

                                favoriteAdapter = new FavoriteAdapter(favouriteModelArrayList, getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                               // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                recyclerView.setAdapter(favoriteAdapter);
                                favoriteAdapter.notifyDataSetChanged();

                                if (getActivity() != null) {
                                   /* if (product_modelList.isEmpty()) {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                                    }*/
                                }

                            } else {
                                ll_empty_list.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
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

                return params;
            }
        };
        queue.add(postRequest);

    }




}
