package com.cook.rotenzonew;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cook.rotenzonew.Model.DataModel;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

    ArrayList<DataModel> listDataModel = new ArrayList<DataModel>();

    public MyCustomAdapter(ArrayList<DataModel> _listDataModel) {
        this.listDataModel = _listDataModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardinterface, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DataModel objDataModel = listDataModel.get(position);

        holder.txtRestaurantName.setText(objDataModel.getRestaurantName().toString());
        holder.txtDesc.setText(objDataModel.getDesc().toString());
        holder.imgRestaurant.setImageResource(objDataModel.getImgSrc());
    }

    @Override
    public int getItemCount() {
        return listDataModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtRestaurantName, txtDesc;
        ImageView imgRestaurant;

        public MyViewHolder(View view) {
            super(view);
            txtRestaurantName = (TextView) view.findViewById(R.id.txtRestaurantName);
            txtDesc = (TextView) view.findViewById(R.id.txtDesc);
            imgRestaurant = (ImageView) view.findViewById(R.id.imgRestaurant);
        }
    }
}

