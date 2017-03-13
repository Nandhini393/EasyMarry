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
public class UserRegThree extends Activity implements MyInterface {
    Button btn_next;
    EditText et_qualif, et_add_info, et_occup, et_emp_in, et_annual_income, et_country, et_state, et_city, et_citizen, et_search_profile_for;
    JSONArray json_qualif, json_occup, json_emp_in, json_annual_income, json_country, json_citizen;
    String str_qualif_id="", str_user_id="", str_occup_id="", str_emp_in_id="", str_annual_income_id="", str_country_id="",
            str_state_id="", str_city_id="", str_citizen_id="";
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    Context c;
    ArrayList<ListDrawerBean> return_beanArrayList;
    ScrollView myscroll;
    GeneralData gD;
    IntentFilter filter1;
    ImageView img_back;
    TextView txt_error_country, txt_error_qualif,txt_error_occup,txt_error_emp_in,txt_error_annual_income,
    txt_error_state,txt_error_city,txt_error_citizen,txt_drawer_error_msg;
    boolean isClosed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user3);
        c = this;
        gD= new GeneralData(c);
        img_back=(ImageView)findViewById(R.id.img_back);
        myscroll = (ScrollView) findViewById(R.id.my_scroll);
        str_user_id=gD.prefs.getString("userid",null);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_qualif = (EditText) findViewById(R.id.et_qualif);
        et_add_info = (EditText) findViewById(R.id.et_add_info);
        et_occup = (EditText) findViewById(R.id.et_occupation);
        et_emp_in = (EditText) findViewById(R.id.et_emp_in);
        et_annual_income = (EditText) findViewById(R.id.et_annual_income);
        et_country = (EditText) findViewById(R.id.et_country);
        et_state = (EditText) findViewById(R.id.et_state);
        et_city = (EditText) findViewById(R.id.et_city);
        et_citizen = (EditText) findViewById(R.id.et_citizen);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);

        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        txt_error_country = (TextView) findViewById(R.id.txt_error_country);
        txt_error_qualif = (TextView) findViewById(R.id.txt_error_qualif);
        txt_error_occup=(TextView)findViewById(R.id.txt_error_occup);
        txt_error_emp_in=(TextView)findViewById(R.id.txt_error_emp_in) ;
        txt_error_annual_income=(TextView)findViewById(R.id.txt_error_annual_income);
        txt_error_state=(TextView)findViewById(R.id.txt_error_state);
        txt_error_city=(TextView)findViewById(R.id.txt_error_city);
        txt_error_citizen=(TextView)findViewById(R.id.txt_error_citizen);

        txt_drawer_error_msg=(TextView)findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);

        et_qualif.setFocusable(false);
        et_add_info.setCursorVisible(false);
        et_occup.setFocusable(false);
        et_emp_in.setFocusable(false);
        et_annual_income.setFocusable(false);
        et_country.setFocusable(false);
        et_state.setFocusable(false);
        et_city.setFocusable(false);
        et_citizen.setFocusable(false);
       et_state.setEnabled(false);
        et_city.setEnabled(false);

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
                    if (et_qualif.getText().toString().length() == 0) {
                        et_qualif.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_qualif.setText("select qualification");
                        txt_error_qualif.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_qualif.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);

                    } else if (et_occup.getText().toString().length() == 0) {
                        et_occup.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_occup.setText("select occcupation");
                        txt_error_occup.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_occup.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);
                    } else if (et_emp_in.getText().toString().length() == 0) {
                        et_emp_in.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_emp_in.setText("select employed in");
                        txt_error_emp_in.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_emp_in.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);
                    } else if (et_annual_income.getText().toString().length() == 0) {
                        et_annual_income.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_annual_income.setText("select annual income");
                        txt_error_annual_income.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_annual_income.setVisibility(View.VISIBLE);
                        myscroll.fullScroll(View.FOCUS_UP);
                    } else if (et_country.getText().toString().length() == 0) {
                        et_country.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_country.setText("select country");
                        txt_error_country.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_country.setVisibility(View.VISIBLE);

                    } else if (et_state.getText().toString().length() == 0) {
                        et_state.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_state.setText("select state");
                        txt_error_state.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_state.setVisibility(View.VISIBLE);

                    } else if (et_city.getText().toString().length() == 0) {
                        et_city.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_city.setText("select city");
                        txt_error_city.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_city.setVisibility(View.VISIBLE);

                    } else if (et_citizen.getText().toString().length() == 0) {
                        et_citizen.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_citizen.setText("select citizenship");
                        txt_error_citizen.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_citizen.setVisibility(View.VISIBLE);

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

        et_qualif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_qualif.getVisibility() == View.VISIBLE) {
                    txt_error_qualif.setVisibility(View.GONE);
                    et_qualif.setPadding(0, 0, 0, 10);
                    et_qualif.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_occup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_occup.getVisibility() == View.VISIBLE) {
                    txt_error_occup.setVisibility(View.GONE);
                    et_occup.setPadding(0, 0, 0, 10);
                    et_occup.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_emp_in.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_emp_in.getVisibility() == View.VISIBLE) {
                    txt_error_emp_in.setVisibility(View.GONE);
                    et_emp_in.setPadding(0, 0, 0, 10);
                    et_emp_in.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_annual_income.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_annual_income.getVisibility() == View.VISIBLE) {
                    txt_error_annual_income.setVisibility(View.GONE);
                    et_annual_income.setPadding(0, 0, 0, 10);
                    et_annual_income.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_country.getVisibility() == View.VISIBLE) {
                    txt_error_country.setVisibility(View.GONE);
                    et_country.setPadding(0, 0, 0, 10);
                    et_country.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_state.getVisibility() == View.VISIBLE) {
                    txt_error_state.setVisibility(View.GONE);
                    et_state.setPadding(0, 0, 0, 10);
                    et_state.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_city.getVisibility() == View.VISIBLE) {
                    txt_error_city.setVisibility(View.GONE);
                    et_city.setPadding(0, 0, 0, 10);
                    et_city.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_citizen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_citizen.getVisibility() == View.VISIBLE) {
                    txt_error_citizen.setVisibility(View.GONE);
                    et_citizen.setPadding(0, 0, 0, 10);
                    et_citizen.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        et_qualif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {

                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Qualification");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_qualif, "qualification");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "qualification");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });

        et_occup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Occupation");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_occup, "occupation");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "occupation");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });
        et_emp_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Employed in");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_emp_in, "emp_in");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "emp_in");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });
        et_annual_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Annual income");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_annual_income, "annual_income");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "annual_income");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });
        et_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Country");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_country, "country");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "country");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });
        et_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {

                    if (et_state.isEnabled()) {

                        et_search_profile_for.setHint("State");
                        et_search_profile_for.setText("");

                        Log.i("TS", "Country_Id : " + str_country_id);

                        return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "state", "countryid");

                        myDrawerLayout.openDrawer(myLinearLayout);

                        et_search_profile_for.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String strVal = s.toString().toLowerCase();
                                Log.i("AB", "Val : " + strVal);

                                final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                                for (int i = 0; i < return_beanArrayList.size(); i++) {

                                    String text = return_beanArrayList.get(i).getStr_list_items().toLowerCase();
                                    Log.i("AB", "text : " + text);

                                    if (text.contains(strVal)) {
                                        filteredList.add(return_beanArrayList.get(i));
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "state");
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged(); // data set changed
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    } else {
                        Toast.makeText(UserRegThree.this, "Select Country", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_emp_in = new JSONArray();
                    json_annual_income = new JSONArray();
                    json_country = new JSONArray();
                    json_citizen = new JSONArray();
                }
            }

        });


        et_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (!et_city.isEnabled()) {

                    Toast.makeText(UserRegThree.this, "Select State", Toast.LENGTH_SHORT).show();
                }
                else{
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("City");
                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "city", "stateid");

                    et_search_profile_for.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String strVal = s.toString().toLowerCase();
                            Log.i("AB", "Val : " + strVal);

                            final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                            for (int i = 0; i < return_beanArrayList.size(); i++) {

                                String text = return_beanArrayList.get(i).getStr_list_items().toLowerCase();
                                Log.i("AB", "text : " + text);

                                if (text.contains(strVal)) {
                                    filteredList.add(return_beanArrayList.get(i));
                                }
                            }

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "city");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

            }
            else{
                Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                txt_drawer_error_msg.setVisibility(View.VISIBLE);
                json_qualif=new JSONArray();
                json_occup=new JSONArray();
                json_emp_in =new JSONArray();
                json_annual_income=new JSONArray();
                json_country=new JSONArray();
                json_citizen=new JSONArray();
            }
            }
        });
        et_citizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Citizenship");
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_citizen, "citizen");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "citizen");
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
                    Toast.makeText(UserRegThree.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_qualif=new JSONArray();
                    json_occup=new JSONArray();
                    json_emp_in =new JSONArray();
                    json_annual_income=new JSONArray();
                    json_country=new JSONArray();
                    json_citizen=new JSONArray();
                }
            }
        });

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }
    public ArrayList<ListDrawerBean> restCallForStateCity(String str_url, final String str_id, final String str_type, final String stridType) {

        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(UserRegThree.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray state = jsobj.getJSONArray("Response");

                                if (state.length() > 0) {
                                    for (int i = 0; i < state.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = state.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        return_beanArrayList.add(drawerBean);
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(c, return_beanArrayList, (MyInterface) c, str_type);
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(UserRegThree.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(UserRegThree.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                params.put(stridType, str_id);


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


        RequestQueue requestQueue = Volley.newRequestQueue(UserRegThree.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
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
            Toast.makeText(UserRegThree.this, "list is empty", Toast.LENGTH_SHORT).show();
        }
        return beanArrayList;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("qualification")) {
            et_qualif.setText(str_items);
            str_qualif_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("occupation")) {
            et_occup.setText(str_items);
            str_occup_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("emp_in")) {
            et_emp_in.setText(str_items);
            str_emp_in_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("annual_income")) {
            et_annual_income.setText(str_items);
            str_annual_income_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("country")) {
            et_country.setText(str_items);
            str_country_id = str_items_id;
           /* if (txt_error_country.getVisibility() == View.VISIBLE) {
                txt_error_country.setVisibility(View.GONE);
                et_country.setBackgroundResource(R.drawable.edit_single);
                myscroll.fullScroll(View.FOCUS_UP);
            }*/
            et_state.setEnabled(true);
            et_city.setEnabled(false);
        } else if (strIdentify.equalsIgnoreCase("state")) {
            et_state.setText(str_items);
            str_state_id = str_items_id;
            et_city.setEnabled(true);
        } else if (strIdentify.equalsIgnoreCase("city")) {
            et_city.setText(str_items);
            str_city_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("citizen")) {
            et_citizen.setText(str_items);
            str_citizen_id = str_items_id;
        }
        Log.e("reg_3: qualification",str_qualif_id);
        Log.e("reg_3: addquali",et_add_info.getText().toString().trim());
        Log.e("reg_3: occupation",str_occup_id);
        Log.e("reg_3: employedin",str_emp_in_id);
        Log.e("reg_3: annualincome",str_annual_income_id);
        Log.e("reg_3: country",str_country_id);
        Log.e("reg_3: state",str_state_id);
        Log.e("reg_3: city",str_city_id);
        Log.e("reg_3: citizenship",str_citizen_id);
        Log.e("reg_3: userid",str_user_id);
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id,String str_partner_name, String strFrom, String str_type,  String str_sent_int,RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }

    public void registerCall() {
        gD.showAlertDialog(c,"Registering", "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register3_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                      //  Toast.makeText(UserRegThree.this, response, Toast.LENGTH_LONG).show();
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

                                startActivity(new Intent(UserRegThree.this,UserRegFour.class));
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
                        gD.altDialog.dismiss();
                     //   Toast.makeText(UserRegThree.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg",res);

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


                params.put("qualification",str_qualif_id );


                if (et_add_info.getText().toString().trim().length() > 0) {
                    params.put("additionalqualification", et_add_info.getText().toString().trim());
                }

                params.put("occupation",str_occup_id );
                params.put("employedin",str_emp_in_id );
                params.put("annualincome", str_annual_income_id);
                params.put("country", str_country_id);
                params.put("state",str_state_id);
                params.put("city",str_city_id );

                if (str_citizen_id.length() > 0) {
                    params.put("citizenship",str_citizen_id);
                }

                params.put("userid",str_user_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMar.ry2016").getBytes(), Base64.DEFAULT)));
// params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }

        };

//3Secs
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegThree.this);
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

                                        JSONArray json_array_religion = jsobj.getJSONArray("qualification");
                                        json_qualif = jsobj.getJSONArray("qualification");
                                        json_occup = jsobj.getJSONArray("occupation");
                                        json_emp_in = jsobj.getJSONArray("Employment");
                                        json_annual_income = jsobj.getJSONArray("Income");
                                        json_country = jsobj.getJSONArray("country");
                                        json_citizen = jsobj.getJSONArray("Citizenship");

                                        if (json_qualif.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_qualif.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "qualification");
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
                                // Toast.makeText(UserRegThree.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        //  Toast.makeText(UserRegThree.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(UserRegThree.this);
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
