package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Model.FavouriteModel;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

        List<FavouriteModel> listDataModel = new ArrayList<FavouriteModel>();
        Context context;
    String mStrUser_id;

//        public FavoriteAdapter(ArrayList<DataModel> _listDataModel) {
//            this.listDataModel = _listDataModel;
//        }

    public FavoriteAdapter(List<FavouriteModel> favouriteModelArrayList,
                           FragmentActivity activity) {
        this.listDataModel=favouriteModelArrayList;
        this.context=activity;

    }

    @Override
        public FavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_adapter, parent, false);
            FavoriteAdapter.MyViewHolder holder = new FavoriteAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(FavoriteAdapter.MyViewHolder holder, final int position) {

            final FavouriteModel objDataModel = listDataModel.get(position);
            User user = SharedPrefManager.getInstance(context).getUser();
            mStrUser_id=user.getId();

            holder.title.setText(objDataModel.getRecip_name());
            holder.semititle.setText(objDataModel.getRecip_time()+" MIN "+"| "+objDataModel.getRecip_kcal()+" KACL");

            Picasso.with(context)
                    .load(URLs.RECIPE_URL + objDataModel.getImage())
                    .into(holder.image);

            holder.iv_red_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Recipe_id=objDataModel.getRecipe_id();

                    AddtoHeart(mStrUser_id,Recipe_id,position);
                }
            });

        }

    private void AddtoHeart(final String mStrUser_id, final String recipe_id, final int position) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "add_to_favrt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("favourite respose", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String favourite=jsonObject.getString("msg");

                            Log.e("favourite status ",favourite);
                            if (result.equalsIgnoreCase("true")) {


                                if (favourite.equals("Favrt!!"))
                                {
                                    Toast.makeText(context, R.string.add_fav, Toast.LENGTH_SHORT).show();
                                }else if (favourite.equals("Unfavrt"))
                                {
                                listDataModel.remove(position);
                                notifyDataSetChanged();
                                    Toast.makeText(context, R.string.remove_fav, Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, "eror "+error, Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", mStrUser_id);
                params.put("recipe_id", recipe_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
        public int getItemCount() {
            return listDataModel.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title,semititle;
            ImageView image,iv_red_heart,iv_white_heart;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                semititle = (TextView) view.findViewById(R.id.semititle);
                image = (ImageView) view.findViewById(R.id.image);
                iv_red_heart = (ImageView) view.findViewById(R.id.iv_red_heart);
                iv_white_heart = (ImageView) view.findViewById(R.id.iv_white_heart);

            }
        }
    }




