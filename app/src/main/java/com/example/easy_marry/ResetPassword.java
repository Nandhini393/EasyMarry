package com.example.easy_marry;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by android2 on 6/6/16.
 */
public class ResetPassword extends Activity {
    Button btn_reset_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        btn_reset_password=(Button)findViewById(R.id.btn_change_pass);
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(ResetPassword.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.reset_popup);
                TextView txt_login_link=(TextView)dialog.findViewById(R.id.txt_link_login);
                txt_login_link.setMovementMethod(LinkMovementMethod.getInstance());
                dialog.show();


            }
        });
    }
}
