package com.cook.rotenzonew.Adapter;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Model.DetailProductList;
import com.cook.rotenzonew.Model.ProductHomelist;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> itemList;
    private final static int ProductHomelist = 1;
    private final static int DetailProductList = 2;
    public static String Product_id;
    Fragment fragment;
    public ProductAdapter(List<Object> itemList ) {
        this.itemList = itemList;


    }

    @Override
    public int getItemViewType(int position) {

        if (itemList.get(position) instanceof com.cook.rotenzonew.Model.ProductHomelist) {
            return ProductHomelist;

        } else if (itemList.get(position) instanceof com.cook.rotenzonew.Model.DetailProductList){
            return DetailProductList;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case ProductHomelist:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_adapter, viewGroup, false);
                return new ProductHomeViewHolder(view);
            case DetailProductList:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_detail, viewGroup, false);
                return new DetailProductListHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int type = viewHolder.getItemViewType();
        switch (type) {
            case ProductHomelist:
                final ProductHomeViewHolder topSpecialViewHolder = (ProductHomeViewHolder) viewHolder;
                final ProductHomelist topHomeSpeciallist = (ProductHomelist) itemList.get(i);


                final Context context = topSpecialViewHolder.top_image_icon.getContext(); //<----- Add this line
                Picasso.with(context).load(URLs.SETTING_PRODUCT_ICON + topHomeSpeciallist.getIcon()).into(topSpecialViewHolder.top_image_icon);
                topSpecialViewHolder.product_image_name.setText(topHomeSpeciallist.getCat_name());

               topSpecialViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         int position=topSpecialViewHolder.getAdapterPosition();

                     //   Intent intent = new Intent(context, ActivityDoctorTopSpecList.class);
                     //   intent.putExtra("id",topHomeSpeciallist.getId());
                     //   context.startActivity(intent);

                        Log.e("id",topHomeSpeciallist.getId());
                        notifyDataSetChanged();
                        Product_id=topHomeSpeciallist.getId();




                   //     Toast.makeText(context, "pos "+position, Toast.LENGTH_SHORT).show();



                    }
                });



                break;


            case DetailProductList:
                DetailProductListHolder adViewHolder = (DetailProductListHolder) viewHolder;
                DetailProductList advertise = (DetailProductList) itemList.get(i);
               // adViewHolder.ad_image.setImageResource(advertise.getImageAd());
                adViewHolder.model.setText(advertise.getProduct_name());


                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ProductHomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView top_image_icon;
        private TextView product_image_name;
        private LinearLayout mLinearLayout;

        public ProductHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout=itemView.findViewById(R.id.mLinearLayout);
            top_image_icon = itemView.findViewById(R.id.product_image);
            product_image_name = itemView.findViewById(R.id.textview);
        }


    }

    public class DetailProductListHolder extends RecyclerView.ViewHolder{
        private ImageView imageview;
        TextView model,type,shape,capacity;
        public DetailProductListHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            model=itemView.findViewById(R.id.model);
            type=itemView.findViewById(R.id.type);
            shape=itemView.findViewById(R.id.shape);
            capacity=itemView.findViewById(R.id.capacity);


        }
    }
}
