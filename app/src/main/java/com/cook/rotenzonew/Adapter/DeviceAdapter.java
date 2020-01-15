package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Fragment.ProductsFragment;
import com.cook.rotenzonew.Fragment.Rate_Review_fragment;
import com.cook.rotenzonew.Model.DeviceModel;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Ravindra Birla on 20/09/2019.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<DeviceModel> drList;
    private DeviceModel drData;
    private Context context;


    public DeviceAdapter(List<DeviceModel> drListdata, Context ctx) {
        drList = drListdata;
        this.context = ctx;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_selection_adapter, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new DeviceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {
            drData = drList.get(position);
            holder.mTextDeviceid.setText(drData.getProduct_brand());
            holder.mTextProduct.setText(drData.getProduct_name());
            Picasso.with(context).load(URLs.SETTING_BASE_URL+drData.getImage()).into(holder.img_profile);


            holder.ll_device.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.ll_card_bg.setBackgroundResource(R.drawable.btn_bg_rate);

                    Fragment fragment5 = new ProductsFragment();
                    FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.containt_main_frame, fragment5);
                    transaction.addToBackStack(null);
                    transaction.commit();

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
        public TextView mTextProduct, mTextDeviceid;
        public ImageView img_profile;
        public LinearLayout ll_item,ll_device,ll_card_bg;


        public ViewHolder(View parent) {
            super(parent);

            img_profile=parent.findViewById(R.id.product_image);
            mTextProduct=parent.findViewById(R.id.mTextProduct);
            mTextDeviceid=parent.findViewById(R.id.mTextDeviceid);
            ll_device=parent.findViewById(R.id.ll_device);
            ll_card_bg=parent.findViewById(R.id.ll_card_bg);

        }
    }
}
