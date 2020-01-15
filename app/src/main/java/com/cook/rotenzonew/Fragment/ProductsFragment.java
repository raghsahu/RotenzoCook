package com.cook.rotenzonew.Fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.DeviceDetailAdapter;

import com.cook.rotenzonew.Adapter.TabSelectAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.ProductHomelist;
import com.cook.rotenzonew.Model.ProductsByCatId_Model;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements TabLayout.OnTabSelectedListener , TabSelectAdapter.AdapterCallback{
    String imageicon;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    ProductHomelist productHomelist;
    ArrayList<ProductHomelist> categoryLists = new ArrayList<>();
    private List<String> cat_menu_id = new ArrayList<>();
    private List<String> image_pos_id = new ArrayList<>();
    TabLayout.Tab last_tab;
     int row_index;
    Drawable background1;
    Boolean tab_select=false;

    DeviceDetailAdapter adapter_product;
    RecyclerView recycler_cat_pro,recycler_tab;

    TabSelectAdapter tabSelectAdapter;
    Context context;
    String mStrUser_id;


    ImageView image;
     List<ProductsByCatId_Model> product_modelList = new ArrayList<>();


    public ProductsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tabSelectAdapter = new TabSelectAdapter(this); // this class implements callback
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        View view = inflater.inflate(R.layout.setting_view, container, false);
        getActivity().setTitle(R.string.product);
        // viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //  setupViewPager(viewPager);
        context=getActivity();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        recycler_cat_pro = view.findViewById(R.id.recycler_cat_pro);
        recycler_tab = view.findViewById(R.id.recycler_tab);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TopSpecialistDrList();

        //this tab use only one time, when activity open
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                background1=tab.getCustomView().findViewById(R.id.image).getBackground();
                int position = tab.getPosition();
                String getcat_id = cat_menu_id.get(tab.getPosition());
                String img=image_pos_id.get(tab.getPosition());

                row_index=position;

               // Drawable background = image.getBackground();
                if (background1 instanceof GradientDrawable) {
                    ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                }

                //**********************************

//                if(row_index==position){
//                    if (background1 instanceof GradientDrawable) {
//                            ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//                        }
//                }
//                else
//                {
//                    if (background1 instanceof GradientDrawable) {
//                        ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
//                    }
//                }
                //*************************
//                if (tab.getPosition()==0){
//                    tab.isSelected();
//
//                    if (tab.isSelected()){
//                        if (background1 instanceof GradientDrawable) {
//                            ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//                        }
//
//                        Log.e("tab_po", ""+tab.getPosition());
//                    }else {
//                        if (background1 instanceof GradientDrawable) {
//                            ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
//                        }
//                    }
//
//                }else {
//
//                }


//-------------------------------------------
//                if (position==0){
//                    if (tab.isSelected()){
//                        Toast.makeText(getActivity(), "ssss", Toast.LENGTH_SHORT).show();
//                        if (background1 instanceof GradientDrawable) {
//                            ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//                        }
//                }
//            }

                //***********************************************

//                for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                    if (i == position) {
//                       // tabLayout.getTabAt(i).getCustomView().setBackgroundColor(Color.parseColor("#000000"));
//                        Toast.makeText(getActivity(), "sel_pos", Toast.LENGTH_SHORT).show();
//                     Drawable background = image.getBackground();
//                     if (background instanceof GradientDrawable) {
//                         ((GradientDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//                     }
//
//                    } else {
//                        Toast.makeText(getActivity(), "se_not_pos", Toast.LENGTH_SHORT).show();
//                        Drawable background = image.getBackground();
//                        if (background instanceof GradientDrawable) {
//                            ((GradientDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
//                        }
//
//                    }
//                }


                System.out.println("product id is " + getcat_id);
               productsByCatId(getcat_id, context);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Drawable background1=tab.getCustomView().findViewById(R.id.image).getBackground();

                 Drawable background = tab.getCustomView().getBackground();
                if (background1 instanceof GradientDrawable) {
                    ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));

                }
                //Toast.makeText(getActivity(), "unselect", Toast.LENGTH_SHORT).show();
                //****************************************

               // int tabIconColor = ContextCompat.getColor(getActivity(), R.color.greycolor);
              //  tab.getCustomView().findViewById(R.id.image).setBackgroundColor(tabIconColor)

