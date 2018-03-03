package com.pln.www.alert;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
import com.pln.www.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormUserDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private Button bAddUser, bCancel;
    private EditText etEmail, etPassword, etName, etRePass;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference dbUsers;

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
        etRePass = (EditText) v.findViewById(R.id.etRePass);
        bAddUser.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        progressDialog = new ProgressDialog(getActivity());

        mAuth = FirebaseAuth.getInstance();

        return v;
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
        final String password = etPassword.getText().toString().trim();
        final String repassword = etRePass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(repassword)){
            Toast.makeText(getActivity(), "Please Re-Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        if(password.equals(repassword)){
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    String currentEmail = userModel.getEmail();
                    if(currentEmail.equals(email)){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Check your email ! Email has already registered", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        progressDialog.dismiss();
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    String status = "1";
                                    dbUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    UserModel userModel = new UserModel(user_id,name,email,status);
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

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_LONG).show();
            return;
        }

    }
}