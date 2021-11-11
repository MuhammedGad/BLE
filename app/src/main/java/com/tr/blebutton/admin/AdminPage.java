package com.tr.blebutton.admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.blebutton.AdminRegister;
import com.tr.blebutton.BluetoothConnection;
import com.tr.blebutton.CihazIsmiDialog;
import com.tr.blebutton.DBhandeler;
import com.tr.blebutton.NormalUser;
import com.tr.blebutton.R;
import com.tr.blebutton.SampleGattAttributes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AdminPage extends AppCompatActivity implements CihazIsmiDialog.SaveDialogListener{
    NormalUser clickItemObj;
    String id;
    Integer lastid;
    Integer theid;
    ArrayList<String> idlist = new ArrayList<>();
    Integer b = 0;
    Button eslesme;
    List<String> dataList = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Key");
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    FirebaseAuth mAuth;
   // FirebaseDatabase database;
    Button kaytolbt, BACK;
    public String mail;
    Button eslesmekapat;
    public String pass;
    public String key;
    String gelenkey;
    AdminUser newAdmin;
    String sGelenVeri;
    ArrayList<String> list = new ArrayList<>();
    boolean keyok = false;
    private String mDeviceName;
    private String mDeviceAddress;
    //  private ExpandableListView mGattServicesList;
    private BluetoothConnection mBluetoothLeService;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;
    Spinner propspin, valuespin;
    Date Current_Time = Calendar.getInstance().getTime();
    final String FormatedDate = DateFormat.getDateInstance(DateFormat.FULL).format(Current_Time);
    final String FormatedTime = DateFormat.getTimeInstance().format(Current_Time);
    DBhandeler DB;

    String readMessage;


    public final static UUID HM_RX_TX =
            UUID.fromString(SampleGattAttributes.HM_RX_TX);

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";


    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothConnection.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                // Log.e(TAG, "Unable to initialize Bluetooth");
                finish();

            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            System.out.println(mDeviceAddress);
            //     mBluetoothLeService.readCharacteristic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothConnection.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;

                Toast.makeText(AdminPage.this,"connected", Toast.LENGTH_SHORT).show();
                //   updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothConnection.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //    updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
            } else if (BluetoothConnection.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothConnection.ACTION_DATA_AVAILABLE.equals(action)) {
                readMessage = intent.getStringExtra(BluetoothConnection.EXTRA_DATA);
              /*   try {
                     String data = readMessage.substring(readMessage.indexOf("K")+1, readMessage.indexOf("Y"));
                     newAdmin.setKeyIndex(data);
                     DatabaseReference Ref = database.getReference("YoneticiIndex");
                     Ref.push().setValue(anahtar.getText().toString()+"/"+data);
                     DB.Addkey(anahtar.getText().toString()+"/"+data);
                 }catch (Exception e){}*/

                    if(readMessage.equals("acik")){
                        eslesme.setBackgroundColor(Color.RED);
                        eslesmekapat.setBackgroundColor(Color.WHITE);

                    }else if(readMessage.equals("kapali")){
                        eslesme.setBackgroundColor(Color.WHITE);
                        eslesmekapat.setBackgroundColor(Color.RED);
                    }
            }
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        eslesme = (Button)findViewById(R.id.eslesme);
        ListView mListView = (ListView)findViewById(R.id.user_list);
         eslesmekapat = (Button)findViewById(R.id.kapat);
         Spinner spinner = (Spinner) findViewById(R.id.dialogs);
         Button service = (Button)findViewById(R.id.button2);
        final Intent intent = getIntent();
        DB = new DBhandeler(this, null, null, 1);
        // mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        Intent gattServiceIntent = new Intent(this, BluetoothConnection.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        ekleme();
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToBLE("son");
                Toast.makeText(AdminPage.this, "son " , Toast.LENGTH_SHORT).show();
            }
        });
        eslesme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToBLE("submodeon");

            }
        });
        eslesmekapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToBLE("submodeoff");

            }
        });
        String id = DB.UidToString();
        System.out.println(id);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView)selectedItemView).setText(null);
                try
                {


                    String selected = parentView.getItemAtPosition(position).toString();

                    if(selected.equals("Cihaz ismi değiştir")){
                        CihazDialog();
                        spinner.setSelection(0);
                    }



                } catch (Exception e) {e.printStackTrace(); }}
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        //   Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menuR, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        DatabaseReference mRef = database.getReference("Cihazlar").child("Uid:"+id.substring(0,8));
       // mListView = (ListView) findViewById(R.id.ListView);
        try {

            FirebaseListAdapter mAdapter = new FirebaseListAdapter<NormalUser>(this, NormalUser.class,R.layout.mylayout,mRef) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void populateView(View view, NormalUser myObj, int position) {
                //Set the value for the views
                    System.out.println(myObj.getTamisim());
                ((TextView)view.findViewById(R.id.tamisim)).setText("Ad / SoyAd:  "+myObj.getTamisim());
                ((TextView)view.findViewById(R.id.blokAdi)).setText("Blok İsimi:  "+myObj.getBlokAdi());
                ((TextView)view.findViewById(R.id.daireNo)).setText("Daire Numarası:  "+myObj.getDaireNo());

            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItemObj = (NormalUser) parent.getAdapter().getItem(position);
                String mkey = clickItemObj.getKey();
                sendDataToBLE("du"+clickItemObj.getIndex()+"i"+mkey);
                mRef.child(mkey).removeValue();
                Toast.makeText(AdminPage.this, "You removed " + mkey, Toast.LENGTH_SHORT).show();

            }
        });
}catch (Exception e){}
    }
    private void arrayAdapterListView()
    {

        setTitle("Yönetici Sayfası");



        ListView listView = (ListView)findViewById(R.id.user_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dataList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                try {

                 //   clickItemObj = adapterView.getAdapter().getItem(index);
                    Toast.makeText(AdminPage.this, "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
    }

    public void ekleme(){
        try {


        }catch (Exception E){}

    }

    public void silme(View v){
        try {
            dataList.remove(clickItemObj.toString());

        }catch (Exception e){}
    }
    void sendDataToBLE(String str) {
      //  Log.d(TAG, "Sending result=" + str);
        final byte[] tx = str.getBytes();
        if (mConnected) {
            characteristicTX.setValue(tx);
            mBluetoothLeService.writeCharacteristic(characteristicTX);
            mBluetoothLeService.setCharacteristicNotification(characteristicRX, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
           // Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }*/

  /*  @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // mConnectionState.setText(resourceId);
            }
        });
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.en);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();


        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));

            // If the service exists for HM 10 Serial, say so.
            if (SampleGattAttributes.lookup(uuid, unknownServiceString) == "HM 10 Serial") {
                //     isSerial.setText("Yes");
            } else {
//                isSerial.setText("No");
            }
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            // get characteristic when UUID matches RX/TX UUID
            characteristicTX = gattService.getCharacteristic(BluetoothConnection.UUID_HM_RX_TX);
            characteristicRX = gattService.getCharacteristic(BluetoothConnection.UUID_HM_RX_TX);
        }
        // String data = "*8912-9613-1516-2245A";
        // sendDataToBLE(data);


    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothConnection.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothConnection.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothConnection.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothConnection.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
    public void CihazDialog() {
        CihazIsmiDialog isimDialog = new CihazIsmiDialog();
        isimDialog.show(getSupportFragmentManager(), "Cihaz Dialog");
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void applyS(String Harirla, String hatirlass) {
        String isim = Harirla;
        sendDataToBLE("AT+NAME"+isim);
    }

}