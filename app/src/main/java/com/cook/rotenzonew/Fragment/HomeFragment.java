package com.cook.rotenzonew.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.HomeNewRecipeAdapter;
import com.cook.rotenzonew.Adapter.SlidingImage_Adapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.BannerImage;
import com.cook.rotenzonew.Model.DataModel;
import com.cook.rotenzonew.Model.RecipeModel;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;

import com.cook.rotenzonew.Utils.APIClient;
import com.cook.rotenzonew.Utils.Api_Call;
import com.cook.rotenzonew.Utils.URLs;
import com.cook.rotenzonew.Utils.VolleySingleton;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

import static com.cook.rotenzonew.Utils.Base_Url.BaseUrl;
import static com.cook.rotenzonew.Utils.Base_Url.slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView, recyclerView2;
    ArrayList<DataModel> _listDataModel = listArray();
    HomeNewRecipeAdapter homeNewRecipeAdapter;
    private List<RecipeModel> deviceModelsList = new ArrayList<>();
    String id;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.sliderimages, R.drawable.sliderimages, R.drawable.sliderimages, R.drawable.sliderimages};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    View view;
    private String mStrUser_id;
    public ArrayList<BannerImage> hero = new ArrayList<>();

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        id= Shared_Pref.getProd_Id(getActivity());
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();
       // mFunSettingApiCall();
        mFunSettingApiCall1();

        LastViewRecipe();
        GetSliderImage();


//        recyclerView2.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
//        recyclerView2.addItemDecoration(new DividerItemDecoration(recyclerView2.getContext(), DividerItemDecoration.VERTICAL));
//
//        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView2.setLayoutManager(mLayoutManager2);
//        recyclerView2.setAdapter(new MyCustomAdapter(_listDataModel));


        return view;
    }

    private void GetSliderImage() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl+slider,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("banner_res", response.toString());
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("data");
                            for (int i = 0; i < heroArray.length(); i++) {
                                Log.d("<><><", heroArray.toString());
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                hero.add(i, new BannerImage(heroObject.getString("id"),
                                        heroObject.getString("image")
                                        ));

                                //adding the hero to herolist

                                //call benner
                                if (hero!=null){
                                    Banner_Call(hero);
                                }

                                Log.d("imagesize", "" + hero.size());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }



    private void LastViewRecipe() {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<RecipeModel> call = apiInterface.LastRecipe_Api(mStrUser_id);
        call.enqueue(new Callback<RecipeModel>() {
            @Override
            public void onResponse(Call<RecipeModel> call, retrofit2.Response<RecipeModel> response) {

                try{
                    if (response!=null){

                        Log.e("Last_rec", ""+response.body().getData().size());
                        if (response.body().getResult().equalsIgnoreCase("true")){

                            homeNewRecipeAdapter = new HomeNewRecipeAdapter(response.body().getData(), getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            //recyclerView2.addItemDecoration(new DividerItemDecoration(recyclerView2.getContext(), DividerItemDecoration.VERTICAL));
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerView2.setLayoutManager(linearLayoutManager);

                            recyclerView2.setAdapter(homeNewRecipeAdapter);

                        }else {
                            Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("recipe_last", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<RecipeModel> call, Throwable t) {

                Log.e("error_last",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void mFunSettingApiCall1() {

        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<RecipeModel> call = apiInterface.HomeRecipe_Api(id,mStrUser_id);
        call.enqueue(new Callback<RecipeModel>() {
            @Override
            public void onResponse(Call<RecipeModel> call, retrofit2.Response<RecipeModel> response) {

                try{
                    if (response!=null){
                        if (response.body().getResult().equalsIgnoreCase("true")){

                            homeNewRecipeAdapter = new HomeNewRecipeAdapter(response.body().getData(), getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            recyclerView.setAdapter(homeNewRecipeAdapter);

                        }else {
                            Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("recipe", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<RecipeModel> call, Throwable t) {

                //Log.e("error",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void mFunSettingApiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "RecipesByProductId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            System.out.println("check response  " + obj);

                            boolean responsedata = obj.getBoolean("result");
                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.getString("id");

                                    System.out.println("id " + id);
//
//                                    RecipeModel deviceModel = new RecipeModel(
//                                            jsonObject.getString("id"),
//                                            jsonObject.getString("menu_id"),
//                                            jsonObject.getString("product_id"),
//                                            jsonObject.getString("recip_name"),
//                                            jsonObject.getString("recip_kcal"),
//                                            jsonObject.getString("recip_time"),
//                                            jsonObject.getString("image")
//
//                                    );
                                   // deviceModelsList.add(deviceModel);
                                }

                                recyclerView.setAdapter(homeNewRecipeAdapter);
                            } else {
                                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
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

                        System.out.println("check response  " + error);

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", id);
                params.put("user_id", mStrUser_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



  /* private void getMenuReipe(String prod_id) {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<MenuRecipe_Model> call = apiInterface.MenuRecipe_Api(prod_id);
        call.enqueue(new Callback<MenuRecipe_Model>() {
            @Override
            public void onResponse(Call<MenuRecipe_Model> call, retrofit2.Response<MenuRecipe_Model> response) {

                try{

                    if (response!=null){
                        if (response.body().getResult().equalsIgnoreCase("true")){

                            recipeNavigationAdapter = new RecipeNavigationAdapter2(response.body().getData(),getActivity());
                            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
                            gridView.setLayoutManager(manager);
                            gridView.setAdapter(recipeNavigationAdapter);

                        }else {
                            Toast.makeText(getActivity(), "No list found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("recipe", e.getMessage());
                }


            }



            @Override
            public void onFailure(Call<MenuRecipe_Model> call, Throwable t) {

                Log.e("error",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/







    public ArrayList<DataModel> listArray() {

        ArrayList<DataModel> objList = new ArrayList<DataModel>();
        DataModel dm;

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.drawable.foodimg);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");

        dm.setImgSrc(R.drawable.foodimg);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");

        dm.setImgSrc(R.drawable.foodimg);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.mipmap.ic_launcher);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.mipmap.ic_launcher);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.mipmap.ic_launcher);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.mipmap.ic_launcher);
        objList.add(dm);

        dm = new DataModel();
        dm.setRestaurantName("Beef stew with  steamed potatoes");
        dm.setImgSrc(R.mipmap.ic_launcher);
        objList.add(dm);

        return objList;
    }

    private void Banner_Call(ArrayList<BannerImage> hero) {

//        for (int i = 0; i < IMAGES.length; i++)
//            ImagesArray.add(IMAGES[i]);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getActivity(), hero));

        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        NUM_PAGES = hero.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

}
