package com.spendanalyzer.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.spendanalyzer.R;
import com.spendanalyzer.apppreference.AppPreferences;
import com.spendanalyzer.db.DBconnection;
import com.spendanalyzer.model.Expenses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class OutputDialog extends DialogFragment {

    DBconnection dataBase;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_output,null);


        final EditText etOutput = (EditText) v.findViewById(R.id.etOutputNumber);
        final Spinner spnrCategory1 = (Spinner) v.findViewById(R.id.spnrCategory1);
        Button btnSaveOut = (Button) v.findViewById(R.id.btnSaveOut);

        dataBase = new DBconnection(getActivity());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrCategory1.setAdapter(adapter);


        btnSaveOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // expenses= new ArrayList<>(dataBase.viewMoney());
                if (etOutput.getText().toString().trim().length()!=0){
                    int  Mon=0;
                    int spe =  Integer.parseInt("-"+etOutput.getText().toString());
                    String Cat = spnrCategory1.getSelectedItem().toString();
                    String Dat = formatter.format( new Date());
                    dataBase.dataInsert(AppPreferences.getInstance(getActivity()).getUserId(),Mon,spe,Cat,Dat);
                    getDialog().dismiss();
                }else {
                    Toast.makeText(getActivity(),"Please enter amount",Toast.LENGTH_LONG).show();
                }



            }
        });
        return v;
    }
}
