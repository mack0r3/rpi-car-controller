package com.example.korzen.rpi_car_controller;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static android.view.View.GONE;


public class MainActivity extends BluetoothActivity implements Handler.Callback {

    private Button connectButton;
    private JoystickView joystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.setHandler(new Handler(this));
        connectButton = (Button) findViewById(R.id.connect_button);

        joystick = (JoystickView) findViewById(R.id.joystick);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                write(angle + "#" + strength);
            }
        });
    }

    public void onConnectClicked(View view) {
        if (bluetoothDevice != null) {
            connectButton.setText("Connecting...");
            connectButton.setEnabled(false);
            connect(bluetoothDevice);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case BluetoothRemoteControlApp.MSG_CONNECTED:
                connectButton.setText("Connected");
                connectButton.setEnabled(false);
                break;
            case BluetoothRemoteControlApp.MSG_CONNECTION_ERROR:
                connectButton.setEnabled(true);
            default:
                break;
        }

        return false;
    }
}
