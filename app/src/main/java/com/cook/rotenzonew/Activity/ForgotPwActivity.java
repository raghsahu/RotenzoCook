package com.cook.rotenzonew.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cook.rotenzonew.Helper.SharedPrefManager;
import com.cook.rotenzonew.Model.User;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.DialogUtil;
import com.cook.rotenzonew.Utils.URLs;
import com.cook.rotenzonew.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPwActivity extends AppCompatActivity {

    EditText email;
    Button submit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

        email=findViewById(R.id.email);
        submit_email=findViewById(R.id.submit_email);

        submit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email=email.getText().toString();

                if (!Email.isEmpty()){

                    CallForgotApi(Email);

                }else {
                    Toast.makeText(ForgotPwActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void CallForgotApi(final String email) {
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"forgot_Password",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.show(ForgotPwActivity.this);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String res=obj.getString("result");
                            String msg=obj.getString("msg");
                            //if no error in response
                            if (res.equalsIgnoreCase("true")){
                                DialogUtil.dismiss();

                                Toast.makeText(ForgotPwActivity.this,msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPwActivity.this, LoginActivity.class));
                            } else {
                                //String msg=obj.getString("msg");
                                DialogUtil.dismiss();
                                Toast.makeText(ForgotPwActivity.this,msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            DialogUtil.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
