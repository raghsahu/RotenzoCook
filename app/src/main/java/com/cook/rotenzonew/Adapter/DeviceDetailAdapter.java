package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.content.res.Resources;
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

import com.bumptech.glide.Glide;
import com.cook.rotenzonew.Fragment.HomeFragment;
import com.cook.rotenzonew.Fragment.ProductsFragment;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.ProductsByCatId_Model;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;

import java.util.List;

public class DeviceDetailAdapter extends RecyclerView.Adapter<DeviceDetailAdapter.ViewHolder> {
    public List<ProductsByCatId_Model> drList;
    public ProductsByCatId_Model drData;
    // Context context;
    //private Session session;
    private String user_id;
    FragmentActivity fx;
    ProductsFragment currentfragment;

    public DeviceDetailAdapter(List<ProductsByCatId_Model> drListdata, FragmentActivity ctx) {
        drList = drListdata;
        this.fx = ctx;
    }

    @NonNull
    @Override
    public DeviceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_detail, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new DeviceDetailAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DeviceDetailAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {
           // Resources res = context.getResources();
            drData = drList.get(position);
            holder.productname.setText(drData.getProduct_name());
            holder.model.setText(drData.getModel());
            holder.shape.setText(drData.getShape());
            holder.type.setText(drData.getType());
            holder.capacity.setText(drData.getCapacity());

//            Picasso.with(fx).
//                    load(URLs.SETTING_BASE_URL + drData.getImage())
//                    .into(holder.imageview);

            Glide.with(fx)
                    .load(URLs.SETTING_BASE_URL + drData.getImage())
                    .into(holder.imageview);

            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drData = drList.get(position);

                   try {
                       String id = drData.getId();
                       Shared_Pref.setProd_Id(fx, id);

                       String prod_id = Shared_Pref.getProd_Id(fx);

                      // String staticvalue="your";
                       HomeFragment fragment=new HomeFragment();
                      /* Bundle args = new Bundle();
                       args.putString("id",prod_id);*/
                      // fragment.setArguments(args);
                       FragmentTransaction transaction = fx.getSupportFragmentManager().beginTransaction();
                      // transaction.replace(R.id.containt_main_frame,fragment,staticvalue);
                       transaction.replace(R.id.containt_main_frame,fragment);
                       transaction.addToBackStack(null);
                       transaction.commit();

                   }
                   catch (Exception e)
                   {
                       Log.e("check exception",e.getMessage());

                   }



                }
            });


        }
    }
    private void loadFragment(Fragment fragment ,String id_model) {

        FragmentTransaction transaction = fx.getSupportFragmentManager().beginTransaction();
       // transaction.replace(R.id.containt_main_frame, fragment);
        transaction.replace(R.id.containt_main_frame,fragment,id_model);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        //return 1;
        return drList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productname, model, type, shape, capacity;
        public ImageView imageview;
        public LinearLayout mLinearLayout;


        public ViewHolder(View parent) {
            super(parent);

            mLinearLayout = parent.findViewById(R.id.mLinearLayout);
            imageview = parent.findViewById(R.id.imageview);
            productname = parent.findViewById(R.id.productname);
            model = parent.findViewById(R.id.model);
            type = parent.findViewById(R.id.type);
            shape = parent.findViewById(R.id.shape);
            capacity = parent.findViewById(R.id.capacity);

        }
    }











}
