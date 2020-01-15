package com.cook.rotenzonew.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cook.rotenzonew.Helper.Shared_Pref;
import com.cook.rotenzonew.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private Dialog Hoadialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        printHashKey();
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

       // if (!Shared_Pref.getLanguage(SplashActivity.this).isEmpty() &&
          //  !Shared_Pref.getLanguage(SplashActivity.this).equalsIgnoreCase("")){

            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashActivity.this, SignupScreenActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);

     //   }else {

//            Hoadialog = new Dialog(SplashActivity.this);
//            Hoadialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            Hoadialog.setCancelable(true);
//            Hoadialog.setContentView(R.layout.select_language_dialog);
//            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            final RadioButton radio_eng=Hoadialog.findViewById(R.id.radio_eng);
//            final RadioButton radio_mn=Hoadialog.findViewById(R.id.radio_mn);
//
//
//            radio_eng.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                radio_eng.setChecked(true);
//                radio_mn.setChecked(false);
//
//                Shared_Pref.setLanguage(SplashActivity.this, "en");
//
//                }
//            });
//
//            radio_mn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    radio_eng.setChecked(false);
//                    radio_mn.setChecked(true);
//
//                    Shared_Pref.setLanguage(SplashActivity.this, "mn");
//                }
//            });
//
//
//            try {
//                if (!SplashActivity.this.isFinishing()){
//                    Hoadialog.show();
//                }
//            }
//            catch (WindowManager.BadTokenException e) {
//                //use a log message
//            }
//
//
//
//        }
//







    }

    private void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cook.rotenzonew",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
