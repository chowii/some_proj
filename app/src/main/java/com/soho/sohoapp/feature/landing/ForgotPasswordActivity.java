package com.soho.sohoapp.feature.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.soho.sohoapp.R;


public class ForgotPasswordActivity extends AppCompatActivity {

    //TODO complete forgot password after api is ready

    public static Intent createIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }
}
