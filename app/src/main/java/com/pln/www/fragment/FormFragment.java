package com.pln.www.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pln.www.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_form, null);


        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE :
                        TextView tv = (TextView) getActivity().findViewById(R.id.message);
                        tv.setText("Hay Achi");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }


            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("ubah aja")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
