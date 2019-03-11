package com.example.mysimplebluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import android.os.Handler;
import android.widget.TextView;

import java.util.UUID;

public class MyBluetoothService {

    private static MyBluetoothService bluetoothService;
    private Context context;
    private Handler handler;
    private BluetoothAdapter adapter;
    private TextView output;
    public static final UUID UUID_ = UUID.randomUUID();

    private MyBluetoothService(Context context, Handler handler){
        this.context = context;
        this.handler = handler;
        adapter = BluetoothAdapter.getDefaultAdapter();

    }

    public static synchronized MyBluetoothService getInstance(Context context,Handler handler){
        if(bluetoothService == null){
            bluetoothService = new MyBluetoothService(context,handler);
        }
        return bluetoothService;
    }

    public class BluetoothServer extends Thread{

    }

    public class BluetoothClient extends Thread{

    }
}
