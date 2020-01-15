package com.cook.rotenzonew.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.RecipeDetailsModelAdapter;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.RecipeDetailsModelData;
import com.cook.rotenzonew.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeNavigationDetailFragment extends Fragment {
    View view;
    ImageView iv_filter;
    String recipe_id;
    SearchView searchView;
    RecyclerView recyclerView;
    List<RecipeDetailsModelData> recipeDetailsModelDataList=new ArrayList<>();
    RecipeDetailsModelAdapter recipeDetailsModelAdapter;

    public RecipeNavigationDetailFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view= inflater.inflate(R.layout.fragment_recipe_navigation_detail, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        searchView=view.findViewById(R.id.searchbar);
        iv_filter=view.findViewById(R.id.iv_filter);

        //***************************
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment5 = new FilterFragment();
                Bundle args = new Bundle();
                args.putSerializable("menu_id", recipeDetailsModelDataList.get(0).getMenuId());
                fragment5.setArguments(args);
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        //*********************************************
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        searchView.clearFocus();
        searchView.setFocusable(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });



        try {
            if (getArguments()!=null){
                recipeDetailsModelDataList.clear();
                recipeDetailsModelDataList =getArguments().getParcelableArrayList("menu_recipe_model");

            }
        }catch (Exception e){

        }

        recipeDetailsModelAdapter= new RecipeDetailsModelAdapter(getActivity(),  recipeDetailsModelDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recipeDetailsModelAdapter);


    return  view;


    }

//*********************************************************
 private void filter(String newText) {
 // ArrayList<SearchProductList> temp = new ArrayList();
 ArrayList <RecipeDetailsModelData> Contact_li= new ArrayList<RecipeDetailsModelData>();
 for (RecipeDetailsModelData smodel : recipeDetailsModelDataList) {
 //use .toLowerCase() for better matches
 if (smodel.getRecipName().toLowerCase().startsWith(newText.toLowerCase())) {
 Contact_li.add(smodel);
 }

 }
 //update recyclerview
 recipeDetailsModelAdapter.updateList(Contact_li);

 }

}
