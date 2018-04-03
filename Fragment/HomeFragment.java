package com.spendanalyzer.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.spendanalyzer.R;
import com.spendanalyzer.adapter.ListExpensesAdapter;
import com.spendanalyzer.apppreference.AppPreferences;
import com.spendanalyzer.db.DBconnection;
import com.spendanalyzer.model.Expenses;
import com.spendanalyzer.model.User;
import com.spendanalyzer.utils.InputDialog;
import com.spendanalyzer.utils.OutputDialog;

import java.util.List;

/**
 * Created by Sonia.
 */
public class HomeFragment extends Fragment {

    DBconnection dataBase;
    List<Expenses> expenses;
    ListView lvDisplay;
    ListExpensesAdapter adapter;
    List<User> userList;
    AppPreferences appPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v =inflater.inflate(R.layout.home,container,false);
        swipeRefreshLayout=v.findViewById(R.id.ll_swipe);


        dataBase = new DBconnection(getActivity());
        appPreferences=AppPreferences.getInstance(getActivity());

        final String useremailId = getArguments().getString("EmailID");

        // Getting user id to store corresponding value to corresponding data
        userList=dataBase.viewUserDetails(useremailId);
        appPreferences.setEmailID(userList.get(0).getEmail());
        appPreferences.setUserId(userList.get(0).getId());


        expenses= dataBase.viewMoney(userList.get(0).getId());
        lvDisplay = (ListView) v.findViewById(R.id.Display_listview);

        adapter = new ListExpensesAdapter(getActivity(), expenses);

        lvDisplay.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                expenses= dataBase.viewMoney(userList.get(0).getId());
                adapter = new ListExpensesAdapter(getActivity(), expenses);
                lvDisplay.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        final InputDialog d = new InputDialog();
        d.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Fragment frg = getFragmentManager().findFragmentByTag("myTag");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.detach(frg);
                ft.attach(frg).commit();
            }
        });


        Button Add = (Button) v.findViewById(R.id.btnAdd);
        Button Sub = (Button) v.findViewById(R.id.btnSub);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manger = getFragmentManager();
                InputDialog myDialog = new InputDialog();
                myDialog.show(manger,"MyDialog");
            }
        });

        Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manger = getFragmentManager();
                OutputDialog myDialog = new OutputDialog();
                myDialog.show(manger,"MyDialog");
            }
        });


        return v;
    }



}

/*

        ListView list = (ListView) v.findViewById(R.id.Display_listview);

        ArrayAdapter<String> listVieAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuitem
        );
        list.setAdapter(listVieAdapter);
 */
