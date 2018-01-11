package com.pln.www.alert;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormDocumentDialog extends AppCompatDialogFragment {

    ImageView imageView;
    EditText Ed;
    Calendar mCurrentDate;
    int day, month, year;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_form_document, null);
        setCancelable(false);

//        Ed = (EditText) Ed.findViewById(R.id.etTglProses);
//        mCurrentDate = Calendar.getInstance();
//        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
//        month = mCurrentDate.get(Calendar.MONTH);
//        year = mCurrentDate.get(Calendar.YEAR);
//
//        month=month+1;
//        Ed.setText(day+"/"+month+"/"+year);
//
//        Ed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Fragment.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int montOfYear, int dayOfMonth) {
//                        montOfYear = montOfYear+1;
//                        Ed.setText(dayOfMonth+"/"+montOfYear+"/"+year);
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
//            }
//        });

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
                .setView(v)
                .create();
    }
}
