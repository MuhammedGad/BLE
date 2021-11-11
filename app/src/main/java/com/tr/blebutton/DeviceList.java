package com.tr.blebutton;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Set;

import static com.tr.blebutton.AppWidgetConfig.KAPI_1;
import static com.tr.blebutton.AppWidgetConfig.SHARED_PREFS;


public class DeviceList extends Activity {
  //  private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    String selected;
    EditText editTextButton;
    String address;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String KAPI_2 = "ikinci kapi";
    public static final String KAPI_3 = "ucuncu kapi";
    public static final String KAPI_4 = "dordncu kapi";
    public static final String KAPI_5 = "besinci kapi";
    public static final String ADRESS_1 = "address1";
    public static final String ADRESS_2 = "address2";
    public static final String ADRESS_3 = "address3";
    public static final String ADRESS_4 = "address4";
    public static final String ADRESS_5 = "address5";
    private AlertDialog enableNotificationListenerAlertDialog;

    /**
     * Tag for Log
     */
    private static final String TAG = "DeviceList";

    /**
     * Return Intent extra
     */
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    /**
     * Member fields
     */
    private BluetoothAdapter mBtAdapter;

    /**
     * Newly discovered devices
     */
    TextView adresstext;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    DBhandeler DB;

    // @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window
        DB = new DBhandeler(this, null, null, 1);

        //  requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_sabit);
        editTextButton = (EditText) findViewById(R.id.edit_text_button);
        Button kayt = (Button)findViewById(R.id.kayt);
        adresstext  = (TextView)findViewById(R.id.adress_view);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try
                {


                    selected = parentView.getItemAtPosition(position).toString();





                } catch (Exception e) {e.printStackTrace(); }}
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        kayt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (selected) {
                    case "1": {
                        String buttonText = editTextButton.getText().toString();
                        DB.addpass(buttonText);
                        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString(KAPI_1, selected + " - " + buttonText);
                        editor.apply();
                        finish();
                        break;
                    }
                    case "2": {
                        String buttonText = editTextButton.getText().toString();
                        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString(KAPI_2, selected + " - " + buttonText);
                        editor.apply();
                        finish();

                        break;
                    }
                    case "3": {
                        String buttonText = editTextButton.getText().toString();
                        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString(KAPI_3, selected + " - " + buttonText);
                        editor.apply();
                        finish();

                        break;
                    }  case "4": {
                        String buttonText = editTextButton.getText().toString();
                        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString(KAPI_4, selected + " - " + buttonText);
                        editor.apply();
                        finish();

                        break;
                    }case "5": {
                        String buttonText = editTextButton.getText().toString();
                        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString(KAPI_5, selected + " - " + buttonText);
                        editor.apply();
                        finish();

                        break;
                    }
                }


            }

        });
        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        Button scanButton = findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setVisibility(View.VISIBLE);



            }

        });
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            PERMISSIONS = new String[]{
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,

            };
        }

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
    /*    ListView pairedListView = findViewById(R.id.paired_devices);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);*/

        // Find and set up the ListView for newly discovered devices
          ListView newDevicesListView = findViewById(R.id.new_devices);
          newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
         newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        //findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            address = info.substring(info.length() - 17);
            prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

            editor = prefs.edit();
            switch (selected){
                case "1": {
                    DB.addadresses(address);
                    break;
                }

                case "2": {
                    DB.addSecondadresses(address);
                    break;
                }
                case "3": {
                    DB.addThirdadresses(address);
                    break;
                }
                case "4": {
                    DB.addForthadresses(address);
                    break;
                }
                case "5": {
                    DB.addFifthadresses(address);
                    break;
                }}

            //  String ADPREF = prefs.getString(ADRESS_1, "ADDRESS");

            adresstext.setText("Cihaz Seçildi");
            // Create the result Intent and include the MAC address

        /*    Intent p = new Intent(DeviceList.this, MainActivity.class);
            p.putExtra(EXTRA_DEVICE_ADDRESS, address);
            Intent service = new Intent(DeviceList.this, BluetoothService.class);
            service.putExtra(EXTRA_DEVICE_ADDRESS, address);
            // Set result and finish this Activity
            startActivity(p);*/
        }
    };

    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // mBluetoothAdapter.startDiscovery();
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // If it's already paired, skip it, because it's been listed already
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                Toast.makeText(getApplicationContext(), "BT Bulundu", Toast.LENGTH_SHORT).show();
                if(device.getName() != null){

                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

}
