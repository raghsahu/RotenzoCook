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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Model.CheckListData;
import com.cook.rotenzonew.Model.MyIngredient;
import com.cook.rotenzonew.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 05/12/2019.
 */
public class MySelfIngreAdapter extends RecyclerView.Adapter<MySelfIngreAdapter.ViewHolder> {
    public static List<MyIngredient> checkListModelList;
    MyIngredient checkListModel;
    public Context context;
    View viewlike;
    public static List<String> list = new ArrayList<>();




    public MySelfIngreAdapter(List<MyIngredient> checkListModelArrayList,
                              Context activity) {
        context = activity;
        checkListModelList = checkListModelArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recipe,tv_gram;
        LinearLayout mLinearLayout;
        CheckBox checkbox;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_recipe = viewlike.findViewById(R.id.tv_recipe);
            tv_gram = viewlike.findViewById(R.id.tv_gram);
            checkbox = viewlike.findViewById(R.id.checkbox);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checklist_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (checkListModelList.size() > 0) {
            checkListModel = checkListModelList.get(position);
            viewHolder.tv_recipe.setText(checkListModel.getIngredients());
           viewHolder.tv_gram.setText(checkListModel.getIngredQuantity()+" "+checkListModel.getIngredUnit());



        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }

        // viewHolder.checkbox.setTag(position);
       /* if (list.contains(checkListModelList.get(position).getId())) {
            viewHolder.checkbox.setChecked(true);
        } else {
            viewHolder.checkbox.setChecked(false);
        }*/

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    // list.add(checkListModelList.get(position).getId());
                } else {
                    // list.remove(checkListModelList.get(position).getId());
                }
                //  Toast.makeText(context, "list " + list, Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return checkListModelList.size();
    }


}
