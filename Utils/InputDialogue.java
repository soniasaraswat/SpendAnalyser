package com.spendanalyzer.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spendanalyzer.R;
import com.spendanalyzer.apppreference.AppPreferences;
import com.spendanalyzer.db.DBconnection;
import com.spendanalyzer.model.Expenses;
import com.spendanalyzer.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputDialog extends DialogFragment {

    DBconnection dataBase;
    private DialogInterface.OnDismissListener onDismissListener;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<User> userList;
    private AppPreferences appPreferences;



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_input,null);
        appPreferences=AppPreferences.getInstance(getActivity());


        final EditText etInput = (EditText) v.findViewById(R.id.etInputNumber);

        Button btnSave = (Button) v.findViewById(R.id.btnSaveIn);

        dataBase = new DBconnection(getActivity());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etInput.getText().toString().trim().length()!=0){
                    int Mon =  Integer.parseInt(etInput.getText().toString());
                    String Cat = "Income";
                    String Dat = formatter.format( new Date());
                    int    spe =0;
                    dataBase.dataInsert(appPreferences.getUserId(),Mon,spe,Cat,Dat);
                    getDialog().dismiss();
                }else {
                    Toast.makeText(getActivity(),"Please enter amount",Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        this.onDismissListener = onDismissListener;
    }
}
