package com.spendanalyzer.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spendanalyzer.R;
import com.spendanalyzer.activity.LoginActivity;
import com.spendanalyzer.adapter.ListExpensesAdapter;
import com.spendanalyzer.apppreference.AppPreferences;
import com.spendanalyzer.db.DBconnection;
import com.spendanalyzer.model.Expenses;
import com.spendanalyzer.model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    DBconnection dataBase;
    ArrayList<String> pos,min,arryRV;
    final String key = "setting";
    final String TAG = "setting";

    TextView tvOutcome,tvIncome,tvTotal,tvUserProfileName;
    CircleImageView imgProfile;
    List<Expenses> expenses;
    List<User> userList;
    private Button logout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.profile,container,false);
        logout=v.findViewById(R.id.logout);
        //String useremailId = getArguments().getString("EmailID");

      /*  dataBase = new DBconnection(getActivity());
      //  userList= new ArrayList<>(dataBase.viewUserDetails(useremailId));

        //recyclerView = (RecyclerView) v.findViewById(R.id.rv);

        expenses= new ArrayList<>(dataBase.viewMoney(AppPreferences.getInstance(getActivity()).getUserId()));*/

       // adapter = new ListExpensesAdapter(getActivity(), expenses);

      //  RecyclerViewAdapter rvadapter = new RecyclerViewAdapter(expenses);
       // recyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
       // recyclerView.setAdapter(rvadapter);




        dataBase = new DBconnection(getActivity());

        min = dataBase.getMinus(AppPreferences.getInstance(getActivity()).getUserId());
        pos = dataBase.getPositive(AppPreferences.getInstance(getActivity()).getUserId());

        int sumArryMin = sumArray(min.toArray(new String[min.size()]));
        int sumArryPos = sumArray(pos.toArray(new String[pos.size()]));
        int total = sumArryPos + sumArryMin;


        String stringMin = Float.toString(sumArryMin);
        String stringPos = Float.toString(sumArryPos);
        String stringTotal = Float.toString(total);


       tvOutcome = (TextView) v.findViewById(R.id.tv123);
       tvIncome = (TextView) v.findViewById(R.id.tv321);
       tvTotal = (TextView) v.findViewById(R.id.tvMonthlyTheTotal);
       tvUserProfileName = (TextView) v.findViewById(R.id.tvUserProfileName);
        tvUserProfileName.setText(AppPreferences.getInstance(getActivity()).getEmailID());
        tvOutcome.setText(stringMin);
        tvIncome.setText(stringPos);
        tvTotal.setText(stringTotal);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        return v;
    }


    private int sumArray(String[] string) {
        int sum = 0;
        for (int i = 0; i < string.length; i++) {
            sum = sum + Integer.parseInt(string[i]);
        }
        return sum;
    }
}
