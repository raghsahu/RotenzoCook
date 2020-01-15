package com.cook.rotenzonew.Fragment;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import com.cook.rotenzonew.Activity.SplashActivity;
import com.cook.rotenzonew.Adapter.DeviceAdapter;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.DeviceModel;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    RecyclerView recyclerView;
    View view;
    Context context;
    DeviceAdapter deviceAdapter;
    LinearLayoutManager linearLayoutManager;
    Spinner spin_lang;
    TextView tv_share_ex,tv_contact_us,tv_signout,tv_change_pw;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String mStrUser_id;
    private List<DeviceModel> deviceModelsList = new ArrayList<>();
     SharedPrefManager manager;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setLanguageForApp(Shared_Pref.getLanguage(getActivity()));
        view = inflater.inflate(R.layout.fragment_settings, container, false);
       context=getActivity();

        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),  new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        initview();
        mFunSettingApiCall();
        deviceAdapter = new DeviceAdapter(deviceModelsList, getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;


    }

    public void initview() {

        recyclerView = view.findViewById(R.id.recyclerView);
        spin_lang = view.findViewById(R.id.spin_lang);
        tv_share_ex = view.findViewById(R.id.tv_share_ex);
        tv_contact_us=view.findViewById(R.id.tv_contact_us);
        tv_signout=view.findViewById(R.id.tv_signout);
        tv_change_pw=view.findViewById(R.id.tv_change_pw);


        spin_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lan=spin_lang.getSelectedItem().toString();
               // Toast.makeText(getActivity(), ""+lan, Toast.LENGTH_SHORT).show();
                if (lan.equalsIgnoreCase("English(en)")){
                   // Shared_Pref.setLanguage(getActivity(), "en");

                    Change_languageApi("English");

                }else if (lan.equalsIgnoreCase("монгол(mn)")){
                   // Shared_Pref.setLanguage(getActivity(), "mn");
                    Change_languageApi("Mongolian");


                }else {
                    //Shared_Pref.setLanguage(getActivity(), "mn");

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //***************exper share click
        tv_share_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment5 = new Rate_Review_fragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        tv_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment5 = new Contact_fragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        tv_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment5 = new ChangePw_fragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.containt_main_frame, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutApp();

            }
        });

    }

    private void Change_languageApi(final String language) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "change_language",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            System.out.println("checkresponse  " + obj);

                            boolean responsedata = obj.getBoolean("result");
                            if (responsedata == true) {

                                if (language.equalsIgnoreCase("English")){
                                    Shared_Pref.setLanguage(getActivity(), "en");
                                }else {
                                    Shared_Pref.setLanguage(getActivity(), "mn");
                                }

                                startActivity(new Intent(getActivity(), SplashActivity.class));
                                getActivity().finish();

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
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("language", language);
                params.put("user_id",mStrUser_id );
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void logoutApp() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                //.setTitle("Lamba")
                .setMessage(R.string.logout_app);

        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                 manager=new SharedPrefManager(getActivity());


                if (googleApiClient != null && googleApiClient.isConnected()) {
                    // signed in. Show the "sign out" button and explanation.
                    google_logout();
                    disconnectFromFacebook();
                } else{
                    Log.e("logout_app", "local login logout");
                    // not signed in. Show the "sign in" button and explanation.
                    disconnectFromFacebook();

                    manager.logout();
                    Shared_Pref.setProd_Id(getActivity(), "");
                    // AppPreference.setName(NewUser_Profile_Activity.this, "");
                    Intent intent=new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }


            }

        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

    private void disconnectFromFacebook() {

        Log.e("logout_app_fb", "fb login logout");
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

                manager.logout();
                Shared_Pref.setProd_Id(getActivity(), "");
                // AppPreference.setName(NewUser_Profile_Activity.this, "");
                Intent intent=new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        }).executeAsync();
    }

    private void google_logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()){
                            Log.e("logout_app_gmail", "google login logout");
                            manager.logout();
                            Shared_Pref.setProd_Id(getActivity(), "");
                            // AppPreference.setName(NewUser_Profile_Activity.this, "");
                            Intent intent=new Intent(getActivity(), SplashActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),"Session not close",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void mFunSettingApiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "selected_cat_id",
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
                                    String id = jsonObject.getString("id");

                                    System.out.println("id " + id);

                                    DeviceModel deviceModel = new DeviceModel(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("product_brand"),
                                            jsonObject.getString("product_name"),
                                            jsonObject.getString("image")
                                    );


                                    deviceModelsList.add(deviceModel);
                                }
                                recyclerView.setAdapter(deviceAdapter);


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
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", "1");
                params.put("user_id", mStrUser_id);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }


}






