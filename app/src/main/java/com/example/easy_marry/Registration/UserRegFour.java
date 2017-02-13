package com.example.easy_marry.Registration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
public class UserRegFour extends Activity implements MyInterface {
    Button btn_next;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    Context c;
    GeneralData gD;
    ImageView img_back;
    TextView txt_error_height, txt_error_weight, txt_error_phy_stat, txt_error_complex,
            txt_error_body_type, txt_error_fam_val, txt_error_fam_type,
            txt_error_fam_stat, txt_error_father_occup, txt_error_mother_occup,txt_drawer_error_msg;
    String str_user_id="", str_height_id="", str_weight_id="",
            str_phy_status_id="", str_complex_id="", str_body_type_id="",
            str_fam_val_id="", str_fam_type_id="", str_fam_stat_id="", str_father_occup_id="", str_mother_occup_id="";
    EditText et_search_profile_for, et_height, et_weight, et_phy_status, et_complexion, et_body_type, et_fam_val, et_fam_type, et_fam_status, et_father_occup, et_mother_occup;
    JSONArray json_height, json_weight, json_phy_status, json_complextion, json_body_type, json_fam_val, json_fam_type, json_fam_status, json_father_occup, json_mother_occup;
    boolean isClosed = false;
    ScrollView myscroll;
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user4);
        c = this;
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);
        myscroll=(ScrollView)findViewById(R.id.myscroll);
        img_back = (ImageView) findViewById(R.id.img_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_phy_status = (EditText) findViewById(R.id.et_phy_status);
        et_complexion = (EditText) findViewById(R.id.et_complextion);
        et_body_type = (EditText) findViewById(R.id.et_body_type);
        et_fam_val = (EditText) findViewById(R.id.et_fam_val);
        et_fam_type = (EditText) findViewById(R.id.et_fam_type);
        et_fam_status = (EditText) findViewById(R.id.et_fam_status);
        et_father_occup = (EditText) findViewById(R.id.et_father_occ);
        et_mother_occup = (EditText) findViewById(R.id.et_mother_occ);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        txt_error_height = (TextView) findViewById(R.id.txt_error_height);
        txt_error_weight = (TextView) findViewById(R.id.txt_error_weight);
        txt_error_phy_stat = (TextView) findViewById(R.id.txt_error_phy_stat);
        txt_error_complex = (TextView) findViewById(R.id.txt_error_complex);

        txt_error_body_type = (TextView) findViewById(R.id.txt_error_body_type);
        txt_error_fam_val = (TextView) findViewById(R.id.txt_error_fam_val);
        txt_error_fam_stat = (TextView) findViewById(R.id.txt_error_fam_stat);
        txt_error_fam_type = (TextView) findViewById(R.id.txt_error_fam_type);
        txt_error_father_occup = (TextView) findViewById(R.id.txt_error_father_occup);
        txt_error_mother_occup = (TextView) findViewById(R.id.txt_error_mother_occup);
        txt_drawer_error_msg=(TextView)findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        et_fam_status.setFocusable(false);
        et_fam_type.setFocusable(false);
        et_fam_val.setFocusable(false);
        et_body_type.setFocusable(false);
        et_complexion.setFocusable(false);
        et_phy_status.setFocusable(false);
        et_height.setFocusable(false);
        et_weight.setFocusable(false);
        et_father_occup.setFocusable(false);
        et_mother_occup.setFocusable(false);
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

                    }

                });

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();

                    }
                });
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    Log.i("LK", "connected");
                    if (et_height.getText().toString().length() == 0) {
                        et_height.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_height.setText("select height");
                        txt_error_height.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_height.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    } else if (et_weight.getText().toString().length() == 0) {
                        et_weight.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_weight.setText("select weight");
                        txt_error_weight.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_weight.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    } else if (et_phy_status.getText().toString().length() == 0) {
                        et_phy_status.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_phy_stat.setText("select physical status");
                        txt_error_phy_stat.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_phy_stat.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    } else if (et_complexion.getText().toString().length() == 0) {
                        et_complexion.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_complex.setText("select complextion");
                        txt_error_complex.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_complex.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    } else if (et_body_type.getText().toString().length() == 0) {
                        et_body_type.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_body_type.setText("select body type");
                        txt_error_body_type.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_body_type.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    }
               /* else if (et_fam_val.getText().toString().length() == 0) {
                    et_fam_val.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_fam_val.setText("select family values");
                    txt_error_fam_val.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_fam_val.setVisibility(View.VISIBLE);

                }
                else if (et_fam_type.getText().toString().length() == 0) {
                    et_fam_type.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_fam_type.setText("select family type");
                    txt_error_fam_type.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_fam_type.setVisibility(View.VISIBLE);

                }
                else if (et_fam_status.getText().toString().length() == 0) {
                    et_fam_status.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_fam_stat.setText("select family status");
                    txt_error_fam_stat.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_fam_stat.setVisibility(View.VISIBLE);

                }
                else if (et_father_occup.getText().toString().length() == 0) {
                    et_father_occup.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_father_occup.setText("select father occupation");
                    txt_error_father_occup.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_father_occup.setVisibility(View.VISIBLE);

                }
                else if (et_mother_occup.getText().toString().length() == 0) {
                    et_mother_occup.setBackgroundResource(R.drawable.edit_single_red);
                    txt_error_mother_occup.setText("select mother occupation");
                    txt_error_mother_occup.setTextColor(Color.parseColor("#ff0000"));
                    txt_error_mother_occup.setVisibility(View.VISIBLE);

                }*/
                    else {
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
        et_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_height.getVisibility() == View.VISIBLE) {
                    txt_error_height.setVisibility(View.GONE);
                    et_height.setPadding(0, 0, 0, 10);
                    et_height.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_weight.getVisibility() == View.VISIBLE) {
                    txt_error_weight.setVisibility(View.GONE);
                    et_weight.setPadding(0, 0, 0, 10);
                    et_weight.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_phy_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_phy_stat.getVisibility() == View.VISIBLE) {
                    txt_error_phy_stat.setVisibility(View.GONE);
                    et_phy_status.setPadding(0, 0, 0, 10);
                    et_phy_status.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_complexion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_complex.getVisibility() == View.VISIBLE) {
                    txt_error_complex.setVisibility(View.GONE);
                    et_complexion.setPadding(0, 0, 0, 10);
                    et_complexion.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_body_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_body_type.getVisibility() == View.VISIBLE) {
                    txt_error_body_type.setVisibility(View.GONE);
                    et_body_type.setPadding(0, 0, 0, 10);
                    et_body_type.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_fam_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_fam_val.getVisibility() == View.VISIBLE) {
                    txt_error_fam_val.setVisibility(View.GONE);
                    et_fam_val.setPadding(0, 0, 0, 10);
                    et_fam_val.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_fam_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_fam_type.getVisibility() == View.VISIBLE) {
                    txt_error_fam_type.setVisibility(View.GONE);
                    et_fam_type.setPadding(0, 0, 0, 10);
                    et_fam_type.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_fam_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_fam_stat.getVisibility() == View.VISIBLE) {
                    txt_error_fam_stat.setVisibility(View.GONE);
                    et_fam_status.setPadding(0, 0, 0, 10);
                    et_fam_status.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_father_occup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_father_occup.getVisibility() == View.VISIBLE) {
                    txt_error_father_occup.setVisibility(View.GONE);
                    et_father_occup.setPadding(0, 0, 0, 10);
                    et_father_occup.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_mother_occup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_mother_occup.getVisibility() == View.VISIBLE) {
                    txt_error_mother_occup.setVisibility(View.GONE);
                    et_mother_occup.setPadding(0, 0, 0, 10);
                    et_mother_occup.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Height");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_height, "height");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "height");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }


        });

        et_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Weight");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_weight, "weight");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "weight");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });

        et_phy_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Physical status");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_phy_status, "physical_status");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "physical_status");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });


        et_complexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Complexion");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_complextion, "complexion");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "complexion");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });
        et_body_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Body type");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_body_type, "body_Type");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "body_Type");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });
        et_fam_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Family values");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_fam_val, "family_values");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_values");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });

        et_fam_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Family type");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_fam_type, "family_type");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_type");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });
        et_fam_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Family status");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_fam_status, "family_status");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_status");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });
        et_father_occup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Father occupation");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_father_occup, "father_occup");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "father_occup");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });
        et_mother_occup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Mother occupation");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_mother_occup, "mother_occup");
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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "mother_occup");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(UserRegFour.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height=new JSONArray();
                    json_weight=new JSONArray();
                    json_phy_status =new JSONArray();
                    json_complextion=new JSONArray();
                    json_body_type=new JSONArray();
                    json_fam_val=new JSONArray();
                    json_fam_type =new JSONArray();
                    json_fam_status=new JSONArray();
                    json_father_occup=new JSONArray();
                    json_mother_occup=new JSONArray();
                }
            }
        });


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

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        if(providerServicesMonth!=null) {
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
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(listDrawerAdapter);
            listDrawerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        else{
            Toast.makeText(UserRegFour.this, "list is empty", Toast.LENGTH_SHORT).show();
        }
        return beanArrayList;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("height")) {
            et_height.setText(str_items);
            str_height_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_weight.setText(str_items);
            str_weight_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("physical_status")) {
            et_phy_status.setText(str_items);
            str_phy_status_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("complexion")) {
            et_complexion.setText(str_items);
            str_complex_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("body_type")) {
            et_body_type.setText(str_items);
            str_body_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_values")) {
            et_fam_val.setText(str_items);
            str_fam_val_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_type")) {
            et_fam_type.setText(str_items);
            str_fam_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_status")) {
            et_fam_status.setText(str_items);
            str_fam_stat_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("father_occup")) {
            et_father_occup.setText(str_items);
            str_father_occup_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("mother_occup")) {
            et_mother_occup.setText(str_items);
            str_mother_occup_id = str_items_id;
        }

        Log.e("reg_4: height", str_height_id);
        Log.e("reg_4: weight", str_weight_id);
        Log.e("reg_4: physicalstatus", str_phy_status_id);
        Log.e("reg_4: complexion", str_complex_id);
        Log.e("reg_4: bodytype", str_body_type_id);
        Log.e("reg_4: familyvalue",str_fam_val_id);
        Log.e("reg_4: familytype",str_fam_type_id);
        Log.e("reg_4: familystatus",str_fam_stat_id);
        Log.e("reg_4: fatheroccupation",str_father_occup_id);
        Log.e("reg_4: motheroccupation",str_mother_occup_id);
        Log.e("reg_4: userid",str_user_id);
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id,String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal) {
        return null;
    }

    public void registerCall() {
        gD.showAlertDialog(c,"Registering", "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register4_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //    Toast.makeText(UserRegFour.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                gD.altDialog.dismiss();
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

                                startActivity(new Intent(UserRegFour.this, UserRegFive.class));
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
                /*        //  Toast.makeText(UserRegFour.this, error.toString(), Toast.LENGTH_LONG).show();
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
                        }*/

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();





                params.put("height", str_height_id);
                params.put("weight", str_weight_id);
                params.put("physicalstatus", str_phy_status_id);
                params.put("complexion", str_complex_id);
                params.put("bodytype", str_body_type_id);


                if (str_fam_val_id.length() > 0) {
                    params.put("familyvalue", str_fam_val_id);
                }
                if (str_fam_type_id.length() > 0) {
                    params.put("familytype", str_fam_type_id);
                }
                if (str_fam_stat_id.length() > 0) {
                    params.put("familystatus", str_fam_stat_id);
                }
                if (str_father_occup_id.length() > 0) {
                    params.put("fatheroccupation", str_father_occup_id);
                }
                if (str_mother_occup_id.length() > 0) {
                    params.put("motheroccupation", str_mother_occup_id);
                }

                params.put("userid", str_user_id);

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
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegFour.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            context = GeneralData.context;
            if (isConnected) {
                Log.i("LK", "connected");
              /*  if (nAttempt == 0) {*/

                //Toast.makeText(UserRegOne.this, "Internet connected", Toast.LENGTH_SHORT).show();
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

                                        JSONArray json_array_height = jsobj.getJSONArray("Height");
                                        json_height = jsobj.getJSONArray("Height");
                                        json_weight = jsobj.getJSONArray("weight");
                                        json_phy_status = jsobj.getJSONArray("Physical status");
                                        json_complextion = jsobj.getJSONArray("Complexion");
                                        json_body_type = jsobj.getJSONArray("Body Type");
                                        json_fam_type = jsobj.getJSONArray("family type");
                                        json_fam_val = jsobj.getJSONArray("family value");
                                        json_fam_status = jsobj.getJSONArray("Family Status");
                                        json_father_occup = jsobj.getJSONArray("occupation");
                                        json_mother_occup = jsobj.getJSONArray("occupation");

                                        if (json_height.length() > 0) {
                                            for (int i = 0; i < json_array_height.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_height.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "height");
                                        listView.setVisibility(View.VISIBLE);
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
                                //   Toast.makeText(UserRegFour.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        //  Toast.makeText(UserRegFour.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(UserRegFour.this);
                requestQueue.add(stringRequest);

             /*   }
                nAttempt = 1;*/

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
    };
}
