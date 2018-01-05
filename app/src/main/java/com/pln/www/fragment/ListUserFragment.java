package com.pln.www.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pln.www.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUserFragment extends AppCompatDialogFragment {


//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        View v = LayoutInflater.from(getActivity())
//                .inflate(R.layout.fragment_add_user, null);
//
//        Button ethapus = (Button) v.findViewById(R.id.ethapus1);
//        ethapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                alert.setMessage("Apakah anda yakin menghapus pengguna ?");
//                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                    }
//                });
////                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////
////                    }
////                });
//                alert.show();
//            }
//        });
//
////        return alert;
//    }
}