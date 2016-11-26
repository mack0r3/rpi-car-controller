package com.example.korzen.rpi_car_controller;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends BluetoothActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConnectClicked(View view) {
        if(bluetoothDevice != null) {
            connect(bluetoothDevice);
        }
    }

    public void onOnClicked(View view) {
        write("1");
    }

    public void onOffClicked(View view) {
        write("0");
    }
}
