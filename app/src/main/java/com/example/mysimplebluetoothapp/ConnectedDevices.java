package com.example.mysimplebluetoothapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConnectedDevices extends AppCompatActivity {

    private MyBluetoothService service;
    private MyBluetoothService.BluetoothClient client;
    private TextView connAs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);
    }

    public void connAsServer(View view) {
    }

    public void cannAsClient(View view) {
    }

    public void sendMessage(View view) {
    }
}
