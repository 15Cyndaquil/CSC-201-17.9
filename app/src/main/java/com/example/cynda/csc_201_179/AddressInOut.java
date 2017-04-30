package com.example.cynda.csc_201_179;


import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Created by 15Cyndaquil on 4/25/2017.
 */
public class AddressInOut {
    private static ArrayList<String> firstNameList = new ArrayList<>();
    private static ArrayList<String> lastNameList = new ArrayList<>();
    private static ArrayList<String> streetList = new ArrayList<>();
    private static ArrayList<String> cityList = new ArrayList<>();
    private static ArrayList<String> stateList = new ArrayList<>();

    private static ArrayList<Integer> zipList = new ArrayList<>();

    private static ArrayList<Long> buildingLongList = new ArrayList<>();

    private static int totalAddresses = 0;

    public static void loadAddresses() {
        int zipCode = 0;
        long length = 0;

        File dir = new File(MainActivity.getDIR(),"address.add");

        try {
            RandomAccessFile inout = new RandomAccessFile(dir, "rw");

            length = inout.length() / 150;

            for (int address = 0; address < length; address++) {
                StringBuilder firsNameS = new StringBuilder("");
                StringBuilder lastNameS = new StringBuilder("");
                long buildingLong = 0;
                StringBuilder streetS = new StringBuilder("");
                StringBuilder cityS = new StringBuilder("");
                StringBuilder stateS = new StringBuilder("");

                for (int firstName = 0; firstName < 16; firstName++) {
                    firsNameS.append(inout.readChar());
                }
                for (int lastName = 0; lastName < 16; lastName++) {
                    lastNameS.append(inout.readChar());
                }
                for (int street = 0; street < 16; street++) {
                    streetS.append(inout.readChar());
                }
                for (int city = 0; city < 21; city++) {
                    cityS.append(inout.readChar());
                }
                for (int state = 0; state < 2; state++) {
                    stateS.append(inout.readChar());
                }
                buildingLong = inout.readLong();
                zipCode = inout.readInt();

                addAddress(firsNameS.toString().trim()
                        , lastNameS.toString().trim()
                        , streetS.toString().trim()
                        , cityS.toString().trim()
                        , stateS.toString().trim()
                        , zipCode
                        , buildingLong);
            }

            inout.close();

            saveAddresses();
            System.out.println("addresses loaded");
        } catch (FileNotFoundException e) {
            System.out.println("FNFE");
        } catch (IOException e) {
            System.out.println("IOE");
        }
    }

    public static void saveAddresses(){


        try {
            File file = new File(MainActivity.getDIR(), "address.add");
            file.createNewFile();
            RandomAccessFile inout = new RandomAccessFile(file, "rw");
            for(int currentAddress = 0; currentAddress<totalAddresses; currentAddress++) {
                StringBuilder firsNameS = new StringBuilder(firstNameList.get(currentAddress));
                StringBuilder lastNameS = new StringBuilder(lastNameList.get(currentAddress));
                long buildingInt = buildingLongList.get(currentAddress);
                StringBuilder streetS = new StringBuilder(streetList.get(currentAddress));
                StringBuilder cityS = new StringBuilder(cityList.get(currentAddress));
                StringBuilder stateS = new StringBuilder(stateList.get(currentAddress));
                int zipCode = zipList.get(currentAddress);
                int length = 0;

                length = 16 - firsNameS.length();
                for (int i = 0; i < length; i++) {
                    firsNameS.append(" ");
                }
                length = 16 - lastNameS.length();
                for (int i = 0; i < length; i++) {
                    lastNameS.append(" ");
                }
                length = 16 - streetS.length();
                for (int i = 0; i < length; i++) {
                    streetS.append(" ");
                }
                length = 21 - cityS.length();
                for (int i = 0; i < length; i++) {
                    cityS.append(" ");
                }

                inout.writeChars(firsNameS.toString() + lastNameS.toString()
                        + streetS.toString() + cityS.toString() + stateS.toString());
                inout.writeLong(buildingInt);
                inout.writeInt(zipCode);
            }
            inout.close();
            System.out.println("Address Saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public static void addAddress(String firstName, String lastName, String street, String city, String state, Integer zip, Long building) {
        if(firstNameList.contains(firstName.trim())&&lastNameList.contains(lastName.trim())
                &&streetList.contains(street.trim())&&cityList.contains(city.trim())
                &&stateList.contains(state.trim())&&zipList.contains(zip)&&buildingLongList.contains(building)){
        }else {
            firstNameList.add(firstName.trim());
            lastNameList.add(lastName.trim());
            streetList.add(street.trim());
            cityList.add(city.trim());
            stateList.add(state.trim());
            zipList.add(zip);
            buildingLongList.add(building);
            totalAddresses++;
        }
    }


    public static ArrayList<String> getFirstNameList() {
        return firstNameList;
    }
    public static ArrayList<String> getLastNameList() {
        return lastNameList;
    }
    public static ArrayList<String> getStreetList() {
        return streetList;
    }
    public static ArrayList<String> getCityList() {
        return cityList;
    }
    public static ArrayList<String> getStateList() {
        return stateList;
    }
    public static ArrayList<Integer> getZipList() {
        return zipList;
    }
    public static ArrayList<Long> getBuildingLongList() {
        return buildingLongList;
    }
    public static int getTotalAddresses() {
        return totalAddresses;
    }

    public static RandomAccessFile toRandomAccessFile(InputStream inputStream, File tempFile, int fileSize) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "rw");

        byte[] buffer = new byte[fileSize];
        int numBytesRead = 0;

        while ( (numBytesRead = inputStream.read(buffer)) != -1 ) {
            randomAccessFile.write(buffer, 0, numBytesRead);
        }

        randomAccessFile.seek(0);

        return randomAccessFile;
    }
}
