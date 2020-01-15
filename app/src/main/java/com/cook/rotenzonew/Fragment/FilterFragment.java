package com.cook.rotenzonew.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.Ingre_Model;
import com.cook.rotenzonew.Model.RecipeDetailsModelData;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.Base_Url;
import com.cook.rotenzonew.Utils.Conectivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cook.rotenzonew.Activity.MainActivity.toolbar_title;
import static com.cook.rotenzonew.Utils.Base_Url.filter_ingridents;
import static com.cook.rotenzonew.Utils.Base_Url.search_recipe_by_ingredients;

public class FilterFragment extends Fragment {

    SeekBar seekbar;
    TextView tv_kcal_value;
    ImageView img_clock;
    private Calendar calendar;
    private TimePicker timePicker1;
    private TextView time,cancel,ok;
    private String format = "";
    private ImageView image,image2;
    LinearLayout linearlayout;
    RadioButton radio_name,radio_cook_time,radio_cal_name;
    EditText et_ingre;
    FloatingActionButton fab_del;
    ListView lv;
    AutoCompleteTextView acT1;
    String Ingre_id;
    String menu_id;
    TextView tv_total_recipe;
    ArrayAdapter<String> adapter;
    HashMap<Integer, Ingre_Model> Ingredient_hashmap=new HashMap<>();
    ArrayList<String> ingredents=new ArrayList<>();
    ArrayList<Ingre_Model>ingredentsModel=new ArrayList<>();
    List<RecipeDetailsModelData> recipeDetailsModelDataList=new ArrayList<>();
    RequestQueue queue;
    String mStrUser_id;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments()!=null){
            menu_id=getArguments().getString("menu_id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root= inflater.inflate(R.layout.fragment_filter, container, false);
        queue= Volley.newRequestQueue(getActivity());
        getActivity().setTitle(R.string.filter);
        toolbar_title.setText(R.string.filter);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        seekbar = (SeekBar)root.findViewById(R.id.seekBar1);
        tv_kcal_value = root.findViewById(R.id.tv_kcal_value);
        img_clock = root.findViewById(R.id.img_clock);
        timePicker1 = (TimePicker) root.findViewById(R.id.timePicker1);
        time = (TextView) root.findViewById(R.id.textView1);
        radio_name =  root.findViewById(R.id.radio_name);
        radio_cook_time =  root.findViewById(R.id.radio_cook_time);
        radio_cal_name =  root.findViewById(R.id.radio_cal_name);
        et_ingre =  root.findViewById(R.id.et_ingre);
        fab_del =  root.findViewById(R.id.fab_del);
        acT1 =  root.findViewById(R.id.acT1);
        tv_total_recipe =  root.findViewById(R.id.tv_total_recipe);
        lv = (ListView) root.findViewById(R.id.listView);


        calendar = Calendar.getInstance();
        if (Conectivity.isConnected(getActivity())){
            getIngredients();
        }else {
            Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, ingredents);
        acT1.setAdapter(adapter);

        acT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                acT1.showDropDown();
                adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1, ingredents);

                acT1.setAdapter(adapter);
            }
        });

        acT1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem=acT1.getAdapter().getItem(position).toString();

                for (int i = 0; i <Ingredient_hashmap.size(); i++) {
                    if (Ingredient_hashmap.get(i).getIngredients().equals(selectedItem)){

                        Ingre_id=Ingredient_hashmap.get(i).getIngredients();

                    }
                    //Toast.makeText(RegistrationActivityTwo.this,country_id, Toast.LENGTH_SHORT).show();
                }
               // Log.e("autotext_id",Ingre_id);
                if (Conectivity.isConnected(getActivity())){
                    getRecipeByIngre(Ingre_id);
                }else {
                    Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
                }

            }
        });

        seekbar.setMax(400);
        seekbar.setProgress(400);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_kcal_value.setText(""+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //***********radio button
        radio_name.setChecked(true);
        radio_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_cal_name.setChecked(false);
                radio_cook_time.setChecked(false);
            }
        });
        radio_cal_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_name.setChecked(false);
                radio_cook_time.setChecked(false);
            }
        });
        radio_cook_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_name.setChecked(false);
                radio_cal_name.setChecked(false);
            }
        });

        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_name.setChecked(true);
                radio_cal_name.setChecked(false);
                radio_cook_time.setChecked(false);
            }
        });

        img_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                /*showTime(hour, min);*/

                /* dialog.setTitle("Dialog Title");*/

                dialog.setContentView(R.layout.dialog_view);

                cancel=(TextView)dialog.findViewById(R.id.cancel);
                ok=dialog.findViewById(R.id.set_button);
                image=dialog.findViewById(R.id.image);
                timePicker1=dialog.findViewById(R.id.timePicker1);
                image2=dialog.findViewById(R.id.clock);
                linearlayout=dialog.findViewById(R.id.linearlayout);

                /* public void showTime(int hour, int min){*/
                if (hour == 0) {
                    hour += 12;
                    format = "AM";
                } else if (hour == 12) {
                    format = "PM";
                } else if (hour > 12) {
                    hour -= 12;
                    format = "PM";
                } else {
                    format = "AM";
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        Toast.makeText(getActivity(),"Timer set sucessfully installed",Toast.LENGTH_LONG).show();
                    }
                });
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        linearlayout.setVisibility(View.VISIBLE);
                        timePicker1.setVisibility(View.GONE);
                        image2.setVisibility(View.VISIBLE);
                        image.setVisibility(View.GONE);


                    }
                });
                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearlayout.setVisibility(View.GONE);
                        timePicker1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                    }
                });

                /*}*/
                dialog.show();


            }
        });

        //************et_ingredient

        acT1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Toast.makeText(getActivity(), "Text is"+s, Toast.LENGTH_SHORT).show();

                adapter.getFilter().filter(s);
