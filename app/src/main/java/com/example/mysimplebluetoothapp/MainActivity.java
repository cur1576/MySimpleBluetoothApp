package com.example.mysimplebluetoothapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listViewDevices;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> list;
    private Set<BluetoothDevice> bondetDevices;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getAddress() + " - " + device.getName();
                arrayAdapter.add(name);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewDevices = findViewById(R.id.list_view_devices);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listViewDevices.setAdapter(arrayAdapter);
        listViewDevices.setOnItemClickListener((parent, view,position,id)->{
            String adresse_plus = (String)parent.getItemAtPosition(position);
            String adresse = adresse_plus.substring(0,adresse_plus.indexOf("-")).trim();
            Intent intent = new Intent(MainActivity.this,ConnectedDevices.class);
            intent.putExtra("adresse",adresse);
            startActivity(intent);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            }
        }

        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, foundFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }

    public void turnOn(View view) {
        if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(turnOn);
            Toast.makeText(this, "Turned On", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Already On", Toast.LENGTH_SHORT).show();
        }
    }

    public void turnOff(View view) {
        bluetoothAdapter.disable();
        Toast.makeText(this, "Turned off", Toast.LENGTH_SHORT).show();
    }


    public void getVisible(View view) {
        Intent setVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        setVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,180);
        startActivity(setVisible);
    }

    public void listDevices(View view) {
        bondetDevices = bluetoothAdapter.getBondedDevices();
        arrayAdapter.clear();
        for(BluetoothDevice device : bondetDevices){
            arrayAdapter.add(device.getAddress() + " - " + device.getName());
        }
    }

    public void searchDevices(View view) {
        arrayAdapter.clear();
        bluetoothAdapter.startDiscovery();
    }
}
