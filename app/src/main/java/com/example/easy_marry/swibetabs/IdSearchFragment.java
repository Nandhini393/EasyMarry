package com.example.easy_marry.swibetabs;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android2 on 17/6/16.
 */
public class IdSearchFragment extends android.support.v4.app.Fragment {
//ImageView img_list, img_grid;

EditText et_get_matrimony_id;
    Button btn_search;
    String str_user_id;
    Context ctx;
    GeneralData gD;
    public IdSearchFragment(String user_id,Context con) {
        // Required empty public constructor
        str_user_id=user_id;
        ctx=con;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.id_search,container, false);
        et_get_matrimony_id=(EditText)v.findViewById(R.id.et_matrimony_id);
        et_get_matrimony_id.setText("EM273881154");
        gD=new GeneralData(ctx);
        btn_search=(Button)v.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    gD.showAlertDialog(ctx,"Loading", "Please wait...");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"idsearch.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                                try {
                                    Log.i("HH_id_search", "strResp : " + response);
                                    ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

                                    JSONObject jsobj = new JSONObject(response);

                                    Log.i("HH_id_search", "strResp : " + response);
                                    if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                        gD.altDialog.dismiss();
                                        Intent i = new Intent(getContext(), DailyRecomViewProfie.class);
                                        i.putExtra("id_search_json",response);
                                        i.putExtra("str_from","id_search");
                                        startActivity(i);

                                    }
                                    else  if (jsobj.getString("status").equalsIgnoreCase("failure")){
                                        gD.altDialog.dismiss();
                                        Toast.makeText(ctx, ""+jsobj.getString("Message"), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        Toast.makeText(getContext(), "res : " + res, Toast.LENGTH_LONG).show();

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
                        Log.e("NN_id","easymarryid->"+et_get_matrimony_id.getText().toString().trim());
                        Log.e("NN_id","userid->"+str_user_id);
                        params.put("easymarryid", et_get_matrimony_id.getText().toString().trim());
                        params.put("userid",str_user_id);


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


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                }
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setCancelable(false);

                    View itemView1 = LayoutInflater.from(ctx)
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

        return v;

    }

}