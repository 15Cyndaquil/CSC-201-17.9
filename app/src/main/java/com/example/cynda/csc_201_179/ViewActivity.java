package com.example.cynda.csc_201_179;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Cyndaquil on 4/29/2017.
 */

public class ViewActivity extends AppCompatActivity{
    private int index;

    private TextView lName, fName, bNum, street, city, state, zip;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        lName = (TextView) findViewById(R.id.lastNameET);
        fName = (TextView) findViewById(R.id.firstNameET);
        bNum = (TextView) findViewById(R.id.buildNumET);
        street = (TextView) findViewById(R.id.streetET);
        city = (TextView) findViewById(R.id.cityET);
        state = (TextView) findViewById(R.id.stateET);
        zip = (TextView) findViewById(R.id.zipET);

        back = (Button) findViewById(R.id.back);

        index = MainActivity.currentIndex();

        if (index>-1){
            lName.setText(AddressInOut.getLastNameList().get(index));
            fName.setText(AddressInOut.getFirstNameList().get(index));
            bNum.setText(String.valueOf(AddressInOut.getBuildingLongList().get(index)));
            street.setText(AddressInOut.getStreetList().get(index));
            city.setText(AddressInOut.getCityList().get(index));
            state.setText(AddressInOut.getStateList().get(index));
            zip.setText(String.valueOf(AddressInOut.getZipList().get(index)));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this, MainActivity.class));
            }
        });
    }
}
