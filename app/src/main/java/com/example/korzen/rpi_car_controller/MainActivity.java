package com.example.korzen.rpi_car_controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.github.controlwear.virtual.joystick.android.JoystickView;


public class MainActivity extends BluetoothActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JoystickView joystick = (JoystickView) findViewById(R.id.joystick);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                write(angle + "#" + strength);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bluetoothDevice != null) {
            connect(bluetoothDevice);
        }
    }
}
