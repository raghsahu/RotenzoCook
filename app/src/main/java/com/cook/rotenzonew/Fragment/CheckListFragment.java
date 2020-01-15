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
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.CheckListAdapter;
import com.cook.rotenzonew.Adapter.HomeNewRecipeAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.CheckListModel;
import com.cook.rotenzonew.Model.RecipeModel;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.APIClient;
import com.cook.rotenzonew.Utils.Api_Call;
import com.cook.rotenzonew.Utils.Conectivity;
import com.cook.rotenzonew.Utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    String mStrUser_id;
    CheckListModel checkListModel;
    CheckListAdapter checkListAdapter;
    RequestQueue queue;
    LinearLayout ll_empty_list,ll_shoplist;

    private List<CheckListModel> checkListModelArrayList = new ArrayList<>();



    public CheckListFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view= inflater.inflate(R.layout.fragment_check_list, container, false);
       getActivity().setTitle(R.string.shopping_list);

        recyclerView=view.findViewById(R.id.recyclerView);
        ll_empty_list=view.findViewById(R.id.ll_empty_list);
        ll_shoplist=view.findViewById(R.id.ll_shoplist);
        queue= Volley.newRequestQueue(getActivity());
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        if (Conectivity.isConnected(getActivity())){
                GetMyIngre(mStrUser_id); //****retrofit
          //  mFunChecklistApi(mStrUser_id); //****volley
        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }


    return view;

    }

    private void GetMyIngre(String mStrUser_id) {

        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<CheckListModel> call = apiInterface.CheckMyIngre(mStrUser_id);
        call.enqueue(new Callback<CheckListModel>() {
            @Override
            public void onResponse(Call<CheckListModel> call, retrofit2.Response<CheckListModel> response) {

                try{
                    if (response!=null){

                       // Log.e("Last_rec", ""+response.body().getData().size());
                        if (response.body().getResult().equalsIgnoreCase("true")){
                            ll_shoplist.setVisibility(View.VISIBLE);
                            ll_empty_list.setVisibility(View.GONE);


                            checkListAdapter = new CheckListAdapter(response.body().getData(), getActivity());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                            recyclerView.setAdapter(checkListAdapter);
                            checkListAdapter.notifyDataSetChanged();

                        }else {
                            ll_shoplist.setVisibility(View.GONE);
                            ll_empty_list.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("recipe_last", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<CheckListModel> call, Throwable t) {

                Log.e("error_last",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void mFunChecklistApi(final String user_id)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"user_ingredient",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("check list  response", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id = jsonObject1.getString("id");
                                    String ingredients = jsonObject1.getString("ingredients");
                                    String ingred_quantity = jsonObject1.getString("ingred_quantity");
                                    String ingred_unit = jsonObject1.getString("ingred_unit");

                                   //checkListModelArrayList.add(i, new CheckListModel(id, ingredients, ingred_quantity,ingred_unit));

                                }

//                               checkListAdapter = new CheckListAdapter(checkListModelArrayList, getActivity());
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//
//                                recyclerView.setAdapter(checkListAdapter);
//                                checkListAdapter.notifyDataSetChanged();



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

                return params;
            }
        };
        queue.add(postRequest);

    }





}









