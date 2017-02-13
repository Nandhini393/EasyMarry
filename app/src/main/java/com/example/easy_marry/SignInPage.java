package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.GCM.GCMClientManager;
import com.example.easy_marry.Registration.UserRegFive;
import com.example.easy_marry.Registration.UserRegFour;
import com.example.easy_marry.Registration.UserRegOne;
import com.example.easy_marry.Registration.UserRegThree;
import com.example.easy_marry.Registration.UserRegTwo;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android3 on 30/5/16.
 */
public class SignInPage extends Activity {
    TextInputLayout Txt_email, txt_password;
    EditText edt_email, edt_password;
    TextView txt_forget_pwd, txt_error_email;
    Button btn_signin, btn_signup;
    ProgressDialog dialog;
    Context ctx;
    ImageView img_facebook_login, img_home_back;
    String fb_id;
    private CallbackManager callbackManager;
    String strGCMid = "";
    String PROJECT_NUMBER = "311648921952";
    GeneralData gD;
    int nAttempt = 0;
    private CoordinatorLayout coordinatorLayout;
    IntentFilter filter1;
LinearLayout ll_SignInBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinpage);
        ctx = this;
        //ctx = GeneralData.context;

        gD = new GeneralData(ctx);
        Txt_email = (TextInputLayout) findViewById(R.id.email);
        txt_password = (TextInputLayout) findViewById(R.id.id_password);
        txt_forget_pwd = (TextView) findViewById(R.id.txt_forget_pwd);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_signin = (Button) findViewById(R.id.btn_sign_in);
        btn_signup = (Button) findViewById(R.id.btn_sign_up);
        ll_SignInBack=(LinearLayout)findViewById(R.id.ll_sign_in_back);
        Txt_email.setHint("Email"); //setting hint.
        txt_password.setHint("Password");
        txt_error_email = (TextView) findViewById(R.id.txt_error_email);
        dialog = new ProgressDialog(SignInPage.this);
      img_home_back = (ImageView) findViewById(R.id.img_home_back);


        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //registerReceiver(myReceiver, filter1);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            //profilePictureView.setProfileId(object.getString("id"));
                                            //edt_userName.setText(object.getString("name"));
                                            Log.e("fb_id", object.getString("id"));
                                            fb_id = object.getString("id");

                                            // Toast.makeText(LoginPage.this, "fb id-->" + object.getString("id"), Toast.LENGTH_SHORT).show();
                                            // Log.e("FBname", object.getString("name"));

                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        //Toast.makeText(LoginPage.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // Toast.makeText(LoginPage.this, exception.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_email.getVisibility() == View.VISIBLE) {
                    txt_error_email.setVisibility(View.GONE);
                    edt_email.setPadding(0, 10, 0, 8);
                    edt_email.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_email.setText("charu93@gmail.com");
        edt_password.setText("1234567");

        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO do something

                    ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                    if (isConnected) {
                        Log.i("LK", "connected");
                        //Toast.makeText(SignInPage.this, "Internet connected", Toast.LENGTH_SHORT).show();

                        if (edt_email.getText().toString().trim().length() == 0) {
                            Toast.makeText(SignInPage.this, "enter email", Toast.LENGTH_SHORT).show();
                        } else if (edt_password.getText().toString().trim().length() == 0) {
                            Toast.makeText(SignInPage.this, "enter password", Toast.LENGTH_SHORT).show();
                        } else if (!isValidEmail(edt_email.getText().toString().trim())) {

                            edt_email.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_email.setText("Invalid E-mail");
                            txt_error_email.setTextColor(Color.parseColor("#ffffff"));
                            txt_error_email.setVisibility(View.VISIBLE);
                        } else {

                            loginCall("no_social");

                        }


                    } else {

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

                    handled = true;
                }
                return handled;

            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    Log.i("LK", "connected");
                    //Toast.makeText(SignInPage.this, "Internet connected", Toast.LENGTH_SHORT).show();

                        if (edt_email.getText().toString().trim().length() == 0) {
                            Toast.makeText(SignInPage.this, "enter email", Toast.LENGTH_SHORT).show();
                        } else if (edt_password.getText().toString().trim().length() == 0) {
                            Toast.makeText(SignInPage.this, "enter password", Toast.LENGTH_SHORT).show();
                        } else if (!isValidEmail(edt_email.getText().toString().trim())) {

                            edt_email.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_email.setText("Invalid E-mail");
                            txt_error_email.setTextColor(Color.parseColor("#ffffff"));
                            txt_error_email.setVisibility(View.VISIBLE);
                        } else {

                            loginCall("no_social");

                        }


                } else {

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
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInPage.this, UserRegOne.class));
            }
        });
        txt_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInPage.this, ForgetPassword.class));
            }
        });
        //getWindow().setBackgroundDrawableResource(R.drawable.login_ban) ;

     Picasso.with(ctx).load(R.drawable.login_compress).into(img_home_back);
        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                strGCMid = registrationId;

                Log.d("RegID", registrationId);
                Log.d("RegID1", strGCMid);
                //send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }

    public void loginCall(final String str_btn_type) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(SignInPage.this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Log.i("HH", "strResp 1");

                                Toast.makeText(SignInPage.this, "Login Success", Toast.LENGTH_SHORT).show();
                                String strName = jsobj.getJSONObject("Response").getString("Name");
                                String strUserId = jsobj.getJSONObject("Response").getString("userid");
                                String strCompleteLevel = jsobj.getJSONObject("Response").getString("completeLevel");
                                String strMembership = jsobj.getJSONObject("Response").getString("membership");
                                String strScreenId = jsobj.getJSONObject("Response").getString("screen_id");
                                String strRating = jsobj.getJSONObject("Response").getString("Rating");
                                String strProfileImage = GeneralData.LOCAL_IP_IMAGE + "upload/" + jsobj.getJSONObject("Response").getString("profileImage");
                                String strEasyMarryId = jsobj.getJSONObject("Response").getString("easymarryid");
                                String strMemPlan = jsobj.getJSONObject("Response").getString("membershipplan");
                                String strMemValid = jsobj.getJSONObject("Response").getString("membershipvalidity");
                                String daily_recom_notify = jsobj.getJSONObject("Response").getString("recommendnotification");
                                String matches_notify = jsobj.getJSONObject("Response").getString("matchesnotification");
                                String shortlist_notify = jsobj.getJSONObject("Response").getString("shortlistnotification");
                                String view_profile_notify = jsobj.getJSONObject("Response").getString("viewednotification");
                                String horoscope_id = jsobj.getJSONObject("Response").getString("horoscope_id");
                                int screen_id= Integer.parseInt(strScreenId);


                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("name", strName);
                                prefEdit.putString("userid", strUserId);
                                prefEdit.putString("completelevel", strCompleteLevel);
                                prefEdit.putString("membership", strMembership);
                                prefEdit.putString("profileimage", strProfileImage);
                                prefEdit.putString("screenid", strScreenId);
                                prefEdit.putString("Rating", strRating);
                                prefEdit.putString("easymarryid", strEasyMarryId);
                                prefEdit.putString("memplan", strMemPlan);
                                prefEdit.putString("memvalid", strMemValid);
                                prefEdit.putString("login_uname", edt_email.getText().toString().trim());
                                prefEdit.putString("login_pword", edt_password.getText().toString().trim());
                                prefEdit.putString("daily_recomm_notify", daily_recom_notify);
                                prefEdit.putString("matches_notify", matches_notify);
                                prefEdit.putString("shortlist_notify", shortlist_notify);
                                prefEdit.putString("view_profile_notify", view_profile_notify);
                                prefEdit.putString("horo_id", horoscope_id);
                                if(screen_id>2) {
                                    String call_preference = jsobj.getJSONObject("Response").getString("preference");
                                    prefEdit.putString("call_preference", call_preference);
                                }
                                prefEdit.commit();

                                Log.e("NN", "img_url_login-->"+strProfileImage);

                                //String strImageURL ="";
                                if (strScreenId.equalsIgnoreCase("1")) {
                                    startActivity(new Intent(SignInPage.this, UserRegTwo.class));
                                    finish();
                                } else if (strScreenId.equalsIgnoreCase("2")) {
                                    startActivity(new Intent(SignInPage.this, UserRegThree.class));
                                    finish();
                                } else if (strScreenId.equalsIgnoreCase("3")) {
                                    startActivity(new Intent(SignInPage.this, UserRegFour.class));
                                    finish();
                                } else if (strScreenId.equalsIgnoreCase("4")) {
                                    startActivity(new Intent(SignInPage.this, UserRegFive.class));
                                    finish();
                                } else {

                                    Log.e("NN", "Hi i'm login going to matches...");
                                    Intent i = new Intent(SignInPage.this, Matches.class);
                                    startActivity(i);
                                    finish();
                                }

                                Log.i("HH", "strResp 4");
                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {

                                if (jsobj.getString("code").equalsIgnoreCase("0")) {
                                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                                    edt_password.setText("");
                                } else if (jsobj.getString("code").equalsIgnoreCase("4")) {

                                    Toast.makeText(getApplicationContext(), "Email is wrong", Toast.LENGTH_SHORT).show();
                                    edt_email.setText("");
                                } else if (jsobj.getString("code").equalsIgnoreCase("2")) {

                                 /*   final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                    builder.setCancelable(false);

                                    View itemView1 = LayoutInflater.from(ctx)
                                            .inflate(R.layout.login_failed_popup, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    TextView txt_span1 = (TextView) itemView1.findViewById(R.id.span1);
                                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_login_again);
                                    String str_span = "Login failed";
                                    SpannableString spannableString = new SpannableString(str_span);
                                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 5, 12, 0);
                                    txt_span1.setText(spannableString);
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            edt_email.setText("");
                                            edt_password.setText("");
                                            altDialog.dismiss();
                                        }
                                    });
                                    altDialog.show();*/

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                    builder.setCancelable(false);

                                    View itemView1 = LayoutInflater.from(ctx)
                                            .inflate(R.layout.custom_basic_det_popup, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    String str_span = "Login failed \n\n Account has been deleted";
                                    TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                    SpannableString spannableString1 = new SpannableString(str_span);
                                    spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 0, 12, 0);
                                    txt_span_text.setText(spannableString1);
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            edt_email.setText("");
                                            edt_password.setText("");
                                            altDialog.dismiss();
                                        }
                                    });
                                    altDialog.show();
                                }

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(SignInPage.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //  Toast.makeText(SignInPage.this, "res : " + res, Toast.LENGTH_LONG).show();

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
                if (str_btn_type.equalsIgnoreCase("social")) {
                    Log.e("NN", fb_id);
                    params.put("fbid", fb_id);

                }

                params.put("username", edt_email.getText().toString().trim());
                params.put("password", edt_password.getText().toString().trim());
                params.put("gcmid", strGCMid);

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


        RequestQueue requestQueue = Volley.newRequestQueue(SignInPage.this);
        requestQueue.add(stringRequest);


    }

    public boolean isValidEmail(CharSequence strEmail) {
        boolean isvalid = false;
        try {
            isvalid = false;
            try {
                if (strEmail == null) {
                    return isvalid;
                } else {
                    isvalid = android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isvalid;
    }
   /* @Override
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
                if (nAttempt != 0) {
                    Toast.makeText(SignInPage.this, "Internet connected", Toast.LENGTH_SHORT).show();
                }
                nAttempt = 1;

            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setCancelable(true);

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
    };*/
}
