package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Model.IngredientModel;
import com.cook.rotenzonew.Model.RecipeProcess_Model;
import com.cook.rotenzonew.R;

import java.util.ArrayList;
import java.util.List;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    public static ArrayList<IngredientModel> contactModels;
    IngredientModel contactModel;
    RecipeProcess_Model recipeProcess_model;
    public Context context;
    View viewlike;
    public static List<String> list = new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recipe;
        public TextView tv_gram;
        LinearLayout mLinearLayout;
        CheckBox checkbox;


        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_gram = viewlike.findViewById(R.id.tv_gram);
            tv_recipe = viewlike.findViewById(R.id.tv_recipe);
            checkbox = viewlike.findViewById(R.id.checkbox);
            mLinearLayout = viewlike.findViewById(R.id.mLinearLayout);

        }
    }


    public IngredientAdapter(Context mContext, ArrayList<IngredientModel> contactModelArrayList, RecipeProcess_Model recipeProcess_model1) {
        context = mContext;
        contactModels = contactModelArrayList;
        recipeProcess_model = recipeProcess_model1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredients_list_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            viewHolder.tv_gram.setText(contactModel.getIngred_quantity()+" "+contactModel.getIngred_unit());
            viewHolder.tv_recipe.setText(contactModel.getIngredients());
        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }


        viewHolder.checkbox.setTag(position);
        if (list.contains(contactModels.get(position).getId())) {
            viewHolder.checkbox.setChecked(true);
        } else {
           list.clear();
            viewHolder.checkbox.setChecked(false);
        }


        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    list.add(contactModels.get(position).getId());
                } else {
                    list.remove(contactModels.get(position).getId());
                }
               // Toast.makeText(context, "list " + list, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }


}