//                try {
//                    //     state_list.clear();
//                    //  stateed.setAdapter(null);
//                    //   StateAdapter.notifyDataSetChanged();
//                    if(state_list.size() <=0) {
//                        StateExecuteTask(country_id, s.toString());
//                    }else {
//                        Toast.makeText(RegistrationActivityTwo.this, "done", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //***tv_total_recipe onclick
        tv_total_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment5 = new RecipeNavigationDetailFragment();
                Bundle args = new Bundle();
                // args.putSerializable("menu_recipe_model", recipeDetailsModelData);
                args.putParcelableArrayList("menu_recipe_model", (ArrayList<? extends Parcelable>) recipeDetailsModelDataList);
                fragment5.setArguments(args);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.commit();
            }
        });







        return root;
    }

    private void getRecipeByIngre(final String ingre_id) {

        String url= Base_Url.BaseUrl+search_recipe_by_ingredients;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Res_search_ing", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result=jsonObject.getString("result");

                            // recipeProcess_model=new RecipeProcess_Model();

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    RecipeDetailsModelData recipeDetailsModelData=new RecipeDetailsModelData();

                                    recipeDetailsModelData.setId(jsonObject2.getString("id"));
                                    recipeDetailsModelData.setRecipName(jsonObject2.getString("recip_name"));
                                    recipeDetailsModelData.setRecipKcal(jsonObject2.getString("recip_kcal"));
                                    recipeDetailsModelData.setRecipTime(jsonObject2.getString("recip_time"));
                                    recipeDetailsModelData.setProductId(jsonObject2.getString("product_id"));
                                    recipeDetailsModelData.setMenuId(jsonObject2.getString("menu_id"));
                                    recipeDetailsModelData.setImage(jsonObject2.getString("image"));

                                    recipeDetailsModelDataList.add(recipeDetailsModelData);
                                   // Log.e("id_rec", ""+recipeDetailsModelDataList.get(0).getId());
                                }

                                tv_total_recipe.setVisibility(View.VISIBLE);
                                tv_total_recipe.setText(R.string.total_found+" "+recipeDetailsModelDataList.size());
                                tv_total_recipe.setText(getResources().getString(R.string.total_found)+" "+recipeDetailsModelDataList.size());

                                if (recipeDetailsModelDataList.size()>0){

                                }

                            }else {
                                tv_total_recipe.setVisibility(View.VISIBLE);
                                tv_total_recipe.setText(getResources().getString(R.string.total_found)+" "+0);
                                // Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("product_id", Shared_Pref.getProd_Id(getActivity()));
               // params.put("menu_id", menu_id);
                params.put("ingredient", ingre_id);
                params.put("time", "");
               params.put("energy_level", "");

                return params;
            }
        };
        queue.add(postRequest);

    }


    private void getIngredients() {

        String url= Base_Url.BaseUrl+filter_ingridents;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response_search", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String result=jsonObject.getString("result");

                            // recipeProcess_model=new RecipeProcess_Model();

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String id = jsonObject2.getString("id");
                                    String ingredients = jsonObject2.getString("ingredients");

                                    ingredents.add(i, ingredients);

                                    Ingredient_hashmap.put(i, new Ingre_Model(id,ingredients));

                                    ingredentsModel.add(i, new Ingre_Model(id,ingredients));
                                }


                            }else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ingredient", "");
                params.put("user_id", mStrUser_id);

                return params;
            }
        };
        queue.add(postRequest);



    }

}
