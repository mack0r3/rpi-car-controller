package com.example.korzen.rpi_car_controller;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by korzen on 20.11.16.
 */

public class BluetoothRemoteControlApp {

    public final static int MSG_CONNECTED = 0;
    public final static int MSG_CONNECTION_ERROR = 1;

    private BluetoothThread bluetoothThread;
    private Handler handler;

    public BluetoothRemoteControlApp() {

    }

    public synchronized void connect(BluetoothDevice device) {
        bluetoothThread = new BluetoothThread(device);
        bluetoothThread.start();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void sendMessage(int what, Object object) {
        if (handler != null) {
            handler.obtainMessage(what, object).sendToTarget();
        }
    }

    public boolean write(String message) {
        if (bluetoothThread != null) {
            return bluetoothThread.write(message);
        }

        return false;
    }

    private class BluetoothThread extends Thread {

        private final String uuidString = "1ea6b388-0000-1000-8000-00805f9b34fb";
        private BluetoothSocket socket;
        private OutputStream outputStream;

        public BluetoothThread(BluetoothDevice device) {
            BluetoothSocket tempSocket = null;
            try {
                tempSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuidString));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            socket = tempSocket;
        }

        @Override
        public void run() {
            try {
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage(MSG_CONNECTION_ERROR, null);
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            sendMessage(MSG_CONNECTED, null);

            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean write(String message) {
            if (outputStream == null || message == null) {
                return false;
            }

            byte[] data = message.getBytes();

            try {
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
