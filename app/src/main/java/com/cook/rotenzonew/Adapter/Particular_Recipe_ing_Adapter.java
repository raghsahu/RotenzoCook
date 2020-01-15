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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cook.rotenzonew.Model.Particular_Recipe_Ingredient_Model;
import com.cook.rotenzonew.Model.RecipeProcess_Model;
import com.cook.rotenzonew.R;
import java.util.ArrayList;
import java.util.List;


public class Particular_Recipe_ing_Adapter extends RecyclerView.Adapter<Particular_Recipe_ing_Adapter.ViewHolder> {
    public static ArrayList<Particular_Recipe_Ingredient_Model> m_recipe_ing_list;
    Particular_Recipe_Ingredient_Model particular_recipe_ingredient_model;
    RecipeProcess_Model recipeProcess_model;
    public Context context;
    View viewlike;
    public static List<String> list = new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recipe, tv_gram;
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


    public Particular_Recipe_ing_Adapter(List<Particular_Recipe_Ingredient_Model> particular_recipe_ingredient_modelArrayList, FragmentActivity fx) {

        m_recipe_ing_list = (ArrayList<Particular_Recipe_Ingredient_Model>) particular_recipe_ingredient_modelArrayList;
        context = fx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredients_list_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (m_recipe_ing_list.size() > 0) {
            particular_recipe_ingredient_model = m_recipe_ing_list.get(position);
            viewHolder.tv_gram.setText(particular_recipe_ingredient_model.getIngred_quantity());
            viewHolder.tv_recipe.setText(particular_recipe_ingredient_model.getIngredients());
        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }


        viewHolder.checkbox.setTag(position);
        if (list.contains(m_recipe_ing_list.get(position).getId())) {
            viewHolder.checkbox.setChecked(true);
        } else {
            viewHolder.checkbox.setChecked(false);
        }


        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    list.add(m_recipe_ing_list.get(position).getId());
                } else {
                    list.remove(m_recipe_ing_list.get(position).getId());
                }
               // Toast.makeText(context, "list " + list, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_recipe_ing_list.size();
    }


}
