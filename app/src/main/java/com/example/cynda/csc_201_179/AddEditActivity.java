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

public class AddEditActivity extends AppCompatActivity {
    private EditText lName, fName, bNum, street, city, state, zip;
    private Button back, add;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        index = MainActivity.currentIndex();

        lName = (EditText) findViewById(R.id.lastNameET);
        fName = (EditText) findViewById(R.id.firstNameET);
        bNum = (EditText) findViewById(R.id.buildNumET);
        street = (EditText) findViewById(R.id.streetET);
        city = (EditText) findViewById(R.id.cityET);
        state = (EditText) findViewById(R.id.stateET);
        zip = (EditText) findViewById(R.id.zipET);

        back = (Button) findViewById(R.id.back);
        add = (Button) findViewById(R.id.add);

        if(index!=-1){
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
                startActivity(new Intent(AddEditActivity.this, MainActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!fName.getText().toString().equals("")
                        &&!lName.getText().toString().equals("")
                        &&!street.getText().toString().equals("")
                        &&!city.getText().toString().equals("")
                        &&!state.getText().toString().equals("")
                        &&!zip.getText().toString().equals("")
                        &&!bNum.getText().toString().equals("")) {
                    if(MainActivity.currentIndex()!=-1){
                        AddressInOut.getLastNameList().set(index, lName.getText().toString());
                        AddressInOut.getFirstNameList().set(index, fName.getText().toString());
                        AddressInOut.getBuildingLongList().set(index, Long.valueOf(bNum.getText().toString()));
                        AddressInOut.getStreetList().set(index, street.getText().toString());
                        AddressInOut.getCityList().set(index, city.getText().toString());
                        AddressInOut.getStateList().set(index, state.getText().toString());
                        AddressInOut.getZipList().set(index, Integer.valueOf(zip.getText().toString()));
                        AddressInOut.saveAddresses();
                        startActivity(new Intent(AddEditActivity.this, MainActivity.class));
                    }else {
                        AddressInOut.addAddress(fName.getText().toString()
                                , lName.getText().toString()
                                , street.getText().toString()
                                , city.getText().toString()
                                , state.getText().toString()
                                , Integer.valueOf(zip.getText().toString())
                                , Long.valueOf(bNum.getText().toString()));
                        AddressInOut.saveAddresses();
                        startActivity(new Intent(AddEditActivity.this, MainActivity.class));
                    }
                }
            }
        });
    }
}
