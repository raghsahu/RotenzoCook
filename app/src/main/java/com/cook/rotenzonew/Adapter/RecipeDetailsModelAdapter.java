package com.cook.rotenzonew.Adapter;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Fragment.Recipe_Detail;
import com.cook.rotenzonew.Model.RecipeDetailsModelData;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsModelAdapter extends RecyclerView.Adapter<RecipeDetailsModelAdapter.ViewHolder>  {
    private List<RecipeDetailsModelData> contactModels;
    RecipeDetailsModelData contactModel;
    public FragmentActivity fx;
    View viewlike;

    public void updateList(ArrayList<RecipeDetailsModelData> contact_li) {
        contactModels = contact_li;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView RecipeText,mTextRecipeTimeCalori;


        ImageView imgRestaurant;
        LinearLayout mLinearLayout;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            RecipeText=viewlike.findViewById(R.id.RecipeText);
             imgRestaurant=viewlike.findViewById(R.id.imgRestaurant);
            mTextRecipeTimeCalori=viewlike.findViewById(R.id.mTextRecipeTimeCalori);
            mLinearLayout=viewlike.findViewById(R.id.mLinearLayout);

        }
    }

    public RecipeDetailsModelAdapter(FragmentActivity mContext, List<RecipeDetailsModelData> contactModelArrayList) {
        fx = mContext;
        contactModels = contactModelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_details_adapter_model, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            viewHolder.mTextRecipeTimeCalori.setText(contactModel.getRecipTime()+" min "+" | "+contactModel.getRecipKcal()+" kcl ");
            viewHolder.RecipeText.setText(contactModel.getRecipName());
            Picasso.with(fx)
                    .load(URLs.RECIPE_URL + contactModel.getImage())
                    .error(R.drawable.foodimg).placeholder(R.drawable.foodimg)
                    .into(viewHolder.imgRestaurant);

        } else{
            Toast.makeText(fx, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment5 = new Recipe_Detail();
                Bundle args = new Bundle();
                args.putString("recipe_id", contactModels.get(position).getId());
                args.putSerializable("RecipeDetailsModelData", contactModels.get(position));
                fragment5.setArguments(args);
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

      //  Log.e("model_size", ""+contactModels.get(position).getId());

    }


    @Override
    public int getItemCount() {
        return contactModels.size();
    }

}
