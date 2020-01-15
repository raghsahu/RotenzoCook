package com.cook.rotenzonew.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.Base_Url;
import com.cook.rotenzonew.Utils.DialogUtil;
import com.cook.rotenzonew.Utils.LocaleHelper;
import com.cook.rotenzonew.Utils.URLs;
import com.cook.rotenzonew.Utils.VolleySingleton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignupScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Button signup,login;
    ImageView fb,googl;
    SignInButton btn_goole;
    LoginButton btn_fb;
    TextView tv_privacy_policy;
    CallbackManager callbackManager;
    private String social_name = "", social_id = "", social_email = "", social_img = "";

     GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setLanguageForApp(Shared_Pref.getLanguage(this));
        setContentView(R.layout.signup_layout);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            // finish();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        initview();
        clicklistner();



    }

    public void onClick(View v) {
        if (v == fb) {
            btn_fb.performClick();
        }
    }

    public void onClick_Google(View v) {
        if (v == googl) {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent,RC_SIGN_IN);
           // btn_goole.performClick();
        }
    }

    private void setLanguageForApp(String lang) {
        LocaleHelper.setLocale(SignupScreenActivity.this, lang);

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

    public  void initview()
    {
        fb = findViewById(R.id.fb);
        googl = findViewById(R.id.googl);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        btn_goole=findViewById(R.id.btn_goole);
        btn_fb=findViewById(R.id.btn_fb);
        tv_privacy_policy=findViewById(R.id.tv_privacy_policy);

       // btn_fb.setBackgroundResource(R.drawable.facebook_icon);
        //btn_goole.setBackgroundResource(R.drawable.google_icon);

        callbackManager = CallbackManager.Factory.create();
        btn_fb.setPermissions("email", "public_profile");

        btn_fb.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");
                    Toast.makeText(SignupScreenActivity.this, "success", Toast.LENGTH_SHORT).show();

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.i("LoginActivity",response.toString());
                                try {
                                    String id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic",profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    String name = object.getString("name");
                                   // String email = object.getString("email");
                                     // String gender = object.getString("gender");
                                    //String birthday = object.getString("birthday");
                                   // if (Connectivity.isConnected(SignupScreenActivity.this)){
                                        gotohomeFacebook(id,name);
                                  //  }else {
                                        //Toast.makeText(SignupScreenActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                                   // }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                        Toast.makeText(SignupScreenActivity.this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Toast.makeText(SignupScreenActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
                      //  Log.e("LoginActivity", exception.toString());
                       // Log.e("LoginActivity", exception.getMessage());
                    }
                });

    }

    private void gotohomeFacebook(final String id, final String name) {

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"facebook_login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.show(SignupScreenActivity.this);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("resp_fb", response.toString());

                            boolean res=obj.getBoolean("result");
                            //if no error in response
                            if (res==true){
                                DialogUtil.dismiss();
                                JSONObject userJson = obj.getJSONObject("data");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("id"),
                                        userJson.getString("name"),
                                        userJson.getString("mobile"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("lat"),
                                        userJson.getString("lng"),
                                        userJson.getString("status"),
                                        userJson.getString("image"),
                                        userJson.getString("email_status"),
                                        userJson.getString("mobile_status"),
                                        userJson.getString("type"),
                                        userJson.getString("facebook_id"));

                               // storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity

                                startActivity(new Intent(SignupScreenActivity.this, MainActivity.class));
                                finish();
                            } else {
                                //String msg=obj.getString("msg");
                                DialogUtil.dismiss();
                                Toast.makeText(SignupScreenActivity.this,"please try again", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("facebook_id", id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void clicklistner()
    {

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupScreenActivity.this,SignupActivity.class));


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupScreenActivity.this,LoginActivity.class));
            }
        });


        btn_goole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);


            }
        });

        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String news_url= Base_Url.BaseUrl_privacy;

                if (!news_url.startsWith("http://") && !news_url.startsWith("https://")){
                    String  url = "http://" + news_url;

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_url));
                    startActivity(browserIntent);
                }


            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    //***********
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            social_name = acct.getDisplayName();
            social_email = acct.getEmail();

            if (acct.getPhotoUrl() != null) {
                social_img = acct.getPhotoUrl().toString();
            } else {
                social_img = "";
            }
            Log.e("social_img ", " " + social_img);
            social_id = acct.getId();

            acct.getId();
            Log.e("GoogleResult", social_id + "------" + social_name + "------" + social_email);

//            if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
                gotoHome();
//            }else {
//                Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
//            }

        }else{
            Toast.makeText(getApplicationContext(),getString(R.string.sign_cancel),Toast.LENGTH_LONG).show();
        }
    }

    private void gotoHome() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"google_login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.show(SignupScreenActivity.this);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("resp_google", response.toString());

                            boolean res=obj.getBoolean("result");
                            //if no error in response
                            if (res==true){
                                DialogUtil.dismiss();
                                JSONObject userJson = obj.getJSONObject("data");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("id"),
                                        userJson.getString("name"),
                                        userJson.getString("mobile"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("lat"),
                                        userJson.getString("lng"),
                                        userJson.getString("status"),
                                        userJson.getString("image"),
                                        userJson.getString("email_status"),
                                        userJson.getString("mobile_status"),
                                        userJson.getString("type"),
                                        userJson.getString("facebook_id"));

                                // storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity

                                startActivity(new Intent(SignupScreenActivity.this, MainActivity.class));
                                finish();
                            } else {
                                //String msg=obj.getString("msg");
                                DialogUtil.dismiss();
                                Toast.makeText(SignupScreenActivity.this,getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", social_name);
                params.put("email", social_email);
                params.put("social_id", social_id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
