package com.cook.rotenzonew.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cook.rotenzonew.Activity.LoginActivity;
import com.cook.rotenzonew.Activity.MainActivity;
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

/**
 * Created by Raghvendra Sahu on 05/12/2019.
 */
public class ChangePw_fragment extends Fragment {

    EditText old_password,new_password,conf_password;
    Button btn_submit;
    String mStrUser_id;
    public ChangePw_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_change_pw, container, false);

        old_password=root.findViewById(R.id.old_password);
        new_password=root.findViewById(R.id.new_password);
        conf_password=root.findViewById(R.id.conf_password);
        btn_submit=root.findViewById(R.id.btn_submit);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        mStrUser_id=user.getId();
        
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_pw=old_password.getText().toString();
                String new_pw=new_password.getText().toString();
                String conf_pw=conf_password.getText().toString();

                if (!old_pw.isEmpty() && !new_pw.isEmpty() && !conf_pw.isEmpty()){
                    if (new_pw.equalsIgnoreCase(conf_pw)){

                        CallChangePwApi(old_pw,new_pw);

                    }else {
                        Toast.makeText(getActivity(), "Confirm password not match", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if (old_pw.isEmpty()){
                       old_password.setError(getString(R.string.old_password));
                    }
                    else if (new_pw.isEmpty()){
                        new_password.setError(getString(R.string.new_password));
                    }else if (conf_pw.isEmpty()){
                        conf_password.setError(getString(R.string.conf_new_password));
                    }
                }


            }
        });


        return root;
    }

    private void CallChangePwApi(final String old_pw, final String new_pw) {
        //DialogUtil.show(getActivity());
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"change_password",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean res=obj.getBoolean("result");
                            String msg=obj.getString("msg");
                            //if no error in response
                            if (res==true) {
                               // DialogUtil.dismiss();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else {
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //DialogUtil.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", mStrUser_id);
                params.put("old_password", old_pw);
                params.put("new_password", new_pw);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}
