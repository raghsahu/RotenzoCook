package com.cook.rotenzonew.Fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.BuildConfig;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessFragment extends Fragment {
    View view;
    TextView textView;
    String mStrUser_id;
    ImageView image_share;

    public ProcessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_process, container, false);

       textView=view.findViewById(R.id.mTextview);
        image_share=view.findViewById(R.id.image_share);


        if (getArguments()!=null){
        String  recipe_id =getArguments().getString("recipe_id");
        System.out.println("recpiy id is    "+recipe_id);
        mFunApiCallProcess(recipe_id);
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();


        image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Share_Ingre();
            }
        });


        return  view;
    }

    private void Share_Ingre() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    public void mFunApiCallProcess(final String recipe_id )
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "recipeDetail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Recipe response is ", response);

                        try {
                          //  product_modelList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")) {

                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String Process=jsonObject1.getString("recip_description");
                                System.out.println("check data "+Process);


                              //  textView.getSettings().setJavaScriptEnabled(true);

                              //  textView.loadData(Process, "text/html", "UTF-8");

                               /* try {
                                    textView.loadData(Process, "text/html; charset=utf-8", "UTF-8");
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e.getMessage());

                                }*/

                               // String str = "<a>This is a  <font color='#0000FF'> blue text</font> and this is a <font color='red'> red text</font> </a>";

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                    textView.setText(Html.fromHtml(Process, Html.FROM_HTML_MODE_COMPACT));
                                }else {
                                    textView.setText(Html.fromHtml(Process));
                                }

                               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    textView.setText(Html.fromHtml(Process), TextView.BufferType.SPANNABLE);

                                  //  textView.setText(Html.fromHtml(Process, TextView.BufferType.SPANNABLE));
                                } else {
                                    textView.setText(Html.fromHtml(Process));
                                }*/




/*
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String cat_id = jsonObject1.getString("cat_id");
                                    String product_brand = jsonObject1.getString("product_brand");
                                    String product_name = jsonObject1.getString("product_name");
                                    String image = jsonObject1.getString("image");
                                    String video = jsonObject1.getString("video");
                                    String site_link = jsonObject1.getString("site_link");


                                 //   product_modelList.add(i, new ProductsByCatId_Model(id, cat_id, product_brand, product_name, image, video, site_link));

                                }
*/





                              //  adapter_product = new DeviceDetailAdapter(product_modelList, getActivity());
                              //  recycler_cat_pro.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                              //  recycler_cat_pro.addItemDecoration(new DividerItemDecoration(recycler_cat_pro.getContext(), DividerItemDecoration.VERTICAL));


                               // recycler_cat_pro.setAdapter(adapter_product);
                              //  adapter_product.notifyDataSetChanged();

                                /*if (getActivity() != null) {
                                    if (product_modelList.isEmpty()) {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                                    }
                                }*/

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
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("recipe_id", recipe_id);
                params.put("user_id", mStrUser_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }





    }







