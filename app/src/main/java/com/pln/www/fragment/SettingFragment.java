package com.pln.www.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pln.www.activity.LoginActivity;
import com.pln.www.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etOldPass, etNewPass, etReNewPass;
    private Button bChangePass;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog progressDialog;
    private AuthCredential authCredential;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void change_password(){
        String OldPass = etOldPass.getText().toString().trim();
        String NewPass = etNewPass.getText().toString().trim();
        String ReNewPass = etReNewPass.getText().toString().trim();

        if(TextUtils.isEmpty(OldPass)){
            Toast.makeText(getActivity(), "Please Enter Old Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(NewPass)){
            Toast.makeText(getActivity(), "Please Enter New Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(ReNewPass)){
            Toast.makeText(getActivity(), "Please Re-Enter New Password", Toast.LENGTH_LONG).show();
            return;
        }

        onWait();

        if(NewPass.equals(ReNewPass)){
            final String getEmail = currentUser.getEmail();
            authCredential = EmailAuthProvider.getCredential(getEmail,OldPass);
            currentUser.reauthenticate(authCredential).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        currentUser.updatePassword(etNewPass.getText().toString()).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_LONG).show();
                                    Thread thread = new Thread(){
                                        @Override
                                        public void run(){
                                            try{
                                                Thread.sleep(3500);
                                                FirebaseAuth.getInstance().signOut();
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    thread.start();
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "At Least 6 Characters", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to Get Your Current Email and Password", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Wrong New Password", Toast.LENGTH_LONG);
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        etOldPass = (EditText) v.findViewById(R.id.etOldPassword);
        etNewPass = (EditText) v.findViewById(R.id.etNewPassword);
        etReNewPass = (EditText) v.findViewById(R.id.etReNewPassword);
        bChangePass = (Button) v.findViewById(R.id.bUpdate);
        bChangePass.setOnClickListener(this);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v == bChangePass) {
            change_password();
        }
    }

    public void onWait(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
