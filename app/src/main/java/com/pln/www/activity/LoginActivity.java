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
    //private DatabaseReference databaseReference;
    //private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //databaseReference = FirebaseDatabase.getInstance().getReference();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
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
        startActivity(startIntent);
        finish();
    }

    private void userLogin(){
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

//        databaseReference.child("Users").orderByChild("email").equalTo(username).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot!=null && dataSnapshot.getChildren()!=null &&
//                        dataSnapshot.getChildren().iterator().hasNext()){
//                    progressDialog.dismiss();
//                    mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            }
//                            else{
//                                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                        }
//                    });
//                }
//                else {
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                progressDialog.dismiss();
//                Toast.makeText(getParent(), "Failed", Toast.LENGTH_LONG).show();
//                return;
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if(v == bLogin){
            userLogin();
        }
    }
}