/*
                Drawable background = image.getBackground();
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                }*/


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                /*Drawable background = image.getBackground();
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(ContextCompat.getColor(getActivity(), R.color.greycolor));
                }*/

               // Toast.makeText(getActivity(), "reselect", Toast.LENGTH_SHORT).show();
                //***********************************
                Drawable background1=tab.getCustomView().findViewById(R.id.image).getBackground();

                // Drawable background = tab.getCustomView().getBackground();
                if (background1 instanceof GradientDrawable) {
                    ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

                }


            }
        });

//        if(row_index==0){
//            if (background1 instanceof GradientDrawable) {
//                ((GradientDrawable) background1).setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//            }
//        }



        return view;


    }


    public void productsByCatId(final String getcat_id, final Context context) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "productsByCatId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response_Cat_Pro", response);

                        try {
                            product_modelList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String cat_id = jsonObject1.getString("cat_id");
                                    String product_brand = jsonObject1.getString("product_brand");
                                    String product_name = jsonObject1.getString("product_name");
                                    String image = jsonObject1.getString("image");
                                    String video = jsonObject1.getString("video");
                                    String site_link = jsonObject1.getString("site_link");
                                    String model = jsonObject1.getString("model");
                                    String type = jsonObject1.getString("type");
                                    String shape = jsonObject1.getString("shape");
                                    String capacity = jsonObject1.getString("capacity");

                                    product_modelList.add(i, new ProductsByCatId_Model(id, cat_id, product_brand, product_name, image,
                                            video, site_link,model,type,shape,capacity));

                                }

                                DeviceDetailAdapter adapter_product = new DeviceDetailAdapter(product_modelList, getActivity());
                                recycler_cat_pro.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                                recycler_cat_pro.addItemDecoration(new DividerItemDecoration(recycler_cat_pro.getContext(), DividerItemDecoration.VERTICAL));
                                recycler_cat_pro.setAdapter(adapter_product);
                                adapter_product.notifyDataSetChanged();

                                if (getActivity() != null) {
                                    if (product_modelList.isEmpty()) {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
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
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id", getcat_id);
                params.put("user_id", mStrUser_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }




    private void TopSpecialistDrList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "getCategories",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            System.out.println("check response  " + obj);

                            boolean responsedata = obj.getBoolean("result");
                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String title = jsonObject.getString("cat_name");
                                    imageicon = URLs.SETTING_PRODUCT_ICON + jsonObject.getString("icon");

                                    productHomelist = new ProductHomelist(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("cat_name"),
                                            jsonObject.getString("icon")

                                    );

                                    categoryLists.add(productHomelist);

                                    cat_menu_id.add(productHomelist.getId());
                                    image_pos_id.add(productHomelist.getIcon());

                                    try {

                                        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabItemView2(imageicon, title)));
                                    }catch (Exception e){

                                    }


                                }

                                //***************set horizontal recycler
                                tabSelectAdapter = new TabSelectAdapter(categoryLists, getActivity());
                                recycler_tab.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                                recycler_tab.setAdapter(tabSelectAdapter);
                                tabSelectAdapter.notifyDataSetChanged();


                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", mStrUser_id);
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private View createTabItemView2(String imgUri, String text) {
        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_tab, null, false);
        TextView title = headerView.findViewById(R.id.title);
        image = headerView.findViewById(R.id.image);
        Picasso.with(getActivity()).load(imgUri).into(image);
        title.setText(text);
        return headerView;
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onMethodCallback(String cat_id) {

        String Cat_Tab_id=cat_id;
        Log.e("Cat_Tab_id", ""+Cat_Tab_id);
    }



}














