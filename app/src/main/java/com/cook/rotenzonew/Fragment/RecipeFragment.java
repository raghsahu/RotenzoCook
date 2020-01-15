package com.cook.rotenzonew.Fragment;



import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.RecipeNavigationAdapter2;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.MenuRecipe_Model;

import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.APIClient;
import com.cook.rotenzonew.Utils.Api_Call;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {
    View view;
    RecyclerView gridView;
    LinearLayout ll_empty_list;
    String id;
    String mStrUser_id;
    RecipeNavigationAdapter2 recipeNavigationAdapter;

    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_recipe, container, false);
        getActivity().setTitle(R.string.menu);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();
        gridView = view.findViewById(R.id.recyclerView);
        ll_empty_list = view.findViewById(R.id.ll_empty_list);
        id= Shared_Pref.getProd_Id(getActivity());

        getMenuReipe(id);
        return view;


    }

    private void getMenuReipe(String prod_id) {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<MenuRecipe_Model> call = apiInterface.MenuRecipe_Api(prod_id,mStrUser_id);
        call.enqueue(new Callback<MenuRecipe_Model>() {
            @Override
            public void onResponse(Call<MenuRecipe_Model> call, retrofit2.Response<MenuRecipe_Model> response) {

                try{

                    if (response!=null){
                        if (response.body().getResult().equalsIgnoreCase("true")){
                            ll_empty_list.setVisibility(View.GONE);
                            gridView.setVisibility(View.VISIBLE);
                            Log.e("rec_book_img", response.body().getData().get(0).getMenu_image());

                            recipeNavigationAdapter = new RecipeNavigationAdapter2(response.body().getData(),getActivity());
                            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
                            gridView.setLayoutManager(manager);
                            gridView.setAdapter(recipeNavigationAdapter);

                        }else {
                            ll_empty_list.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("recipe", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<MenuRecipe_Model> call, Throwable t) {

                Log.e("error",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
