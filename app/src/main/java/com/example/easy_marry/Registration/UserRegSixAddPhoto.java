package com.example.easy_marry.Registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.HomeScreen;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.RealPathUtil;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by android2 on 20/10/16.
 */
public class UserRegSixAddPhoto extends Activity {
    Button btn_add_photo,btn_next;
    ImageView img_user_pic, img_back;
    Context ctx;
    GeneralData gD;
    String str_user_id, str_span_txt;
    TextView txt_span, txt_skip;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;
    private Bitmap bitmap;
    // ProgressDialog loading;
    android.support.v7.app.AlertDialog altDialog_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user6_add_photo);
        ctx = this;
        gD = new GeneralData(ctx);
        str_user_id = gD.prefs.getString("userid", null);
        img_back = (ImageView) findViewById(R.id.img_back);
        btn_add_photo = (Button) findViewById(R.id.btn_add_photo);
        img_user_pic = (ImageView) findViewById(R.id.img_user_pic);
        txt_span = (TextView) findViewById(R.id.txt_span_text_photo);
        txt_skip = (TextView) findViewById(R.id.txt_skip);
        str_span_txt = "Profiles with photo get 20 times more response";
        SpannableString spannableString = new SpannableString(str_span_txt);
        btn_next=(Button)findViewById(R.id.btn_next);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 19, 32, 0);
        txt_span.setText(spannableString);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegSixAddPhoto.this, Matches.class));
                finish();
            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegSixAddPhoto.this, PartnerRegOne.class));
                finish();
            }
        });
        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img_user_pic.getDrawable() != null) {
                    update();
                } else {
                    add_image();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegSixAddPhoto.this, PartnerRegOne.class));
                finish();
            }
        });

    }

    private void update() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Remove Image"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegSixAddPhoto.this);
        // builder.setTitle("Select Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    takePicture();
                } else if (item == 1) {
                    openGallery();
                } else {
                    img_user_pic.setImageDrawable(
                            getResources().getDrawable(R.drawable.default_use));
                }
            }
        });
        builder.show();
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(intent, REQUEST_CODE_GALLERY);
      startActivityForResult(intent, GALLERY_ACTIVITY_CODE);
    }

    private void add_image() {

        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library"};

            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegSixAddPhoto.this);

            // builder.setTitle("Select Image");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // camera
                    if (item == 0) {
                        takePicture();
                    } else {
                        openGallery();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_CODE_GALLERY) {


                    Uri selectedImageUri = data.getData();
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                            null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();

                    String selectedImagePath = cursor.getString(column_index);

                    Bitmap bm;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 200;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                            && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(selectedImagePath, options);
                    img_user_pic.setImageBitmap(bm);

                    try {
                        if (requestCode == 1 && resultCode == RESULT_OK) {
                            String realPath;
                            // SDK < API11
                            if (Build.VERSION.SDK_INT < 11) {
                                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                            }
                            // SDK >= 11 && SDK < 19
                            else if (Build.VERSION.SDK_INT < 19) {
                                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                            }
                            // SDK > 19 (Android 4.4)
                            else {
                                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                                Log.i("realll path->", realPath);
                            }
                            Uri uriFromPath = Uri.fromFile(new File(realPath));
                            File sourceFilePath = new File(realPath);
                            String strrealpath = realPath;


                            uploadImage(bm);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
                    try {
                        final int IMAGE_MAX_SIZE = 300;

                        // Bitmap bitmap;
                        File file = null;
                        FileInputStream fis;
                        BitmapFactory.Options opts;
                        int resizeScale;
                        Bitmap bmp;
                        //   file = new File(Uri.parse(mCurrentPhotoPath).getPath());
                        // This bit determines only the width/height of the
                        // bitmap
                        // without loading the contents
                        opts = new BitmapFactory.Options();
                        opts.inJustDecodeBounds = true;
                        fis = new FileInputStream(file);
                        BitmapFactory.decodeStream(fis, null, opts);
                        fis.close();

                        // Find the correct scale value. It should be a power of
                        // 2
                        resizeScale = 1;

                        if (opts.outHeight > IMAGE_MAX_SIZE
                                || opts.outWidth > IMAGE_MAX_SIZE) {
                            resizeScale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(opts.outHeight, opts.outWidth)) / Math.log(0.5)));
                        }

                        // Load pre-scaled bitmap
                        opts = new BitmapFactory.Options();
                        opts.inSampleSize = resizeScale;


                        fis = new FileInputStream(file);



                        bmp = BitmapFactory.decodeStream(fis, null, opts);
                        Bitmap getBitmapSize = BitmapFactory.decodeResource(
                                getResources(), R.drawable.default_use);
                   /* rnd_image.setLayoutParams(new RelativeLayout.LayoutParams(
                            200, 200));//(width,height);*/
                        img_user_pic.setImageBitmap(bmp);
                        //  rnd_image.setRotation(90);
                        fis.close();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 70, baos);


                        uploadImage(bmp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (requestCode == GALLERY_ACTIVITY_CODE) {
                    if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                        try{
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA };
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            performCrop(picturePath);

                      /*  //return Image Path to the Main Activity
                        Intent returnFromGalleryIntent = new Intent();
                        returnFromGalleryIntent.putExtra("picturePath",picturePath);
                        setResult(RESULT_OK,returnFromGalleryIntent);
                        finish();*/
                        }catch(Exception e){
                            e.printStackTrace();
                            Intent returnFromGalleryIntent = new Intent();
                            setResult(RESULT_CANCELED, returnFromGalleryIntent);
                            finish();
                        }
                    }else{
                        Log.i("NN","RESULT_CANCELED");
                        Intent returnFromGalleryIntent = new Intent();
                        setResult(RESULT_CANCELED, returnFromGalleryIntent);
                        finish();
                    }
                }

                if (requestCode == RESULT_CROP ) {
                    if(resultCode == Activity.RESULT_OK){
                        Bundle extras = data.getExtras();
                        Bitmap selectedBitmap = extras.getParcelable("data");
                        // Set The Bitmap Data To ImageView
                        img_user_pic.setImageBitmap(selectedBitmap);
                        uploadImage(selectedBitmap);
//                    rnd_image.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }

            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    /*@Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_GALLERY) {


            Uri selectedImageUri = intent.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);

            img_user_pic.setImageBitmap(bm);


            try {
                if (requestCode == 1 && responseCode == RESULT_OK) {
                    String realPath;
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, intent.getData());
                    }
                    // SDK >= 11 && SDK < 19
                    else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, intent.getData());
                    }
                    // SDK > 19 (Android 4.4)
                    else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, intent.getData());
                        Log.i("realll path->", realPath);
                    }


                    Uri uriFromPath = Uri.fromFile(new File(realPath));

                    // 1. Get the image path from the gallery------------ vinoth
                    File sourceFilePath = new File(realPath);


                    String strrealpath = realPath;

                    File ff = new File(strrealpath);
                    ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                    if (isConnected) {
                        View itemView1;
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                        builder.setCancelable(true);
                        itemView1 = LayoutInflater.from(ctx)
                                .inflate(R.layout.custom_image_load, null);
                        AnimationDrawable loadingViewAnim;

                        altDialog_load = builder.create();
                        altDialog_load.setView(itemView1);
                        altDialog_load.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        LinearLayout loadingLayout = (LinearLayout)itemView1.findViewById(R.id.LinearLayout1);
                        //TextView loadigText = (TextView) itemView1.findViewById(R.id.textView111);
                        ImageView   loadigIcon = (ImageView)itemView1. findViewById(R.id.imageView111);
                        loadigIcon.setBackgroundResource(R.drawable.loading_anim);
                        loadingViewAnim = (AnimationDrawable) loadigIcon.getBackground();
                        //loadigText.setText("Registering..Please wait");
                        loadingViewAnim.start();
                        altDialog_load.show();

                        uploadImage(bm);
                    }
                    else{
                        Toast.makeText(UserRegSixAddPhoto.this, "Image upload failed!!!", Toast.LENGTH_SHORT).show();
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                        builder.setCancelable(false);
                        View itemView1 = LayoutInflater.from(ctx)
                                .inflate(R.layout.custom_basic_det_popup, null);
                        final android.support.v7.app.AlertDialog altDialog = builder.create();
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
                    Log.i("DD", sourceFilePath.getAbsolutePath());
                    Log.d("HMKCODE", "Real Path: " + realPath);

                    // 2. Upload the multipart content of the image to the server------------ vinoth
                    // ImageUploadMultipartTask imUMT = new ImageUploadMultipartTask(sourceFilePath.getAbsolutePath(), "http://aryvartdev.com/projects/utician/register.php?user_id=",);
                    //imUMT.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);

                img_user_pic.setImageBitmap(bitmap);
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    View itemView1;
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(ctx)
                            .inflate(R.layout.custom_image_load, null);
                    AnimationDrawable loadingViewAnim;

                    altDialog_load = builder.create();
                    altDialog_load.setView(itemView1);
                    altDialog_load.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    LinearLayout loadingLayout = (LinearLayout)itemView1.findViewById(R.id.LinearLayout1);
                    //TextView loadigText = (TextView) itemView1.findViewById(R.id.textView111);
                    ImageView   loadigIcon = (ImageView)itemView1. findViewById(R.id.imageView111);
                    loadigIcon.setBackgroundResource(R.drawable.loading_anim);
                    loadingViewAnim = (AnimationDrawable) loadigIcon.getBackground();
                    //loadigText.setText("Registering..Please wait");
                    loadingViewAnim.start();
                    altDialog_load.show();
                    uploadImage(bitmap);
                }
                else{
                    Toast.makeText(UserRegSixAddPhoto.this, "Image upload failed!!!", Toast.LENGTH_SHORT).show();
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                    builder.setCancelable(false);
                    View itemView1 = LayoutInflater.from(ctx)
                            .inflate(R.layout.custom_basic_det_popup, null);
                    final android.support.v7.app.AlertDialog altDialog = builder.create();
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
                String path = Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserRegSixAddPhoto.this, Matches.class));
        finish();
    }

    private void uploadImage(final Bitmap bmp) {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register10_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Log.i("HH", "strResp : " + s);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(s);

                            Log.i("HH", "strResp : " + s);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
//                                altDialog_load.dismiss();
                                btn_next.setVisibility(View.VISIBLE);
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

                           /* GeneralData.str_name = jsobj.getJSONObject("Response").getString("Name");
                            GeneralData.str_id = jsobj.getJSONObject("Response").getString("userid");
                            GeneralData.str_complete_profile_level = jsobj.getJSONObject("Response").getString("completeLevel");
                            GeneralData.str_image = GeneralData.LOCAL_IP_IMAGE + "images/" + jsobj.getJSONObject("Response").getString("profileImage");
                            GeneralData.str_membership = jsobj.getJSONObject("Response").getString("membership");
                          */

                                Log.e("NN6:profileimage",strProfileImage);

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

                                //Disimissing the progress dialog
                                loading.dismiss();
                                //Showing toast message of the response
                                // Toast.makeText(UserRegSixAddPhoto.this, s, Toast.LENGTH_LONG).show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                          loading.dismiss();
                       // altDialog_load.dismiss();
                        //Showing toast
                        //Toast.makeText(UserRegSixAddPhoto.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bmp);
                //Getting Image Name
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("userid", str_user_id);
                params.put("image", image);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
