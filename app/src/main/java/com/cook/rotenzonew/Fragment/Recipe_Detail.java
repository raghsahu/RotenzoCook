package com.cook.rotenzonew.Fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.RecipeDetailsModelData;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.AlertReceiver;
import com.cook.rotenzonew.Utils.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recipe_Detail extends Fragment {
    int  mHour, mMinute;
    View view;
    String mStrUser_id;
    private TabLayout tabLayout;
    private LinearLayout container;
    List<RecipeDetailsModelData> recipeDetailsModelDataList = new ArrayList<>();
    ImageView imageView;
    String menu_id;
    FloatingActionButton floatingActionButton;
    TextView recipename, title, totalmin, awaymin, calories, protien;
    String Recipe_id;
    LinearLayout mLinearTimer, linearlayout;

    String recipe_id_fromHomePage;
    RecipeDetailsModelData recipeDetailsModelData;

    public Recipe_Detail() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_recipe__detail, container, false);
        imageView = view.findViewById(R.id.image);
        recipename = view.findViewById(R.id.recipename);
        title = view.findViewById(R.id.title);
        totalmin = view.findViewById(R.id.totalmin);
        awaymin = view.findViewById(R.id.awaymin);
        calories = view.findViewById(R.id.calories);
        protien = view.findViewById(R.id.protien);
        mLinearTimer = view.findViewById(R.id.mLinearTimer);
        floatingActionButton = view.findViewById(R.id.heart);

        if (getArguments() != null) {

                recipeDetailsModelData = (RecipeDetailsModelData) getArguments().getSerializable("RecipeDetailsModelData");
                recipename.setText(recipeDetailsModelData.getRecipName());
                title.setText(recipeDetailsModelData.getRecipe_Advantage());
                Picasso.with(getActivity())
                        .load(URLs.RECIPE_URL + recipeDetailsModelData.getImage())
                       // .error(R.drawable.foodimg)
                        .placeholder(R.drawable.foodimg)
                        .into(imageView);
                totalmin.setText(recipeDetailsModelData.getRecipTime());
                calories.setText(recipeDetailsModelData.getRecipKcal());
                protien.setText(recipeDetailsModelData.getRecipe_Protein());
                Recipe_id = recipeDetailsModelData.getId();

                //Log.e("reccc_id", Recipe_id);
                //Log.e("reccM_id", ""+getArguments().getString("recipe_id"));

            }


         User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();


        AddLastView(mStrUser_id,Recipe_id);//****lastview recipe

         floatingActionButton.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {
                if (!clicked) {
                    floatingActionButton.setImageResource(R.drawable.like_hert);

                    AddtoHeart(mStrUser_id,Recipe_id);

                    clicked = true;
                } else {
                    floatingActionButton.setImageResource(R.drawable.ic_like_red);
                    clicked = false;
                    AddtoHeart(mStrUser_id,Recipe_id);


                }
            }
        });


        mLinearTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                updateTimeText(c);
                                startAlarm(c);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        container = (LinearLayout) view.findViewById(R.id.fragment_container);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.process));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.ingre));
        replaceFragment(new ProcessFragment());

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new ProcessFragment());
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new IngredientsFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    private void AddLastView(final String mStrUser_id, final String recipe_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "add_view_recipe",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("addlast_respose", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String favourite=jsonObject.getString("msg");

                            Log.e("lastview ",favourite);
                            if (result.equalsIgnoreCase("true")) {


                            } else {
                             //   Toast.makeText(getActivity(), "no records", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("recipe_id", Recipe_id);
        fragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void updateTimeText(Calendar c) {
        String timeText = getResources().getString(R.string.set_alarm);
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Toast.makeText(getActivity(), " " + timeText, Toast.LENGTH_SHORT).show();
    }

    private void startAlarm(Calendar c) {
        // String timeText;
        //timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        //  System.out.println(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
        // Toast.makeText(getActivity(), "Start Alarm "+c.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        //  int i=5;
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        //  alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
       /* int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }*/
    }

    private void AddtoHeart(final String user_id,final String recipe_id) {
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
                                    Toast.makeText(getActivity(), R.string.add_fav, Toast.LENGTH_SHORT).show();
                                }else if (favourite.equals("Unfavrt"))
                                {

                                    Toast.makeText(getActivity(), R.string.remove_fav, Toast.LENGTH_SHORT).show();
                                }





                               /* for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String cat_id = jsonObject1.getString("cat_id");
                                    String product_brand = jsonObject1.getString("product_brand");
                                    String product_name = jsonObject1.getString("product_name");
                                    String image = jsonObject1.getString("image");
                                    String video = jsonObject1.getString("video");
                                    String site_link = jsonObject1.getString("site_link");
                                    product_modelList.add(i, new ProductsByCatId_Model(id, cat_id, product_brand, product_name, image, video, site_link));

                                }

                                adapter_product = new DeviceDetailAdapter(product_modelList, getActivity());
                                recycler_cat_pro.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recycler_cat_pro.addItemDecoration(new DividerItemDecoration(recycler_cat_pro.getContext(), DividerItemDecoration.VERTICAL));


                                recycler_cat_pro.setAdapter(adapter_product);
                                adapter_product.notifyDataSetChanged();*/



                            } else {
                                Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "eror "+error, Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("recipe_id", recipe_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
















}
