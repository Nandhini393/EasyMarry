package com.example.easy_marry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 6/6/16.
 */
public class ForgetPassword extends Activity {
    Button btn_forget_pwd;
 EditText ed_get_email;
    ImageView img_back;
    TextView txt_error_msg1;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        context=this;
        btn_forget_pwd=(Button)findViewById(R.id.btn_forget_pass);
        img_back=(ImageView)findViewById(R.id.img_for_back);
        ed_get_email=(EditText)findViewById(R.id.et_mail_or_number);
        txt_error_msg1=(TextView)findViewById(R.id.txt_error_msg1);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if((ed_get_email.getText().toString().trim().equals("hi"))||(ed_get_email.getText().toString().trim().equals("123456"))){

                   if(ed_get_email.getText().toString().trim().length()==0){
                       txt_error_msg1.setVisibility(View.VISIBLE);
                       txt_error_msg1.setText("Enter email");
                       ed_get_email.setBackgroundResource(R.drawable.red_edit_single);
                   }
                else{
                       forgetPassCall();

                   }
            }
        });
    }

    public void forgetPassCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "forgot.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                txt_error_msg1.setVisibility(View.INVISIBLE);
                                ed_get_email.setBackgroundResource(R.drawable.edit_single);

                                View itemView1;
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);
                                itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.reset_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);

                                TextView txt_login_link=(TextView)itemView1.findViewById(R.id.txt_link_login);
                                txt_login_link.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(ForgetPassword.this,SignInPage.class));
                                    }
                                });

                                altDialog.show();

                            }

                            else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                txt_error_msg1.setVisibility(View.VISIBLE);
                                ed_get_email.setBackgroundResource(R.drawable.red_edit_single);
                                }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(getApplicationContext(), "res : " + res, Toast.LENGTH_LONG).show();

// Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
// Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
// returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

//username=sathish@ansjad.com&password=testing

                params.put("email", ed_get_email.getText().toString());


// params.put("username", "dineshW@adjhd.com");
// params.put("password", "123456");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
// params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }

        };

//30Secs
        RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}
