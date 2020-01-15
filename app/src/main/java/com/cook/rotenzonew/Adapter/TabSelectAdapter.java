package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.cook.rotenzonew.Fragment.HomeFragment;
import com.cook.rotenzonew.Fragment.ProductsFragment;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.ProductHomelist;
import com.cook.rotenzonew.Model.ProductsByCatId_Model;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 05/12/2019.
 */
public class TabSelectAdapter extends RecyclerView.Adapter<TabSelectAdapter.ViewHolder> {
    public List<ProductHomelist> drList;
    public ProductHomelist drData;
   // Context context;
    FragmentActivity fx;
    AdapterCallback mAdapterCallback;
    private int row_index;
    Drawable bacground;

    public TabSelectAdapter(ArrayList<ProductHomelist> drListdata, FragmentActivity ctx) {
        drList = drListdata;
        this.fx = ctx;

    }

//    public TabSelectAdapter(AdapterCallback callback) {
//        this.mAdapterCallback = callback;
//    }

    public TabSelectAdapter(ProductsFragment fragment) {
        try {
            this.mAdapterCallback = ((AdapterCallback) fragment);
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement AdapterCallback.");
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tab, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            mAdapterCallback = ((AdapterCallback) fx);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

        if (drList.size() > 0) {
            drData = drList.get(position);
            holder.title.setText(drData.getCat_name());

            Glide.with(fx)
                    .load(URLs.SETTING_PRODUCT_ICON + drData.getIcon())
                    .into(holder.imageview);

            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cat_id=drList.get(position).getId();
                    try {
                        mAdapterCallback.onMethodCallback(cat_id);
                    } catch (ClassCastException exception) {
                        // do something
                        Log.e("tab_sel_error", exception.toString());
                    }

                    row_index=position;
                    notifyDataSetChanged();

                }
            });


            if(row_index==position){
              //  holder.imageview.setBackgroundResource(R.drawable.red_round);
              // holder.imageview.setBackgroundColor(Color.parseColor("#a71525"));

                holder.ll_tab_bg.setBackgroundResource(R.drawable.red_round);

            }
            else
            {
              //  holder.imageview.setBackgroundResource(R.drawable.round_image);
               // holder.imageview.setBackgroundColor(Color.parseColor("#f0eded"));
              //  holder.ll_tab_bg.setImageDrawable(fx.getResources().getDrawable(R.drawable.round_image));
                holder.ll_tab_bg.setBackgroundResource(R.drawable.round_image);
            }

        }
    }


    public interface AdapterCallback {
        void onMethodCallback(String cat_id);
    }


    @Override
    public int getItemCount() {
        //return 1;
        return drList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageview;
        public LinearLayout mLinearLayout,ll_tab_bg;


        public ViewHolder(View parent) {
            super(parent);

            mLinearLayout = parent.findViewById(R.id.ll_tab);
            ll_tab_bg = parent.findViewById(R.id.ll_tab_bg);
            title = parent.findViewById(R.id.title);
            imageview = parent.findViewById(R.id.image);


        }
    }




}
