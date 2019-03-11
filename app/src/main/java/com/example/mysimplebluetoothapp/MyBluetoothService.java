package com.example.mysimplebluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MyBluetoothService {

    private static MyBluetoothService bluetoothService;
    private Context context;
    private Handler handler;
    private BluetoothAdapter adapter;
    private TextView output;
    public static final UUID UUID_ = UUID.randomUUID();
    private static final String TAG = MyBluetoothService.class.getSimpleName();

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
        private final BluetoothServerSocket serverSocket;

        public BluetoothServer(){
            BluetoothServerSocket tmp = null;
            try {
                tmp = adapter.listenUsingInsecureRfcommWithServiceRecord("Server",UUID_);
            } catch (IOException e) {
                Log.e(TAG, "Server nicht bereit", e);
            }
            serverSocket = tmp;
        }

        public void run(){
            BluetoothSocket socket;
            InputStream inputStream;
            int bytes;
            while (true){
                final byte[] buffer = new byte[1024];
                try {
                    socket = serverSocket.accept();
                    inputStream = socket.getInputStream();
                    bytes = inputStream.read(buffer);
                    handler.post(()->output.append(new String(buffer) + "\n"));

                } catch (IOException e) {
                    Log.e(TAG, "Verbindung fehlgeschlagen", e);
                }
            }
        }

    }

    public class BluetoothClient extends Thread{

        public BluetoothClient(String adresse) {

        }
    }
}
