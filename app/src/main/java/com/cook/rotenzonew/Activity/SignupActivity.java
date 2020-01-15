package com.cook.rotenzonew.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
EditText username,email,password,phone;
Button mSubmitData;
ProgressBar progressBar;
    TextView tv_t_and_c;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //setLanguageForApp(Shared_Pref.getLanguage(this));
        setContentView(R.layout.activity_signup);


    if (SharedPrefManager.getInstance(this).isLoggedIn()) {
        startActivity(new Intent(this, MainActivity.class));
    }

        initview();

        mSubmitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFunSignupCall();
            }
        });

    tv_t_and_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String news_url= Base_Url.BaseUrl_t_c;

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

    private void setLanguageForApp(String lang) {
        LocaleHelper.setLocale(SignupActivity.this, lang);

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

    public void initview()
{
    username=findViewById(R.id.username);
    email=findViewById(R.id.email);
    password=findViewById(R.id.password);
    phone=findViewById(R.id.phone);
    mSubmitData=findViewById(R.id.button);
    tv_t_and_c=findViewById(R.id.tv_t_and_c);
}

public void mFunSignupCall()
{
    final String mStrusername = username.getText().toString().trim();
    final String mStremail = email.getText().toString().trim();
    final String mStrpassword = password.getText().toString().trim();
    final String mStrphone = phone.getText().toString().trim();

    //first we will do the validations

    if (TextUtils.isEmpty(mStrusername)) {
        username.setError(getString(R.string.enter_first));
        username.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(mStremail)) {
        email.setError(getString(R.string.enter_email));
        email.requestFocus();
        return;
    }

    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mStremail).matches()) {
        email.setError(getString(R.string.valid_email));
        email.requestFocus();
        return;
    }

    if (TextUtils.isEmpty(mStrpassword)) {
        password.setError(getString(R.string.enter_pw));
        password.requestFocus();
        return;
    }

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"user_signup",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    DialogUtil.show(SignupActivity.this);
                    try {
                        Log.e("signup_res", response.toString());
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                         boolean res=obj.getBoolean("result");
                        //if no error in response
                        if (res==true){

                            DialogUtil.dismiss();
                            JSONObject userJson = obj.getJSONObject("data");
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

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            String msg=obj.getString("msg");
                            DialogUtil.dismiss();
                            Toast.makeText(SignupActivity.this,""+msg, Toast.LENGTH_SHORT).show();
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
            params.put("name", mStrusername);
            params.put("email", mStremail);
            params.put("mobile", mStrphone);
            params.put("password", mStrpassword);
            params.put("language", "Mongolian");
            return params;
        }
    };

    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


}




}
