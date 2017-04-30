package com.example.cynda.csc_201_179;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private LinearLayout vLay;

    private RadioGroup radioGroup;

    private Button pre, view, add, edit, next;

    private int width;
    private int height;
    private int currentScreen = 0;

    private static File dir;

    private static StringBuilder selected = new StringBuilder();

    private ArrayList<String> sortedLastName = new ArrayList<>();
    private ArrayList<String> firstName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected.replace(0, selected.length(), "");

        dir = getFilesDir();


        vLay = (LinearLayout) findViewById(R.id.vLay);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        pre = (Button) findViewById(R.id.pre);
        view = (Button) findViewById(R.id.view);
        add = (Button) findViewById(R.id.add);
        edit = (Button) findViewById(R.id.edit);
        next = (Button) findViewById(R.id.next);

        ViewTreeObserver vto = vLay.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    vLay.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    vLay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                width  = vLay.getMeasuredWidth();
                height = vLay.getMeasuredHeight();

                height = height - ((Button) findViewById(R.id.view)).getHeight();
                height = height/83;
                setUp();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEditActivity.class));
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected.equals("")){
                    startActivity(new Intent(MainActivity.this, AddEditActivity.class));
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preButton();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton();
            }
        });




    }

    private void setUp(){
        setList();

        for(int i=0; i<height; i++) {
            if(sortedLastName.size()>currentScreen) {
                final RadioButton left = new RadioButton(this);
                int currentIndex = Integer.valueOf(sortedLastName.get(i).substring(sortedLastName.get(i).indexOf(",") + 1, sortedLastName.get(i).length()));
                left.setText(firstName.get(currentIndex)+" "+sortedLastName.get(i).substring(0, sortedLastName.get(i).indexOf(",")));
                left.setHint(String.valueOf(currentIndex));
                currentScreen++;

                left.setTextSize(23);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    left.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }

                LinearLayout horz = new LinearLayout(this);
                horz.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horz.setOrientation(LinearLayout.HORIZONTAL);
                radioGroup.addView(left);

                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected.replace(0, selected.length(), "");
                        selected.append(left.getHint());
                    }
                });
            }
        }
        System.out.println(sortedLastName.size()+" "+height+" "+currentScreen);
    }

    private void setList(){
            AddressInOut.loadAddresses();


        for(int i=0; i<AddressInOut.getTotalAddresses(); i++){
            sortedLastName.add(AddressInOut.getLastNameList().get(i)+","+i);
            firstName.add(AddressInOut.getFirstNameList().get(i));
        }
        Collections.sort(sortedLastName);
    }
    private void nextButton() {
        selected.replace(0, selected.length(), "");
        radioGroup.removeAllViews();
        if (currentScreen != sortedLastName.size()) {
            for (int i = 0; i < height; i++) {
                if (sortedLastName.size() > currentScreen) {
                    final RadioButton left = new RadioButton(this);
                    int currentIndex = Integer.valueOf(sortedLastName.get(currentScreen).substring(sortedLastName.get(currentScreen).indexOf(",") + 1, sortedLastName.get(currentScreen).length()));
                    left.setText(firstName.get(currentIndex) + " " + sortedLastName.get(currentScreen).substring(0, sortedLastName.get(currentScreen).indexOf(",")));
                    currentScreen++;
                    left.setTextSize(23);
                    left.setHint(String.valueOf(currentIndex));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        left.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }

                    LinearLayout horz = new LinearLayout(this);
                    horz.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    horz.setOrientation(LinearLayout.HORIZONTAL);
                    radioGroup.addView(left);

                    left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected.replace(0, selected.length(), "");
                            selected.append(left.getHint());
                        }
                    });
                }
            }
        }
        System.out.println(sortedLastName.size() + " " + height + " " + currentScreen);
    }
    private void preButton(){
        selected.replace(0, selected.length(), "");
        radioGroup.removeAllViews();
        if(currentScreen%height==0){
            currentScreen = currentScreen - height*2;
        }else {
            currentScreen = currentScreen - height;
        }if(currentScreen<0){
            currentScreen=0;
        }
        for(int i=0; i<height; i++) {
            if(sortedLastName.size()>currentScreen) {
                final RadioButton left = new RadioButton(this);
                int currentIndex = Integer.valueOf(sortedLastName.get(currentScreen).substring(sortedLastName.get(currentScreen).indexOf(",") + 1, sortedLastName.get(currentScreen).length()));
                left.setText(firstName.get(currentIndex)+" "+sortedLastName.get(currentScreen).substring(0, sortedLastName.get(currentScreen).indexOf(",")));
                currentScreen++;
                left.setTextSize(23);
                left.setHint(String.valueOf(currentIndex));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    left.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }

                LinearLayout horz = new LinearLayout(this);
                horz.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horz.setOrientation(LinearLayout.HORIZONTAL);
                radioGroup.addView(left);

                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected.replace(0, selected.length(), "");
                        selected.append(left.getHint());
                    }
                });
            }
        }
        System.out.println(sortedLastName.size()+" "+height+" "+currentScreen);
    }

    public static int currentIndex(){
        if(selected.toString().trim()!=""){
            return Integer.valueOf(selected.toString().trim());
        }
        return -1;
    }

    public static File getDIR(){
        return dir;
    }
}
