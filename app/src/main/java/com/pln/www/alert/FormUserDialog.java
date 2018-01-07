package com.pln.www.alert;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pln.www.LoginActivity;
import com.pln.www.R;
import com.pln.www.adapter.UserModel;
import com.pln.www.fragment.UserFragment;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormUserDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private Button bAddUser, bCancel;
    private EditText etEmail, etPassword, etName;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference dbUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_form_user, null);
        setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        bAddUser = (Button) v.findViewById(R.id.bAdd);
        bCancel = (Button) v.findViewById(R.id.bCancel);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etName = (EditText) v.findViewById(R.id.etName);
        etPassword = (EditText) v.findViewById(R.id.etPass);
        bAddUser.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onWait(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == bAddUser){
            AddNewUser();
        }
        if(v == bCancel){
            getDialog().dismiss();
        }
    }

    private void AddNewUser(){
        final String email = etEmail.getText().toString().trim();
        final String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        onWait();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String user_id = mAuth.getCurrentUser().getUid();
                    dbUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                    UserModel userModel = new UserModel(name,email);
                    dbUsers.setValue(userModel);
                    progressDialog.dismiss();
                    getDialog().dismiss();
                    Toast.makeText(getActivity(), "New User Added", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Wrong Email or Password", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }
}