package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.io.Serializable;
import java.util.List;

public class HomeNewRecipeAdapter extends RecyclerView.Adapter<HomeNewRecipeAdapter.ViewHolder> {
    private List<RecipeDetailsModelData> drList;
    private RecipeDetailsModelData drData;
    private Context context;
   LinearLayout mLinearLayout;

    public HomeNewRecipeAdapter(List<RecipeDetailsModelData> drListdata, Context ctx) {
        drList = drListdata;
        this.context = ctx;
    }

    @NonNull
    @Override
    public HomeNewRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout_adapter, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new HomeNewRecipeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewRecipeAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {
            drData = drList.get(position);
            holder.txtRestaurantName.setText(drData.getRecipName());
          //  holder.mTextProduct.setText(drData.getProduct_name());
            if (!drData.getImage().isEmpty()) {
                Picasso.with(context).load(URLs.RECIPE_URL + drData.getImage())
                        .placeholder(R.drawable.food_item)
                        .into(holder.image);

            }

            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drData = drList.get(position);
                    String recipe_id=drData.getId();
                    Log.e("rrrepi_id", recipe_id);

                    Fragment fragment5 = new Recipe_Detail();
                    Bundle args = new Bundle();
                    args.putString("recipe_id", drList.get(position).getId());
                    args.putSerializable("RecipeDetailsModelData", (Serializable) drList.get(position));
                    fragment5.setArguments(args);
                    FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.containt_main_frame, fragment5);
                    transaction.addToBackStack(null);
                    transaction.commit();

                   // Toast.makeText(context, " "+recipe_id, Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        //return 1;
        return drList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRestaurantName;
        public ImageView image;
        public LinearLayout mLinearLayout;


        public ViewHolder(View parent) {
            super(parent);

           mLinearLayout=parent.findViewById(R.id.mLinearLayout);
            image=parent.findViewById(R.id.image);
            txtRestaurantName=parent.findViewById(R.id.txtRestaurantName);
          //  mTextDeviceid=parent.findViewById(R.id.mTextDeviceid);



        }
    }
}
