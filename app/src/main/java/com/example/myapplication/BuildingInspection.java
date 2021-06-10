package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.ui.dashboard.DashboardFragment;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class BuildingInspection extends Fragment {
    private CheckBox row1Ok;
    private CheckBox row1NotOk;
    private CheckBox row2Ok;
    private CheckBox row2NotOk;
    private CheckBox row3Ok;
    private CheckBox row3NotOk;
    private CheckBox row4Ok;
    private CheckBox row4NotOk;
    private CheckBox row5Ok;
    private CheckBox row5NotOk;

    private Button submit;

    private TextView checkBoxError;

    private ArrayList<Integer> values;

    private int question=1;
    private TableLayout table;
    private TableRow row1;
    private TableRow row2;
    private TableRow row3;
    private TableRow row4;
    private TableRow row5;

    Connection connect;
    String ConnectionResult = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_building_inspection3, container, false);

        values = new ArrayList<>(Arrays.asList(-1,-1,-1,-1,-1));


        //row1Ok=(CheckBox) rootView.findViewById(R.id.firstOkBuilding);
       // row1NotOk=(CheckBox) rootView.findViewById(R.id.firstNotOkBuilding);
       // row2Ok=(CheckBox) rootView.findViewById(R.id.secondOkBuilding);
        //row2NotOk=(CheckBox) rootView.findViewById(R.id.secondNotOkBuilding);
       // row3Ok=(CheckBox) rootView.findViewById(R.id.thirdOkBuilding);
//        row4Ok=(CheckBox) rootView.findViewById(R.id.fourthOkBuilding);
        //row4NotOk=(CheckBox) rootView.findViewById(R.id.fourthNotOkBuilding);
       // row5Ok=(CheckBox) rootView.findViewById(R.id.fifthOkBuilding);
       // row5NotOk=(CheckBox) rootView.findViewById(R.id.fifthNotOkBuilding);

        //table = (TableLayout) rootView.findViewById(R.id.tableLayoutBuilding);
        //row1 = (TableRow) rootView.findViewById(R.id.row1Building);
        //row2 = (TableRow) rootView.findViewById(R.id.row2Building);
        //row3 = (TableRow) rootView.findViewById(R.id.row3Building);
        //row4 = (TableRow) rootView.findViewById(R.id.row4Building);
        //row5 = (TableRow) rootView.findViewById(R.id.row5Building);
        //row2.setVisibility(View.INVISIBLE);
        //row3.setVisibility(View.INVISIBLE);
       // row4.setVisibility(View.INVISIBLE);
        //row5.setVisibility(View.INVISIBLE);

        submit = (Button) rootView.findViewById(R.id.BuildingSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
       /*
        checkBoxError=(TextView) rootView.findViewById(R.id.checkBoxErrorBuilding);
        if(errorStateHelper.checkBuildingError){
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else{
            checkBoxError.setVisibility(View.INVISIBLE);
        }
*/

        return rootView;
    }

    public void setArray(int index, CheckBox one, CheckBox two) {

        if (one.isChecked() && !two.isChecked()) {
            values.set(index, 1);
        }
        else if (!one.isChecked() && two.isChecked()) {
            values.set(index, 0);
        }
        else {
            values.set(index, -1);
        }

    }
    public void submit() {

        setArray(0, row1Ok, row1NotOk);
        setArray(1, row2Ok, row2NotOk);
        setArray(2, row3Ok, row3NotOk);
        setArray(3, row4Ok, row4NotOk);
        setArray(4, row5Ok, row5NotOk);

        for (int i = 0; i < 5; i++) {
            if (values.get(i) == -1) {
                errorStateHelper.checkBuildingError = true;
            }
        }
        if (errorStateHelper.checkBuildingError) {
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else {
            try {

                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionClass();
                if (connect != null) {
                    Statement st = connect.createStatement();
                    st.executeUpdate("INSERT INTO BuildingInspection VALUES (" + values.get(0) + ", "
                            + values.get(1) + ", " + values.get(2) + ", " + values.get(3) + ", " + values.get(4) + ")");
                } else {
                    ConnectionResult = "Check Connection";
                }
                connect.close();
            } catch (Exception ex) {
                System.out.println("ERROR");
            }

            Intent intent = new Intent(getActivity(), com.example.myapplication.homePage.class);
            startActivity(intent);

        }

    }
}