package com.cook.rotenzonew.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cook.rotenzonew.Activity.MainActivity;
import com.cook.rotenzonew.Adapter.VideoAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.TabTipslist;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.Model.Videolist;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.Conectivity;
import com.cook.rotenzonew.Utils.URLs;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TipsFragment extends Fragment {

    ArrayList<Videolist>videolist=new ArrayList<>();
    ArrayList<TabTipslist>TipsTablist=new ArrayList<>();
    VideoAdapter videoAdapter;
    RecyclerView recycler_video;
    TabLayout tablayout;
    String mStrUser_id;

    public TipsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        View root= inflater.inflate(R.layout.fragment_tips, container, false);

        recycler_video=root.findViewById(R.id.recycler_video);
        tablayout=root.findViewById(R.id.tablayout);
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        if (Conectivity.isConnected(getActivity())){
            getTipsTab();

        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
               String TipsId = TipsTablist.get(position).getId();

                Log.d("mmm",TipsId.toString());
                if (Conectivity.isConnected(getActivity())){
                    getVideo(TipsId);
                }else Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });




        return root;
    }

    private void getTipsTab() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "get_tips",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            Log.e("tab_tips", response.toString());
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            System.out.println("check_response  " + obj);

                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String tips = jsonObject.getString("tips");
                                   // String mn_tips = jsonObject.getString("mn_tips");

                                    TipsTablist.add(i, new TabTipslist(id,tips));

                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                            }

                            for (int j = 0; j < TipsTablist.size(); j++) {

                                tablayout.addTab(tablayout.newTab().setText(TipsTablist.get(j).getTips()));
                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",mStrUser_id );

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void getVideo(final String tipsId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "get_tipTitle_by_productId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            videolist.clear();
                            progressDialog.dismiss();
                            Log.e("video_tips", response.toString());
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            System.out.println("check response  " + obj);

                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String video = jsonObject.getString("video");
                                    String video_title = jsonObject.getString("video_title");

                                    videolist.add(i, new Videolist(video,video_title));

                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
                            }

                            videoAdapter = new VideoAdapter(videolist, getActivity());
                            recycler_video.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //recycler_video.addItemDecoration(new DividerItemDecoration(recycler_video.getContext(), DividerItemDecoration.VERTICAL));
                            recycler_video.setAdapter(videoAdapter);
                            videoAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", Shared_Pref.getProd_Id(getActivity()));
                params.put("tip_id", tipsId);
                params.put("user_id", mStrUser_id);

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

}
