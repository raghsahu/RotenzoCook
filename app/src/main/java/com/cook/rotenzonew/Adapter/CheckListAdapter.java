package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Model.CheckListData;
import com.cook.rotenzonew.Model.CheckListModel;
import com.cook.rotenzonew.R;
import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {
    public static List<CheckListData> checkListModelList;
    CheckListData checkListModel;
    public Context context;
    View viewlike;
    public static List<String> list = new ArrayList<>();
    MySelfIngreAdapter mySelfIngreAdapter;



    public CheckListAdapter(List<CheckListData> checkListModelArrayList,
                            FragmentActivity activity) {
        context = activity;
        checkListModelList = checkListModelArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recipe;
        LinearLayout mLinearLayout;
        CheckBox checkbox;
        RecyclerView recycler_my_ingre;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_recipe = viewlike.findViewById(R.id.tv_recipe);
            recycler_my_ingre = viewlike.findViewById(R.id.recycler_my_ingre);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_ingre_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (checkListModelList.size() > 0) {
            checkListModel = checkListModelList.get(position);
            viewHolder.tv_recipe.setText(checkListModel.getRecipName());
           //viewHolder.text2.setText(checkListModel.getIngred_quantity()+" "+checkListModel.getIngred_unit());


            mySelfIngreAdapter = new MySelfIngreAdapter(checkListModel.getIngredients(), context);
            viewHolder.recycler_my_ingre.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            viewHolder.recycler_my_ingre.addItemDecoration(new DividerItemDecoration(viewHolder.recycler_my_ingre.getContext(), DividerItemDecoration.VERTICAL));
            viewHolder.recycler_my_ingre.setAdapter(mySelfIngreAdapter);
            mySelfIngreAdapter.notifyDataSetChanged();




        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public int getItemCount() {
        return checkListModelList.size();
    }


}
