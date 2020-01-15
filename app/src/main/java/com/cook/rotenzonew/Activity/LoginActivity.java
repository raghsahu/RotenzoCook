package com.cook.rotenzonew.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.cook.rotenzonew.Utils.DialogUtil;
import com.cook.rotenzonew.Utils.LocaleHelper;
import com.cook.rotenzonew.Utils.URLs;
import com.cook.rotenzonew.Utils.VolleySingleton;
import com.facebook.login.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView tv_forgot_pw,tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setLanguageForApp(Shared_Pref.getLanguage(this));
        setContentView(R.layout.login_layout);
        initview();
        clicklistner();
    }

        public void setLanguageForApp(String lang){

            LocaleHelper.setLocale(LoginActivity.this, lang);

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

    public void clicklistner()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mFunApiLogin();
            }
        });

        tv_forgot_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,ForgotPwActivity.class);
                startActivity(intent);

            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });
    }

    public void mFunApiLogin()
    {
        //first getting the values
        final String mStrEmail = email.getText().toString();
        final String mStrpassword = password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(mStrEmail)) {
            email.setError(getString(R.string.enter_first));
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mStrpassword)) {
            password.setError(getString(R.string.enter_pw));
            password.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.show(LoginActivity.this);
                        try {
                            JSONObject obj = new JSONObject(response);
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


                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                //String msg=obj.getString("msg");
                                DialogUtil.dismiss();
                                Toast.makeText(LoginActivity.this,getString(R.string.invalid_email_pw), Toast.LENGTH_SHORT).show();
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
                params.put("email", mStrEmail);
                params.put("password", mStrpassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void initview()
    {
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        tv_forgot_pw=findViewById(R.id.tv_forgot_pw);
        tv_signup=findViewById(R.id.tv_signup);

    }
}
