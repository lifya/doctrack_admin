package com.pln.www.alert;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pln.www.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormDocumentDialog extends AppCompatDialogFragment implements View.OnClickListener{

    private ImageView imageView;
    private Button bCancel, bAddDoc;
    private EditText Ed, etFile, etKeterangan;
    private Spinner spinner;
    private String getItem;
    private Calendar mCurrentDate;
    private int day, month, year;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_form_document, null);
        setCancelable(false);

        bCancel = (Button) v.findViewById(R.id.btnCancel);
        bAddDoc = (Button) v.findViewById(R.id.btnAdd);
        spinner = (Spinner) v.findViewById(R.id.spProses1);
        etKeterangan = (EditText) v.findViewById(R.id.etKeterangan);
        Ed = (EditText) v.findViewById(R.id.etTglProses);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month=month+1;
        Ed.setText(day+"/"+month+"/"+year);

        Ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int montOfYear, int dayOfMonth) {
                        montOfYear = montOfYear+1;
                        Ed.setText(dayOfMonth+"/"+montOfYear+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch(which) {
//                    case DialogInterface.BUTTON_POSITIVE :
//                        TextView tv = (TextView) getActivity().findViewById(R.id.message);
//                        tv.setText("Hay Achi");
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        break;
//                }
//            }
//        };
        bCancel.setOnClickListener(this);
        bAddDoc.setOnClickListener(this);
        return v;
    }

    public void onWait(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == bCancel){
            getDialog().dismiss();
        }
        if(v == bAddDoc){
            addDetailData();
            Toast.makeText(getActivity(), "" + getItem, Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void addDetailData(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getItem = spinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
