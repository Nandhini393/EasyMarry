package com.example.easy_marry.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.example.easy_marry.Adapter.ListDrawerAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.R;
import com.example.easy_marry.Splash_Screen;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 14/10/16.
 */
public class PartnerRegFour extends Activity {
    Button btn_next;
    RadioButton rb_eat_all, rb_eat_veg, rb_eat_nv, rb_eat_egg, rb_drink_yes, rb_drink_no, rb_drink_social, rb_smoke_yes, rb_smoke_no, rb_smoke_social;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ArrayList<ListDrawerBean> return_beanArrayList;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    String str_eat="4",str_drink="2",str_smoke="2";
    String str_user_id="";
    GeneralData gD;
    Context c;
    TextView txt_skip;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_partner4);
c=this;
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);
        rb_eat_all = (RadioButton) findViewById(R.id.radio_eating_all);
        rb_eat_veg = (RadioButton) findViewById(R.id.radio_eating_veg);
        rb_eat_nv = (RadioButton) findViewById(R.id.radio_eating_nonveg);
        rb_eat_egg = (RadioButton) findViewById(R.id.radio_eating_egg);
       // rb_eat_none = (RadioButton) findViewById(R.id.radio_eating_none);

        rb_drink_yes = (RadioButton) findViewById(R.id.radio_drink_yes);
        rb_drink_no = (RadioButton) findViewById(R.id.radio_drink_no);
        rb_drink_social = (RadioButton) findViewById(R.id.radio_drink_social);
       // rb_drink_none = (RadioButton) findViewById(R.id.radio_drink_none);


        rb_smoke_yes = (RadioButton) findViewById(R.id.radio_smoke_yes);
        rb_smoke_no = (RadioButton) findViewById(R.id.radio_smoke_no);
        rb_smoke_social = (RadioButton) findViewById(R.id.radio_smoke_social);
       // rb_smoke_none = (RadioButton) findViewById(R.id.radio_smoke_none);

        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);

        img_back = (ImageView) findViewById(R.id.img_back);
        txt_skip = (TextView) findViewById(R.id.txt_skip);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegFour.this, Matches.class));
                finish();
            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegFour.this, Matches.class));
                finish();
            }
        });

        rb_eat_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_eat_all.isChecked()){
                    str_eat= "4";
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                   // rb_eat_none.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });
        rb_eat_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_eat_veg.isChecked()){
                    str_eat= "1";
                    rb_eat_all.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                    //rb_eat_none.setChecked(false);
                }
            }
        });
        rb_eat_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_eat_nv.isChecked()){
                    str_eat= "2";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_egg.setChecked(false);
                   // rb_eat_none.setChecked(false);
                }
            }
        });
        rb_eat_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_eat_egg.isChecked()){
                    str_eat= "3";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    //rb_eat_none.setChecked(false);
                }
            }
        });




      /*  rb_eat_none.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_eat_none.isChecked()){

                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });*/
        rb_drink_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_drink_yes.isChecked()){
                    str_drink="1";
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(false);
                   // rb_drink_none.setChecked(false);
                }
            }
        });
        rb_drink_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_drink_no.isChecked()){
                    str_drink="2";
                    rb_drink_yes.setChecked(false);
                    rb_drink_social.setChecked(false);
                    //rb_drink_none.setChecked(false);
                }
            }
        });
        rb_drink_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_drink_social.isChecked()){

                    str_drink="3";

                    rb_drink_no.setChecked(false);
                    rb_drink_yes.setChecked(false);
                   // rb_drink_none.setChecked(false);
                }
            }
        });

       /* rb_drink_none.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_drink_none.isChecked()){

                    rb_drink_no.setChecked(false);
                    rb_drink_yes.setChecked(false);
                    rb_drink_social.setChecked(false);
                }
            }
        });*/

        rb_smoke_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_smoke_yes.isChecked()){

                    str_smoke="1";

                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);
                    //rb_smoke_none.setChecked(false);
                }
            }
        });
        rb_smoke_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_smoke_no.isChecked()){
                    str_smoke="2";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_social.setChecked(false);
                   // rb_smoke_none.setChecked(false);
                }
            }
        });
        rb_smoke_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_smoke_social.isChecked()){
                    str_smoke="3";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);
                    //rb_smoke_none.setChecked(false);
                }
            }
        });

       /* rb_smoke_none.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rb_smoke_none.isChecked()){

                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);
                }
            }
        });

*/
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                Log.e("str_eat", str_eat);
                Log.e("str_drink", str_drink);
                Log.e("str_smoke", str_smoke);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"register9_screen.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               // Toast.makeText(PartnerRegFour.this, response, Toast.LENGTH_LONG).show();
                                try {
                                    Log.i("HH", "strResp : " + response);
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    JSONObject jsobj = new JSONObject(response);

                                    Log.i("HH", "strResp : " + response);
                                    if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                        String strName=jsobj.getJSONObject("Response").getString("Name");
                                        String strUserId=jsobj.getJSONObject("Response").getString("userid");
                                        String strCompleteLevel=jsobj.getJSONObject("Response").getString("completeLevel");
                                        String strMembership=jsobj.getJSONObject("Response").getString("membership");
                                        String strScreenId=jsobj.getJSONObject("Response").getString("screen_id");
                                        String strProfileImage=GeneralData.LOCAL_IP_IMAGE + "upload/" +jsobj.getJSONObject("Response").getString("profileImage");
                                        String strEasyMarryId=jsobj.getJSONObject("Response").getString("easymarryid");
                                        String strMemPlan=jsobj.getJSONObject("Response").getString("membershipplan");
                                        String strMemValid=jsobj.getJSONObject("Response").getString("membershipvalidity");
                                        String strRating = jsobj.getJSONObject("Response").getString("Rating");
                           /* GeneralData.str_name = jsobj.getJSONObject("Response").getString("Name");
                            GeneralData.str_id = jsobj.getJSONObject("Response").getString("userid");
                            GeneralData.str_complete_profile_level = jsobj.getJSONObject("Response").getString("completeLevel");
                            GeneralData.str_image = GeneralData.LOCAL_IP_IMAGE + "images/" + jsobj.getJSONObject("Response").getString("profileImage");
                            GeneralData.str_membership = jsobj.getJSONObject("Response").getString("membership");
                          */


                                        Log.e("NNp4:profileimage",strProfileImage);
                                        SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                        prefEdit.putString("name", strName);
                                        prefEdit.putString("userid", strUserId);
                                        prefEdit.putString("completelevel", strCompleteLevel);
                                        prefEdit.putString("membership", strMembership);
                                        prefEdit.putString("profileimage", strProfileImage);
                                        prefEdit.putString("screenid", strScreenId);
                                        prefEdit.putString("easymarryid", strEasyMarryId);
                                        prefEdit.putString("memplan", strMemPlan);
                                        prefEdit.putString("memvalid", strMemValid);
                                        prefEdit.putString("Rating", strRating);
                                        prefEdit.commit();

                                        Log.e("NN:name",strName);
                                        Log.e("NN:userid",strUserId);
                                        Log.e("NN:completelevel",strCompleteLevel);
                                        Log.e("NN:membership",strMembership);
                                        Log.e("NN:profileimage",strProfileImage);
                                        Log.e("NN:screenid",strScreenId);
                                        Log.e("NN:easymarryid",strEasyMarryId);
                                        Log.e("NN:memplan",strMemPlan);
                                        Log.e("NN:memvalid",strMemValid);


                                        Intent i = new Intent(PartnerRegFour.this, Matches.class);
                                      //  i.putExtra("user_id", "1");
                                        startActivity(i);
                                        finish();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                              //  Toast.makeText(PartnerRegFour.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        //Toast.makeText(PartnerRegFour.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                        if (str_user_id.length() > 0) {
                            params.put("userid", str_user_id);
                        }
                        if (str_eat.length() > 0) {
                            params.put("partnereatinghabits", str_eat);
                        }
                        if (str_drink.length() > 0) {
                            params.put("partnerdrinkinghabits", str_drink);
                        }
                        if (str_smoke.length() > 0) {
                            params.put("partnersmokinghabits", str_smoke);
                        }

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


                RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegFour.this);
                requestQueue.add(stringRequest);
                } else {
                    listView.setVisibility(View.GONE);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(false);
                    View itemView1 = LayoutInflater.from(c)
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

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PartnerRegFour.this, Matches.class));
        finish();

    }
}
