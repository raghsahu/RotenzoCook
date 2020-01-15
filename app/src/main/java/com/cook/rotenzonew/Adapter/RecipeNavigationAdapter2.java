package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.cook.rotenzonew.Fragment.RecipeNavigationDetailFragment;
import com.cook.rotenzonew.Model.MenuRecipeData;
import com.cook.rotenzonew.Model.RecipeDetailsModelData;
import com.cook.rotenzonew.R;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;


public class RecipeNavigationAdapter2 extends RecyclerView.Adapter<RecipeNavigationAdapter2.ViewHolder> {
    private List<MenuRecipeData> contactModels;
    Context context;
    MenuRecipeData contactModel;
    FragmentActivity fx;


    public RecipeNavigationAdapter2(List<MenuRecipeData> drListdata, FragmentActivity ctx) {
        contactModels = drListdata;
        this.fx = ctx;
    }

    @NonNull
    @Override
    public RecipeNavigationAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_navigation_adapter, parent, false);
        return new RecipeNavigationAdapter2.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeNavigationAdapter2.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.txtRecipeName.setText(contactModel.getMenuName());
            holder.count.setText(contactModel.getCount() + " recipe");
            Picasso.with(fx)
                    .load(URLs.MENUS_URL + contactModel.getMenu_image())
                    .error(R.drawable.foodimg)
                    .placeholder(R.drawable.foodimg)
                    .into(holder.imageview);

            Log.e("recipebokk_img",""+ contactModel.getMenu_image());

            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<RecipeDetailsModelData> recipe_model = new ArrayList<>(contactModels.get(position).getRecipe().size());
                    recipe_model.addAll(contactModels.get(position).getRecipe());

                    Fragment fragment5 = new RecipeNavigationDetailFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("menu_recipe_model", (ArrayList<? extends Parcelable>) contactModels.get(position).getRecipe());
                    fragment5.setArguments(args);
                    FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.containt_main_frame, fragment5);
                    transaction.addToBackStack(null);
                    transaction.commit();


                }
            });

        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        } }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRecipeName, count;
        public ImageView imageview;
        public LinearLayout mLinearLayout;


        public ViewHolder(View parent) {
            super(parent);
            mLinearLayout = parent.findViewById(R.id.mLinearLayout);
            imageview = parent.findViewById(R.id.image);
            count = parent.findViewById(R.id.mTextRecipecount);
            txtRecipeName = parent.findViewById(R.id.txtRecipeName);


        }
    }


}
