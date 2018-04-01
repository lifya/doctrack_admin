package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser currentUser;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private Button bLogin;
    private EditText etUsername, etPassword;
    private ProgressDialog progressDialog;
    private TextView fpass;
    private DatabaseReference dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);

        dbUsers = FirebaseDatabase.getInstance().getReference("Users");

        fpass = (TextView) findViewById(R.id.fpass);
        fpass.setOnClickListener(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            sendtoStart();
        }
    }

    public void sendtoStart(){
        Intent startIntent = new Intent(LoginActivity.this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
        finish();
    }

    private void userLogin(){
        final String email = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        dbUsers.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String idUser = child.getKey();
                    System.out.println(idUser);
                    dbUsers.child(idUser).child("status").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int status = Integer.parseInt(dataSnapshot.getValue().toString());
                            if(status == 1){
                                mAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressDialog.dismiss();
                                                if(task.isSuccessful()){
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                }
                                                else{
                                                    Toast.makeText(LoginActivity.this, "Failed ! Check Your Email or Password", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                            }
                                        });
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Failed ! Unregistered", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

public void sentoReset() {
    Intent startIntent = new Intent(LoginActivity.this, RessetPasswordActivity.class);
    startActivity(startIntent);
    finish();
}

    @Override
    public void onClick(View v) {
        if(v == bLogin){
            userLogin();
        }
        else if (v == fpass) {
            sentoReset();
        }
    }
}
