package tnkwong.cs190i.cs.ucsb.edu.colormind;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private final int REQUEST_ENABLE_BT = 1010;
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothAdapter mBluetoothAdapter;
    private UUID myUUID = new UUID(12, 25);

    private FragmentManager fm;
    private ArrayList<Integer> design;

    private final String TAG = "Debug";

    private ListView listView;

    private Button waitButton;
    private Button findButton;
    private Button saveButton;
    private Button sendButton;
    private Button startButton;

    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private HashMap<String, BluetoothDevice> btDeviceMap = new HashMap<>();

    private ConnectedThread myConnection;

    private BluetoothDevice foundDevice;

    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private RetrieveThread rt;

    private boolean connected = false;
    private boolean connectionSet = false;

    private ButtonsFragment saveFrag;

    private Bundle btBundle;
    private ArrayList<Integer> btSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        fm = getSupportFragmentManager();
        saveFrag = (ButtonsFragment) fm.findFragmentById(R.id.saveFragment);
        saveFrag.enable();
        saveFrag.btLayout();

        listView = (ListView) findViewById(R.id.deviceLIst);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = parent.getItemAtPosition(position).toString();
                connectThread = new ConnectThread(btDeviceMap.get(key));
                connectThread.start();
                Log.i("DEBUG", "Connected to " + key);
            }
        });

        btSetup();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        waitButton = (Button) findViewById(R.id.waitButton);
        waitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    findButton.setVisibility(View.GONE);
                    acceptThread = new AcceptThread();
                    acceptThread.start();
                } else
                    Toast.makeText(BluetoothActivity.this, "Bluetooth not connected yet", Toast.LENGTH_SHORT).show();
            }
        });

        findButton = (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitButton.setVisibility(View.GONE);
                mBluetoothAdapter.startDiscovery();
            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                design = saveFrag.getColorVals(5);
                sendButton.setVisibility(View.VISIBLE);
            }
        });

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setVisibility(View.GONE);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionSet) {
                    String s = "";
                    for (int i : design)
                        s += Integer.toString(i) + " ";
                    byte[] b = s.getBytes(Charset.forName("UTF-8"));
                    myConnection.write(b);
                    sendButton.setVisibility(View.GONE);
                }

            }
        });

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

    }

    public void startGame() {
        Intent i = new Intent(this, GameActivity.class);
        int[] settingsCode = {0, 5, 10};
        if(btSolution != null) {
            for(Integer x : btSolution){
                Log.i(TAG, "startGame: Number " + Integer.toString(x));
            }
            i.putIntegerArrayListExtra("bt", btSolution);
            i.putExtra("bluetooth", true);
        }
        i.putExtra("settings", settingsCode);
        startActivity(i);
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                foundDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = foundDevice.getName();
                btDeviceMap.put(deviceName, foundDevice);
                String deviceHardwareAddress = foundDevice.getAddress(); // MAC address
                Toast.makeText(context, "Found device!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: Found device " + deviceName);
                mDeviceList.add(deviceName);
                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket
            // because mmServerSocket is final.
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Test", myUUID);
            } catch (IOException e) {
                Log.e("DEBUG", "Socket's listen() method failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned.
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e("DEBUG", "Socket's accept() method failed", e);
                    break;
                }

                if (socket != null) {
                    // A connection was accepted. Perform work associated with
                    // the connection in a separate thread.
                    Log.i(TAG, "run: CONNECTED");
                    manageMyConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        Log.e("DEBUG", "Could not close the connect socket", e);
                    }
                    break;
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e("DEBUG", "Could not close the connect socket", e);
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(myUUID);
            } catch (IOException e) {
                Log.e("DEBUG", "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e("DEBUG", "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e("DEBUG", "Could not close the client socket", e);
            }
        }
    }


    private void btSetup() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            connected = false;
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
/*        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);*/
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Toast.makeText(this, "Bluetooth service running", Toast.LENGTH_SHORT).show();
        connected = true;
        return;
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {
        myConnection = new ConnectedThread(socket);
        myConnection.start();
        Log.i(TAG, "manageMyConnectedSocket: Connected to device");
        connectionSet = true;
        rt = new RetrieveThread();
        rt.start();
    }

    private class RetrieveThread extends Thread {
        public RetrieveThread() {

        }

        public void run() {
            while (true) {
                try {
                    String in = myConnection.outputs.take();
                    if (in != null) {
                        Log.i(TAG, "run: Messaged received: " + in);
                        messageReceived(in);
                    }
                } catch (InterruptedException e) {
                    ;
                }
            }
        }
    }

    public void messageReceived(String in) {
        String[] design = in.split(" ");
        btSolution = new ArrayList<>(5);
        for (String num : design) {
            try {
                btSolution.add(Integer.parseInt(num));
            }
            catch (NumberFormatException e){
                ;
            }
        }
    }
}
