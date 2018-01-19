package com.pln.www.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pln.www.R;

public class RessetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resset_password);
        imageback = (ImageView) findViewById(R.id.back);
        imageback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (imageback == view) {
            LoginActivity home = new LoginActivity();
            sentoHome();
        }
    }

    @Override
    public void onBackPressed() {
        LoginActivity doc = new LoginActivity();
        sentoHome();
    }
    public void sentoHome() {
        Intent startIntent = new Intent(RessetPasswordActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }
}
