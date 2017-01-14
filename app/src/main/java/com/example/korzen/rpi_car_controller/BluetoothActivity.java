package com.example.korzen.rpi_car_controller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by korzen on 20.11.16.
 */

public class BluetoothActivity extends AppCompatActivity {

    private static final int ENABLE_BLUETOOTH = 1;

    private static BluetoothRemoteControlApp btControlApp;

    protected BluetoothDevice bluetoothDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btControlApp = new BluetoothRemoteControlApp();
        bluetoothDevice = findPairedDevice("raspberrypi");
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableBluetoothIfNecessary();
    }

    protected void setHandler(Handler handler) {
        btControlApp.setHandler(handler);
    }

    protected void connect(BluetoothDevice device) {
        btControlApp.connect(device);
    }

    protected boolean write(String message) {
        return btControlApp.write(message);
    }

    private void enableBluetoothIfNecessary() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, ENABLE_BLUETOOTH);
        }

    }

    private BluetoothDevice findPairedDevice(String deviceName) {
        BluetoothAdapter bluetoothAdapter;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice pairedDevice: pairedDevices) {
            if(pairedDevice.getName().equals(deviceName))
                return pairedDevice;
        }

        Toast.makeText(this, "Your device is not paired with " + deviceName, Toast.LENGTH_SHORT).show();

        return null;
    }
}
