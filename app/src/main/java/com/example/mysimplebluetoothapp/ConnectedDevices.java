package com.example.mysimplebluetoothapp;

import android.os.Handler;
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
        service = MyBluetoothService.getInstance(this, new Handler());
        connAs = findViewById(R.id.tv_connAs);
    }

    public synchronized void connAsServer(View view) {
        connAs.setText("Verbunden als Server");
        MyBluetoothService.BluetoothServer server = service.new BluetoothServer();
        server.start();
    }

    public synchronized void connAsClient(View view) {
        connAs.setText("Verbunden als Client");
        String adresse = getIntent().getStringExtra("adresse");
        client = service.new BluetoothClient(adresse);
    }

    public synchronized void sendMessage(View view) {
        client.start();
    }
}
