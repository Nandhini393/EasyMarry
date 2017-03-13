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
import android.support.design.widget.TextInputLayout;
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
 * Created by android2 on 7/12/16.
 */
public class UserRegTwo extends Activity implements MyInterface {
    EditText et_religion, et_gowthram, et_caste, et_subcaste, et_zodiac, et_star, et_mother_ton, et_marital_status, et_childrens, et_search_profile_for;
    RadioButton rb_dhosham_yes, rb_dhosham_no, rb_dhosham_doesnt_matter, rb_other_comm_yes, rb_other_comm_no;
    Context c;
    Button btn_next;
    JSONArray json_religion;
    JSONArray json_gowthram;
    JSONArray json_caste;
    JSONArray json_zodiac;
    JSONArray json_marital_status;
    JSONArray json_mother_ton;
    JSONArray state;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    String str_religion_id = "", str_gowthram_id = "", str_caste_id = "", str_subcaste_id = "111", str_zodiac_id = "", str_star_id = "",
            str_dhosham_id = "2", str_mother_ton_id = "", str_marital_id = "", str_user_id = "", str_other_comm_id = "Yes",str_login_name,str_login_pwd;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ArrayList<ListDrawerBean> return_beanArrayList;
    ImageView img_back;
    GeneralData gD;
    TextView txt_error_religion, txt_error_caste, txt_error_gowthram, txt_error_subcaste, txt_error_zodiac, txt_error_star, txt_error_mother_ton,
            txt_error_marital_status, txt_error_children,txt_drawer_error_msg;
    boolean isClosed = false;
    TextInputLayout tll_gothram,tll_caste,tll_subcaste;
    ScrollView sv_myscroll;
    String str_resp="";
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user2);
        c = this;
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);
        str_login_name = gD.prefs.getString("login_uname", null);
        str_login_pwd = gD.prefs.getString("login_pword", null);
        tll_caste=(TextInputLayout)findViewById(R.id.input_layout_caste);
        tll_subcaste=(TextInputLayout)findViewById(R.id.input_layout_subcaste);
        sv_myscroll = (ScrollView) findViewById(R.id.myscroll);
        tll_gothram = (TextInputLayout) findViewById(R.id.input_layout_gothram);
        img_back = (ImageView) findViewById(R.id.img_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_religion = (EditText) findViewById(R.id.et_religion);
        et_gowthram = (EditText) findViewById(R.id.et_gothram);
        et_caste = (EditText) findViewById(R.id.et_caste);
        et_subcaste = (EditText) findViewById(R.id.et_subcaste);
        et_zodiac = (EditText) findViewById(R.id.et_zodiac);
        et_star = (EditText) findViewById(R.id.et_star);
        et_mother_ton = (EditText) findViewById(R.id.et_mother_ton);
        et_marital_status = (EditText) findViewById(R.id.et_marital_status);
        et_childrens = (EditText) findViewById(R.id.et_no_of_children);
        rb_dhosham_yes = (RadioButton) findViewById(R.id.radio_dhosam_yes);
        rb_dhosham_no = (RadioButton) findViewById(R.id.radio_dhosam_no);
        rb_dhosham_doesnt_matter = (RadioButton) findViewById(R.id.radio_dhosam_doesnt_matter);
        rb_other_comm_yes = (RadioButton) findViewById(R.id.radio_comm_yes);
        rb_other_comm_no = (RadioButton) findViewById(R.id.radio_comm_no);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        txt_error_religion = (TextView) findViewById(R.id.txt_error_religion);
        txt_error_caste = (TextView) findViewById(R.id.txt_error_caste);
        txt_error_gowthram = (TextView) findViewById(R.id.txt_error_gothram);
        txt_error_subcaste = (TextView) findViewById(R.id.txt_error_subcaste);
        txt_error_zodiac = (TextView) findViewById(R.id.txt_error_zodiac);
        txt_error_star = (TextView) findViewById(R.id.txt_error_star);
        txt_error_mother_ton = (TextView) findViewById(R.id.txt_error_mother_ton);
        txt_error_marital_status = (TextView) findViewById(R.id.txt_error_marital_status);
        txt_error_children = (TextView) findViewById(R.id.txt_error_child);
        txt_drawer_error_msg=(TextView)findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        et_religion.setFocusable(false);
        et_gowthram.setFocusable(false);
        et_caste.setFocusable(false);
        et_subcaste.setFocusable(false);
        et_zodiac.setFocusable(false);
        et_star.setFocusable(false);
        et_mother_ton.setFocusable(false);
        et_marital_status.setFocusable(false);
        et_caste.setEnabled(false);
        et_subcaste.setEnabled(false);
        et_star.setEnabled(false);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(c)
                        .inflate(R.layout.custom_incomplete_reg_popup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.show();
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView txt_title = (TextView) itemView1.findViewById(R.id.txt_title);
                TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
                txt_title.setText("Profile Incomplete");
                txt_content.setText("Do you want to continue now?");
                Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
                btn_continue.setText("Yes");
                Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
                btn_exit.setText("Later");
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor prefEdit = gD.prefs.edit();
                        prefEdit.putString("login_uname",null);
                        prefEdit.putString("login_pword",null);
                        prefEdit.commit();
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

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    Log.i("LK", "connected");
                    if (et_religion.getText().toString().length() == 0) {
                        txt_error_religion.setVisibility(View.VISIBLE);
                        txt_error_religion.setText("Select religion");
                        txt_error_religion.setTextColor(Color.parseColor("#ff0000"));
                        sv_myscroll.fullScroll(View.FOCUS_UP);
                    } else if (et_caste.getText().toString().length() == 0) {
                        txt_error_caste.setVisibility(View.VISIBLE);
                        txt_error_caste.setText("Select caste");
                        txt_error_caste.setTextColor(Color.parseColor("#ff0000"));
                        sv_myscroll.fullScroll(View.FOCUS_UP);
                    } /*else if (et_gowthram.getText().toString().length() == 0) {
                    txt_error_gowthram.setVisibility(View.VISIBLE);
                    txt_error_gowthram.setText("Select gothram");
                    txt_error_gowthram.setTextColor(Color.parseColor("#ff0000"));

                }*/

                    else if ((tll_subcaste.getVisibility() == View.VISIBLE)) {
                        Log.e("JN","Hii im visible");
                        Log.e("JN", "visiblitiy-->"+String.valueOf(tll_subcaste.getVisibility()));
                     if (et_subcaste.getText().toString().length() == 0) {
                         Log.e("JN","Hii im greater than 0");
                        txt_error_subcaste.setVisibility(View.VISIBLE);
                        txt_error_subcaste.setText("Select subcaste");
                        txt_error_subcaste.setTextColor(Color.parseColor("#ff0000"));
                        sv_myscroll.fullScroll(View.FOCUS_UP);
                    }
                     else if (et_mother_ton.getText().toString().length() == 0) {
                         txt_error_mother_ton.setVisibility(View.VISIBLE);
                         txt_error_mother_ton.setText("Select mother tongue");
                         txt_error_mother_ton.setTextColor(Color.parseColor("#ff0000"));

                     } else if (et_marital_status.getText().toString().length() == 0) {
                         txt_error_marital_status.setVisibility(View.VISIBLE);
                         txt_error_marital_status.setText("Select maritial status");
                         txt_error_marital_status.setTextColor(Color.parseColor("#ff0000"));

                     } else if ((et_childrens.getVisibility() == View.VISIBLE)) {
                         if (et_childrens.getText().toString().length() == 0) {
                             txt_error_children.setVisibility(View.VISIBLE);
                             txt_error_children.setText("Enter Childrens");
                             txt_error_children.setTextColor(Color.parseColor("#ff0000"));

                         } else {
                             registerCall();
                         }
                     }
                     else {
                         registerCall();
                     }
                }/*else if (et_zodiac.getText().toString().length() == 0) {
                    txt_error_zodiac.setVisibility(View.VISIBLE);
                    txt_error_zodiac.setText("Select zodiac");
                    txt_error_zodiac.setTextColor(Color.parseColor("#ff0000"));
                } else if (et_star.getText().toString().length() == 0) {
                    txt_error_star.setVisibility(View.VISIBLE);
                    txt_error_star.setText("Select star");
                    txt_error_star.setTextColor(Color.parseColor("#ff0000"));
                }*/ else {
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

        et_religion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_religion.getVisibility() == View.VISIBLE) {
                    txt_error_religion.setVisibility(View.GONE);
                    et_religion.setPadding(0, 0, 0, 10);
                    et_religion.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_caste.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_caste.getVisibility() == View.VISIBLE) {
                    txt_error_caste.setVisibility(View.GONE);
                    et_caste.setPadding(0, 0, 0, 10);
                    et_caste.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  et_gowthram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_gowthram.getVisibility() == View.VISIBLE) {
                    txt_error_gowthram.setVisibility(View.GONE);
                    et_gowthram.setPadding(0, 0, 0, 10);
                    et_gowthram.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        et_subcaste.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_subcaste.getVisibility() == View.VISIBLE) {
                    txt_error_subcaste.setVisibility(View.GONE);
                    et_subcaste.setPadding(0, 0, 0, 10);
                    et_subcaste.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    /*    et_zodiac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_zodiac.getVisibility() == View.VISIBLE) {
                    txt_error_zodiac.setVisibility(View.GONE);
                    et_zodiac.setPadding(0, 0, 0, 10);
                    et_zodiac.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_star.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_star.getVisibility() == View.VISIBLE) {
                    txt_error_star.setVisibility(View.GONE);
                    et_star.setPadding(0, 0, 0, 10);
                    et_star.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        et_mother_ton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_mother_ton.getVisibility() == View.VISIBLE) {
                    txt_error_mother_ton.setVisibility(View.GONE);
                    et_mother_ton.setPadding(0, 0, 0, 10);
                    et_mother_ton.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_marital_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_marital_status.getVisibility() == View.VISIBLE) {
                    txt_error_marital_status.setVisibility(View.GONE);
                    et_marital_status.setPadding(0, 0, 0, 10);
                    et_marital_status.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_childrens.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_error_children.getVisibility() == View.VISIBLE) {
                    txt_error_children.setVisibility(View.GONE);
                    et_childrens.setPadding(0, 0, 0, 10);
                    et_childrens.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rb_dhosham_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_yes.isChecked()) {
                    str_dhosham_id = "1";
                    rb_dhosham_no.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                }
            }
        });
        rb_dhosham_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_no.isChecked()) {
                    str_dhosham_id = "2";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                }
            }
        });
        rb_dhosham_doesnt_matter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_doesnt_matter.isChecked()) {
                    str_dhosham_id = "3";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_no.setChecked(false);
                }
            }
        });
        rb_other_comm_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_other_comm_yes.isChecked()) {
                    str_other_comm_id = "Yes";
                }
            }
        });
        rb_other_comm_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_other_comm_no.isChecked()) {
                    str_other_comm_id = "No";
                }
            }
        });



        et_mother_ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    Log.i("LK", "connected");
                    et_search_profile_for.setHint("Mother tongue");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_mother_ton, "mothertongue");
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


                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "mothertongue");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }


            }
        });
        et_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Religion");
                    Log.i("LK", "connected");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_religion, "religion");

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


                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "religion");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }



            }
        });


        et_caste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    Log.i("LK", "connected");
                    et_search_profile_for.setHint("Caste");
                    //final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_caste, "caste");
                    return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "caste", "religionid");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "caste");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();

                }
            }
        });
        et_subcaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    if(et_subcaste.getText().toString().trim().equalsIgnoreCase("Not Specified")){
                        et_subcaste.setEnabled(false);
                        et_subcaste.setTextColor(Color.parseColor("#000000"));
                    }
                    else{
                        myDrawerLayout.openDrawer(myLinearLayout);
                        et_search_profile_for.setText("");
                        Log.i("LK", "connected");
                        et_search_profile_for.setHint("Subcaste");
                        return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");
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

                                listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "subcaste");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }
            }
        });
        et_zodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();


                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    Log.i("LK", "connected");
                    et_search_profile_for.setHint("Zodiac");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_zodiac, "zodiac");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "zodiac");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }
            }
        });
        et_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();


                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    et_search_profile_for.setHint("Star");
                    Log.i("LK", "connected");
                    return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "star", "rasiid");


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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "star");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }
            }
        });
        et_marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();


                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    Log.i("LK", "connected");
                    et_search_profile_for.setHint("Marital status");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_marital_status, "marital_status");

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

                            listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "marital_status");
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
                    Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion=new JSONArray();
                    json_gowthram=new JSONArray();
                    json_caste =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();
                }
            }
        });
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
            Toast.makeText(UserRegTwo.this, "list is empty", Toast.LENGTH_SHORT).show();
        }
        return beanArrayList;

    }

    public ArrayList<ListDrawerBean> restCallForSubcaste(String str_url, final String str_id, final String str_type, final String stridType) {


        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                 JSONArray state = jsobj.getJSONArray("Response");

                                if (state.length() > 0) {
                                    if(str_type.equalsIgnoreCase("subcaste")){
                                        tll_subcaste.setVisibility(View.VISIBLE);
                                    }
                                    for (int i = 0; i < state.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = state.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        return_beanArrayList.add(drawerBean);
                                    }
                                }
                                else{
                                    if(str_type.equalsIgnoreCase("subcaste")){
                                        tll_subcaste.setVisibility(View.VISIBLE);
                                        et_subcaste.setText("Not Specified");
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
                        //  Toast.makeText(UserRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(UserRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(UserRegTwo.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();


        if (isConnected) {
            Log.i("LK", "connected");
            if (strIdentify.equalsIgnoreCase("religion")) {

                et_religion.setText(str_items);
                str_religion_id = str_items_id;

                if (str_religion_id.equalsIgnoreCase("1")) {
                    tll_gothram.setVisibility(View.VISIBLE);
                    et_gowthram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                            if (isConnected) {
                                myDrawerLayout.openDrawer(myLinearLayout);
                                et_search_profile_for.setText("");

                                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_gowthram, "gowthram");
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


                                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "gowthram");
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
                                Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                                txt_drawer_error_msg.setVisibility(View.VISIBLE);

                                json_religion=new JSONArray();
                                json_caste=new JSONArray();
                                json_gowthram =new JSONArray();
                                json_zodiac=new JSONArray();
                                json_marital_status=new JSONArray();
                                json_mother_ton=new JSONArray();


                            }
                        }
                    });


                } else {
                    tll_gothram.setVisibility(View.GONE);
                }
                et_caste.setEnabled(true);
                et_subcaste.setEnabled(false);
                et_subcaste.setText("");
                et_caste.setText("");
            }
            else if (strIdentify.equalsIgnoreCase("caste")) {
                et_caste.setText(str_items);
                str_caste_id = str_items_id;
                et_subcaste.setEnabled(true);
                et_subcaste.setText("");
                restCallForSubcaste(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");
            } else if (strIdentify.equalsIgnoreCase("subcaste")) {
                et_subcaste.setText(str_items);
                str_subcaste_id = str_items_id;
            } else if (strIdentify.equalsIgnoreCase("zodiac")) {
                et_zodiac.setText(str_items);
                str_zodiac_id = str_items_id;
                et_star.setEnabled(true);
            } else if (strIdentify.equalsIgnoreCase("star")) {
                et_star.setText(str_items);
                str_star_id = str_items_id;
            } else if (strIdentify.equalsIgnoreCase("mothertongue")) {
                et_mother_ton.setText(str_items);
                str_mother_ton_id = str_items_id;
            } else if (strIdentify.equalsIgnoreCase("marital_status")) {
                et_marital_status.setText(str_items);
                str_marital_id = str_items_id;
                if (str_marital_id.equalsIgnoreCase("2") || str_marital_id.equalsIgnoreCase("3") || str_marital_id.equalsIgnoreCase("4")) {
                    et_childrens.setVisibility(View.VISIBLE);
                } else {
                    et_childrens.setVisibility(View.GONE);
                }
            } else if (strIdentify.equalsIgnoreCase("gowthram")) {
                et_gowthram.setText(str_items);
                str_gowthram_id = str_items_id;
            }
            Log.i("VD", "str_religion_id : " + str_religion_id);
            Log.i("VD", "str_caste_id : " + str_caste_id);
            Log.i("VD", "str_subcaste_id : " + str_subcaste_id);
            Log.i("VD", "str_zodiac_id : " + str_zodiac_id);
            Log.i("VD", "str_star_id : " + str_star_id);
            Log.i("VD", "str_mother_ton_id : " + str_mother_ton_id);
            Log.i("VD", "str_marital_id : " + str_marital_id);
            Log.i("VD", "str_gowthram_id : " + str_gowthram_id);
        }
        else{
            Toast.makeText(UserRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
            txt_drawer_error_msg.setVisibility(View.VISIBLE);
            json_religion=new JSONArray();
            json_gowthram=new JSONArray();
            json_caste =new JSONArray();
            json_zodiac=new JSONArray();
            json_marital_status=new JSONArray();
            json_mother_ton=new JSONArray();
        }
        myDrawerLayout.closeDrawers();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
            builder.setCancelable(false);

            /*View itemView1 = LayoutInflater.from(c)
                    .inflate(R.layout.progress_loading_popup, null);
            final android.support.v7.app.AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            TextView txt_continue = (TextView) itemView1.findViewById(R.id.txt_yes);
            TextView txt_exit = (TextView) itemView1.findViewById(R.id.txt_later);
            txt_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    isClosed = true;
                }

            });

            txt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    altDialog.dismiss();
                    isClosed = false;
                }
            });*/

            View itemView1 = LayoutInflater.from(c)
                    .inflate(R.layout.custom_incomplete_reg_popup, null);
            final android.support.v7.app.AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView txt_title = (TextView) itemView1.findViewById(R.id.txt_title);
            TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
            txt_title.setText("Profile Incomplete");
            txt_content.setText("Do you want to continue now?");
            Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
            btn_continue.setText("Yes");
            Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
            btn_exit.setText("Later");
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    isClosed = true;
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("login_uname",null);
                    prefEdit.putString("login_pword",null);
                    prefEdit.commit();
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

    @Override
    public String get_matches(String str_id, String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }


    public void registerCall() {
        gD.showAlertDialog(c,"Registering", "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register2_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
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
                                startActivity(new Intent(UserRegTwo.this, UserRegThree.class));
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
                        // Toast.makeText(UserRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg", res);

                                gD.altDialog.dismiss();
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


               /* Log.e("reg_2: religion", str_religion_id);
              //  Log.e("reg_2: gothram", str_gowthram_id);
                Log.e("reg_2: caste", str_caste_id);
                Log.e("reg_2: subcaste", str_subcaste_id);
              //  Log.e("reg_2: zodiac", str_zodiac_id);
                Log.e("reg_2: dhosham", str_dhosham_id);
                Log.e("reg_2: mothertongue", str_mother_ton_id);
                Log.e("reg_2: maritalstatus", str_marital_id);
                Log.e("reg_2: othercommunity", str_other_comm_id);
                Log.e("reg_2: userid", str_user_id);*/
                //  Log.e("reg_2: star", str_star_id);
                //  Log.e("reg_2: noofchild", et_childrens.getText().toString().trim());


                params.put("religion", str_religion_id);
                params.put("caste", str_caste_id);
                params.put("subcaste", str_subcaste_id);

                if (str_zodiac_id.length() > 0) {
                    params.put("zodiac", str_zodiac_id);
                }
                if (str_gowthram_id.length() > 0) {
                    params.put("gothram", str_gowthram_id);

                }
                if (str_star_id.length() > 0) {
                    params.put("star", str_star_id);

                }
                if (str_dhosham_id.length() > 0) {
                    params.put("dhosham", str_dhosham_id);

                }
                if (et_childrens.getText().toString().trim().length() > 0) {
                    params.put("noofchild", et_childrens.getText().toString().trim());

                }

                params.put("mothertongue", str_mother_ton_id);
                params.put("maritalstatus", str_marital_id);
                params.put("othercommunity", str_other_comm_id);
                params.put("userid", str_user_id);

                //   religion=1&gothram=&caste=1&subcaste=2&zodiac=&dhosham=&mothertongue=2&maritalstatus=2&othercommunity=Yes&userid=273&star=2


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
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegTwo.this);
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

                                        JSONArray json_array_religion = jsobj.getJSONArray("religion");

                                        json_religion = jsobj.getJSONArray("religion");
                                        json_gowthram = jsobj.getJSONArray("Gowthram");
                                        //   json_caste = jsobj.getJSONArray("Caste");
                                        json_zodiac = jsobj.getJSONArray("Zodiac");
                                        json_marital_status = jsobj.getJSONArray("martialstatus");
                                        json_mother_ton = jsobj.getJSONArray("mothertongue");
                             /*   str_profile_for_id = profile_created_by.getJSONObject(0).getString("id");
                                str_religion_id = jsonArray_religion.getJSONObject(0).getString("id");
                                str_mother_tonngue_id = jsonArray_mother_tongue.getJSONObject(0).getString("id");*/


                                        if (json_religion.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_religion.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "religion");
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
                                //Toast.makeText(UserRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        // Toast.makeText(UserRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(UserRegTwo.this);
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
