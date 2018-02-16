package com.pln.www.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pln.www.R;

public class RessetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageback;
    Button bReset;
    EditText etEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resset_password);
        imageback = (ImageView) findViewById(R.id.ivBack);
        bReset = (Button) findViewById(R.id.breset);
        etEmail = (EditText) findViewById(R.id.etEmail);
        imageback.setOnClickListener(this);
        bReset.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view == imageback) {
            onBackPressed();
        }
        if(view == bReset){
            resetPassword();
        }
    }

    @Override
    public void onBackPressed() {
        sentoLogin();
    }

    private void sentoLogin() {
        Intent startIntent = new Intent(RessetPasswordActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void resetPassword(){
        String email = etEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(RessetPasswordActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RessetPasswordActivity.this, "Successed ! Check Your Email", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RessetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RessetPasswordActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}
