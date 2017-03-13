package com.example.easy_marry.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

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
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 14/10/16.
 */
public class UserRegFive extends Activity implements MyInterface {
    Button btn_next;
    RadioButton rb_eat_all, rb_eat_veg, rb_eat_nv, rb_eat_egg,
            rb_drink_yes, rb_drink_no, rb_drink_social, rb_smoke_yes, rb_smoke_no, rb_smoke_social;
    String str_eat = "4", str_drink = "2", str_smoke = "2", str_user_id = "", str_hobbie_id = "";
    GeneralData gD;
    EditText et_hobbies, et_something_abt_you, et_search_profile_for;
    Context c;
    TextView txt_error_hobbies, txt_error_abt_you, txt_char_count;
    JSONArray json_hobbies;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ImageView img_back;
    boolean isClosed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user5);
        c = this;
        btn_next = (Button) findViewById(R.id.btn_next);
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);
        img_back = (ImageView) findViewById(R.id.img_back);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_hobbies = (EditText) findViewById(R.id.et_hobbies);
        et_something_abt_you = (EditText) findViewById(R.id.et_abt_you);


        rb_eat_all = (RadioButton) findViewById(R.id.radio_eating_all);
        rb_eat_veg = (RadioButton) findViewById(R.id.radio_eating_veg);
        rb_eat_nv = (RadioButton) findViewById(R.id.radio_eating_nonveg);
        rb_eat_egg = (RadioButton) findViewById(R.id.radio_eating_egg);

        rb_drink_yes = (RadioButton) findViewById(R.id.radio_drink_yes);
        rb_drink_no = (RadioButton) findViewById(R.id.radio_drink_no);
        rb_drink_social = (RadioButton) findViewById(R.id.radio_drink_social);

        rb_smoke_yes = (RadioButton) findViewById(R.id.radio_smoke_yes);
        rb_smoke_no = (RadioButton) findViewById(R.id.radio_smoke_no);
        rb_smoke_social = (RadioButton) findViewById(R.id.radio_smoke_social);

        txt_error_hobbies = (TextView) findViewById(R.id.txt_error_hobbies);
        txt_error_abt_you = (TextView) findViewById(R.id.txt_error_abt_u);
        txt_char_count = (TextView) findViewById(R.id.txt_char_count);

        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
                builder.setCancelable(true);

                View itemView1 = LayoutInflater.from(c)
                        .inflate(R.layout.custom_incomplete_reg_popup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.show();
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
                TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
                txt_title.setText("Profile Incomplete");
                txt_content.setText("Do you want to continue now?");
                Button btn_continue=(Button)itemView1.findViewById(R.id.btn_cont_reg);
                btn_continue.setText("Yes");
                Button btn_exit=(Button)itemView1.findViewById(R.id.btn_exit);
                btn_exit.setText("Later");
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        isClosed = true;
                    }

                });

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                        isClosed = false;
                    }
                });
            }
        });
        rb_eat_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_all.isChecked()) {
                    str_eat = "4";
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });
        rb_eat_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_veg.isChecked()) {
                    str_eat = "1";
                    rb_eat_all.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });
        rb_eat_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_nv.isChecked()) {
                    str_eat = "2";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_egg.setChecked(false);

                }
            }
        });
        rb_eat_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_egg.isChecked()) {
                    str_eat = "3";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);

                }
            }
        });


        rb_drink_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_yes.isChecked()) {
                    str_drink = "1";
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(false);

                }
            }
        });
        rb_drink_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_no.isChecked()) {
                    str_drink = "2";
                    rb_drink_yes.setChecked(false);
                    rb_drink_social.setChecked(false);

                }
            }
        });
        rb_drink_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_social.isChecked()) {

                    str_drink = "3";

                    rb_drink_no.setChecked(false);
                    rb_drink_yes.setChecked(false);

                }
            }
        });


        rb_smoke_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_yes.isChecked()) {

                    str_smoke = "1";

                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);

                }
            }
        });
        rb_smoke_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_no.isChecked()) {
                    str_smoke = "2";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_social.setChecked(false);

                }
            }
        });
        rb_smoke_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_social.isChecked()) {
                    str_smoke = "3";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);

                }
            }
        });
        et_hobbies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_hobbies.getVisibility() == View.VISIBLE) {
                    txt_error_hobbies.setVisibility(View.GONE);
                    et_hobbies.setPadding(0, 0, 0, 10);
                    et_hobbies.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_something_abt_you.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (txt_error_abt_you.getVisibility() == View.VISIBLE) {
                    txt_error_abt_you.setVisibility(View.GONE);
                    et_something_abt_you.setPadding(0, 0, 0, 10);
                    et_something_abt_you.setBackgroundResource(R.drawable.edit_text_border2);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e("reg_5: count", String.valueOf(s.toString().length()));

                txt_char_count.setVisibility(View.VISIBLE);
                txt_char_count.setText(s.toString().length() + " characters");
            }
        });

       /* et_hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_hobbies, "hobbies");

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < beanArrayList.size(); i++) {

                            String text = beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "hobbies");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
*/
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
              /*  if (et_hobbies.getText().toString().length() == 0) {
                    et_hobbies.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_hobbies.setText("Enter hobbies");
                    txt_error_hobbies.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_hobbies.setVisibility(View.VISIBLE);
                } else if (et_something_abt_you.getText().toString().length() == 0) {
                    et_something_abt_you.setBackgroundResource(R.drawable.edit_text_border2_red);
                    txt_error_abt_you.setText("Enter about you");
                    txt_error_abt_you.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_abt_you.setVisibility(View.VISIBLE);
                } else*/
                if (et_something_abt_you.getText().toString().length() < 50) {
                    et_something_abt_you.setBackgroundResource(R.drawable.edit_text_border2_red);
                    txt_error_abt_you.setText("About you(min.50 chars)");
                    txt_error_abt_you.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_abt_you.setVisibility(View.VISIBLE);

                } else {
                    registerCall();
                }
                }
                else{
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register_select.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray json_array_hobbies = jsobj.getJSONArray("Hobbies");
                                json_hobbies = jsobj.getJSONArray("Hobbies");


                                if (json_hobbies.length() > 0) {
                                    for (int i = 0; i < json_array_hobbies.length(); i++) {

                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = json_hobbies.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "hobbies");
                                listView.setAdapter(listDrawerAdapter);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(UserRegFive.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(UserRegFive.this, "res : " + res, Toast.LENGTH_LONG).show();

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

               /* params.put("username", edt_email.getText().toString().trim());
                params.put("password", edt_password.getText().toString().trim());*/

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


        RequestQueue requestQueue = Volley.newRequestQueue(UserRegFive.this);
        requestQueue.add(stringRequest);

        Log.e("reg_5: eatinghabits", str_eat);
        Log.e("reg_5: drinkinghabits", str_drink);
        Log.e("reg_5: smokinghabits", str_smoke);
        Log.e("reg_5: hobbies", et_hobbies.getText().toString().trim());
        Log.e("reg_5: aboutyou", et_something_abt_you.getText().toString().trim());
        Log.e("reg_5: userid", str_user_id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
            builder.setCancelable(false);

            View itemView1 = LayoutInflater.from(c)
                    .inflate(R.layout.custom_incomplete_reg_popup, null);
            final android.support.v7.app.AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
            TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
            txt_title.setText("Profile Incomplete");
            txt_content.setText("Do you want to continue now?");
            Button btn_continue=(Button)itemView1.findViewById(R.id.btn_cont_reg);
            btn_continue.setText("Yes");
            Button btn_exit=(Button)itemView1.findViewById(R.id.btn_exit);
            btn_exit.setText("Later");
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    isClosed = true;
                }

            });

            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    altDialog.dismiss();
                    isClosed = false;
                }
            });

        }
        return isClosed;

    }

    public void registerCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register5_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //   Toast.makeText(UserRegFive.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                String strName = jsobj.getJSONObject("Response").getString("Name");
                                String strUserId = jsobj.getJSONObject("Response").getString("userid");
                                String strCompleteLevel = jsobj.getJSONObject("Response").getString("completeLevel");
                                String strMembership = jsobj.getJSONObject("Response").getString("membership");
                                String strScreenId = jsobj.getJSONObject("Response").getString("screen_id");
                                String strProfileImage = GeneralData.LOCAL_IP_IMAGE + "upload/" + jsobj.getJSONObject("Response").getString("profileImage");
                                String strEasyMarryId = jsobj.getJSONObject("Response").getString("easymarryid");
                                String strMemPlan = jsobj.getJSONObject("Response").getString("membershipplan");
                                String strMemValid = jsobj.getJSONObject("Response").getString("membershipvalidity");
                                String strRating = jsobj.getJSONObject("Response").getString("Rating");

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

                                Log.e("NN:name", strName);
                                Log.e("NN:userid", strUserId);
                                Log.e("NN:completelevel", strCompleteLevel);
                                Log.e("NN:membership", strMembership);
                                Log.e("NN:profileimage", strProfileImage);
                                Log.e("NN:screenid", strScreenId);
                                Log.e("NN:easymarryid", strEasyMarryId);
                                Log.e("NN:memplan", strMemPlan);
                                Log.e("NN:memvalid", strMemValid);

                                Intent i = new Intent(UserRegFive.this, UserRegSixAddPhoto.class);
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
                        //  Toast.makeText(UserRegFive.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg", res);

                                //  Toast.makeText(UserRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                if (str_eat.length() > 0) {
                    params.put("eatinghabits", str_eat);
                }
                if (str_drink.length() > 0) {
                    params.put("drinkinghabits", str_drink);
                }
                if (str_smoke.length() > 0) {
                    params.put("smokinghabits", str_smoke);
                }
                if (et_hobbies.getText().toString().trim().length() > 0) {
                    params.put("hobbies", et_hobbies.getText().toString().trim());
                }
                if (et_something_abt_you.getText().toString().trim().length() > 0) {
                    params.put("aboutyou", et_something_abt_you.getText().toString().trim());
                }
                if (str_user_id.length() > 0) {
                    params.put("userid", str_user_id);
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

//3Secs
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegFive.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        try {
            if (providerServicesMonth.length() > 0) {
                for (int i = 0; i < providerServicesMonth.length(); i++) {
                    ListDrawerBean drawerBean = new ListDrawerBean();
                    jsobj = providerServicesMonth.getJSONObject(i);
                    drawerBean.setStr_list_items_id(jsobj.getString("id"));
                    drawerBean.setStr_list_items(jsobj.getString("value"));
                    beanArrayList.add(drawerBean);
                }
            }

            listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, stridentifyEdit);
            listView.setAdapter(listDrawerAdapter);
            listDrawerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("hobbies")) {
            et_hobbies.setText(str_items);
            str_hobbie_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id,String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }
}
