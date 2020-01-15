package com.cook.rotenzonew.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Adapter.DeviceDetailAdapter;
import com.cook.rotenzonew.Adapter.TabSelectAdapter;
import com.cook.rotenzonew.Fragment.CheckListFragment;
import com.cook.rotenzonew.Fragment.FavouriteFragment;
import com.cook.rotenzonew.Fragment.HomeFragment;
import com.cook.rotenzonew.Fragment.ProductsFragment;
import com.cook.rotenzonew.Fragment.RecipeFragment;
import com.cook.rotenzonew.Fragment.SettingsFragment;
import com.cook.rotenzonew.Fragment.TipsFragment;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.DataModel;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.cook.rotenzonew.Model.ProductsByCatId_Model;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;

import com.cook.rotenzonew.Utils.LocaleHelper;
import com.cook.rotenzonew.Utils.URLs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabSelectAdapter.AdapterCallback{
    DrawerLayout drawer;
    LinearLayout mLinearLayoutFavorite;
    NavigationView navigationView;
    HomeFragment homefragment;
    RecyclerView recyclerView, recyclerView2;
    ArrayList<DataModel> _listDataModel = listArray();
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.sliderimages, R.drawable.sliderimages, R.drawable.sliderimages, R.drawable.sliderimages};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private Locale myLocale;
    BottomNavigationView navigation;
    public static TextView toolbar_title;
    Context context;
    String mStrUser_id;

    List<ProductsByCatId_Model> product_modelList = new ArrayList<>();
      RecyclerView  recycler_cat_pro;

    boolean doubleBackToExitPressedOnce ;
    private Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
     boolean viewIsAtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguageForApp(Shared_Pref.getLanguage(this));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        context=MainActivity.this;
        final User user = SharedPrefManager.getInstance(MainActivity.this).getUser();
        mStrUser_id=user.getId();

      //  Toast.makeText(this, "lll "+Shared_Pref.getLanguage(this), Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setForegroundGravity(Gravity.RIGHT);
        }
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        toolbar_title = findViewById(R.id.toolbar_title);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.nav_icon, getApplicationContext().getTheme());
        toggle.setHomeAsUpIndicator(drawable);


        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        setUpHomeFragment();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

    }

    public void setLanguageForApp(String lang){

        LocaleHelper.setLocale(MainActivity.this, lang);

        Resources activityRes = getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(lang);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.product:

                    fragment=new ProductsFragment();
                    toolbar_title.setText(R.string.product);
                   // item.setIcon(R.drawable.products_icon);
                    loadFragment(fragment);
                    return true;
                case R.id.recipebook:
                    fragment=new RecipeFragment();
                    toolbar_title.setText(R.string.menu);
                    //item.setIcon(R.drawable.recipes_icon);
                    loadFragment(fragment);
                  //  toolbar.setTitle("My Gifts");
                    return true;
                case R.id.favourite:
                    fragment = new FavouriteFragment();
                    toolbar_title.setText(R.string.favourite);
                    loadFragment(fragment);
                  //  toolbar.setTitle("Cart");
                    return true;
                case R.id.navigation_checklist:
                    toolbar_title.setText(R.string.shopping_list);
                   // toolbar.setTitle("Profile");
                    fragment = new CheckListFragment();
                    loadFragment(fragment);

                    return true;
            }
            return false;
        }
    };




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containt_main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        viewIsAtHome = false;
    }

    public void setUpHomeFragment(){
        homefragment=new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.containt_main_frame, homefragment);
        ft.addToBackStack(null);
        ft.commit();
        viewIsAtHome = true;
    }


    public void  displaySelectedScreen(int itemId)
    {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.product:
                fragment=new ProductsFragment();

               // loadFragment(fragment);
                break;
            case R.id.recipe:

               // fragment = new FavouriteFragment();
                break;
            case R.id.settings:

                fragment = new SettingsFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.containt_main_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containt_main_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
    }


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



    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_again), Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);


        //********************
//        if (drawer.isDrawerOpen(GravityCompat.END)) {
//            drawer.closeDrawer(GravityCompat.END);
//       }
//       else if (!viewIsAtHome){
//                setUpHomeFragment();
//        }
//        else {
//            new AlertDialog.Builder(this)
//                    .setMessage(getString(R.string.back_press))
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            MainActivity.this.finish();
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//        }




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                return true;
           // case R.id.action_settings:
               // return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //displaySelectedScreen(item.getItemId());
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.product) {
            toolbar_title.setText(R.string.product);
            fragment = new ProductsFragment();
            viewIsAtHome = false;

        } else if (id == R.id.recipe) {
            toolbar_title.setText(R.string.recipebook);
            fragment=new RecipeFragment();
            viewIsAtHome = false;

        } else if (id==R.id.favourite)
        {
            toolbar_title.setText(R.string.favourite);
            fragment=new FavouriteFragment();
            viewIsAtHome = false;

        }
        else if (id == R.id.shopping) {
            toolbar_title.setText(R.string.shopping_list);
            fragment = new CheckListFragment();
            viewIsAtHome = false;

        } else if (id == R.id.tips) {
            toolbar_title.setText(R.string.tips);
            fragment = new TipsFragment();
            viewIsAtHome = false;

        }else if (id == R.id.settings) {
            toolbar_title.setText(R.string.action_settings);
            fragment = new SettingsFragment();
            viewIsAtHome = false;
        }else if (id == R.id.e_shop) {

           OpenWebsiteE_shop();
            viewIsAtHome = false;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containt_main_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void OpenWebsiteE_shop() {
        String news_url="https://next.mn/brand/rotenzo/";

        if (!news_url.startsWith("http://") && !news_url.startsWith("https://")){
            String  url = "http://" + news_url;

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_url));
            context.startActivity(browserIntent);
        }
    }


    @Override
    public void onMethodCallback(String cat_id) {

        String Cat_Tab_id=cat_id;
        Log.e("Cat_Tab_id_main", ""+Cat_Tab_id);

        ProductsFragment fragment = new ProductsFragment();
        //((ProductsFragment) fragment).productsByCatId(Cat_Tab_id,context);
       productsByCatId(Cat_Tab_id);

      recycler_cat_pro = findViewById(R.id.recycler_cat_pro);
    }

    private void productsByCatId(final String cat_tab_id) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "productsByCatId",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.e("Response_Cat_Pro_main", response);

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

                                    DeviceDetailAdapter adapter_product = new DeviceDetailAdapter(product_modelList, (FragmentActivity) context);
                                    recycler_cat_pro.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                                    recycler_cat_pro.addItemDecoration(new DividerItemDecoration(recycler_cat_pro.getContext(), DividerItemDecoration.VERTICAL));
                                    recycler_cat_pro.setAdapter(adapter_product);
                                    adapter_product.notifyDataSetChanged();

//                                    if (getActivity() != null) {
//                                        if (product_modelList.isEmpty()) {
//                                            Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }

                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
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
                    params.put("cat_id", cat_tab_id);
                    params.put("user_id", mStrUser_id);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            //adding the string request to request queue
            requestQueue.add(stringRequest);

        }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

}
