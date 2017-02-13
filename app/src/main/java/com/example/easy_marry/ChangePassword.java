package com.example.easy_marry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
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
import com.example.easy_marry.Adapter.GridAdapter;
import com.example.easy_marry.Bean.GridBean;
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
 * Created by android2 on 20/6/16.
 */
public class ChangePassword extends Activity {
    Button btn_change_pass;
    View itemView1;
    Context context;
    ImageView img_back;
    GeneralData gD;
    String str_user_id;
    EditText et_get_matrimony_id,et_get_current_pass,et_get_new_pass,et_get_con_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        context=this;
        gD= new GeneralData(context);



        str_user_id = gD.prefs.getString("userid", null);
        img_back=(ImageView)findViewById(R.id.img_set_back);
       // et_get_matrimony_id=(EditText)findViewById(R.id.et_matrimony_id);
        et_get_current_pass=(EditText)findViewById(R.id.et_current_pass);
        et_get_new_pass=(EditText)findViewById(R.id.et_new_pass);
        et_get_con_pass=(EditText)findViewById(R.id.et_con_pass);

        btn_change_pass=(Button)findViewById(R.id.btn_change_pass);
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
               /* if(et_get_matrimony_id.getText().toString().length()==0){
                    Toast.makeText(ChangePassword.this, "Enter Matrimony Id ", Toast.LENGTH_SHORT).show();

                }
                else*/
                if(et_get_current_pass.getText().toString().length()==0){
                    Toast.makeText(ChangePassword.this, "Enter current password", Toast.LENGTH_SHORT).show();

                }
                else if(et_get_new_pass.getText().toString().length()==0){
                    Toast.makeText(ChangePassword.this, "Enter new password", Toast.LENGTH_SHORT).show();

                }
                else if(et_get_con_pass.getText().toString().length()==0){
                    Toast.makeText(ChangePassword.this, "Enter confirm password", Toast.LENGTH_SHORT).show();

                }
                else if(!et_get_new_pass.getText().toString().equals(et_get_con_pass.getText().toString())){
                    Toast.makeText(ChangePassword.this, "New and confirm password not matched", Toast.LENGTH_SHORT).show();

                }
                else if(et_get_current_pass.getText().toString().equals(et_get_new_pass.getText().toString())){
                    Toast.makeText(ChangePassword.this, "Current and New password are same", Toast.LENGTH_SHORT).show();

                }
                else{
                    changePasswordCall();
                }

                }
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);

                    View itemView1 = LayoutInflater.from(context)
                            .inflate(R.layout.custom_basic_det_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                    txt_span_text.setText("Unable to connect with internet. Please check your internet connection and try again");
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            altDialog.dismiss();
                        }
                    });
                    altDialog.show();
                }


            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void changePasswordCall(){
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"changepassword.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {


                               /* final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);

                                itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.change_pass_success_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                TextView txt_span1=(TextView)itemView1.findViewById(R.id.span_text_pass) ;
                                String str_span="Your password has been successfully changed";
                                SpannableString spannableString = new SpannableString(str_span);
                                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 13, 0);
                                txt_span1.setText(spannableString);
                                altDialog.show();*/

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);

                                View itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.custom_basic_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                String str_span="Your password has been successfully changed";
                                SpannableString spannableString = new SpannableString(str_span);
                                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 13, 0);
                                txt_span_text.setText(spannableString);

                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                                altDialog.show();

                            }
                            else  if (jsobj.getString("status").equalsIgnoreCase("failure")){
                                if (jsobj.getInt("code")==2){
                                    Toast.makeText(ChangePassword.this, ""+jsobj.getString("Message"), Toast.LENGTH_SHORT).show();
                                }
                                else if (jsobj.getInt("code")==0){
                                    Toast.makeText(ChangePassword.this, ""+jsobj.getString("Message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(ChangePassword.this, "None", Toast.LENGTH_SHORT).show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(context, "res : " + res, Toast.LENGTH_LONG).show();

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

                params.put("userid",str_user_id);
                params.put("oldpassword", et_get_current_pass.getText().toString().trim());
                params.put("newpassword", et_get_new_pass.getText().toString().trim());


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
