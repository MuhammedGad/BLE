package com.tr.blebutton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.blebutton.admin.AdminUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Login extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();
    EditText anahtar, isim, dairenumarasi, BlokAdi;
    String key, daire, blok, tamisim;
    boolean keyok = false;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    FirebaseAuth mAuth;
    String uid;
    FirebaseDatabase database;
    Button kaytolbt, BACK;
    public String mail;
    public String pass;
   // public String key;
    AdminUser newAdmin;
    String sGelenVeri;
   // ArrayList<String> list = new ArrayList<>();
  //  boolean keyok = false;
    private String mDeviceName;
    private String mDeviceAddress;
    String index;
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
            Toast.makeText(Login.this,"ok", Toast.LENGTH_SHORT).show();

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

                Toast.makeText(Login.this,"connected", Toast.LENGTH_SHORT).show();
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
                try {


                    index = readMessage.substring(15);

                    uid = readMessage.substring(7,15);
                    DB.Addkey(key);
                    DB.AddIndex(index);
                    DB.AddUid(uid);
                    NormalUser newUser = new NormalUser();


                        newUser.setBlokAdi(blok);
                        newUser.setDaireNo(daire);
                        newUser.setIndex(index);
                        newUser.setKey(key);
                        newUser.setTamisim(tamisim);
                        newUser.setUID(uid);
                        Toast.makeText(Login.this,index, Toast.LENGTH_SHORT).show();
                        int UserId = (Integer.parseInt(index) / 4) - 7;
                    FirebaseDatabase.getInstance().getReference("Cihazlar").child("Uid:"+readMessage.substring(7,15)).child(key)
                            .setValue(newUser);
                    Toast.makeText(Login.this,index, Toast.LENGTH_SHORT).show();



                }catch (Exception E){}

            }
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView daire = (TextView)findViewById(R.id.textView);
         Button back = (Button)findViewById(R.id.back);
         Button giris = (Button)findViewById(R.id.onayla);
         anahtar = (EditText)findViewById(R.id.anahtar);
         isim = (EditText)findViewById(R.id.isim);
        final Intent intent = getIntent();
        daire.setPaintFlags(daire.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
         dairenumarasi = (EditText)findViewById(R.id.daire_numarasi);
         BlokAdi = (EditText)findViewById(R.id.blok_adi);
        DB = new DBhandeler(this, null, null, 1);
        // mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        Intent gattServiceIntent = new Intent(this, BluetoothConnection.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        Toast.makeText(Login.this,mDeviceAddress, Toast.LENGTH_SHORT).show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anasayfa = new Intent(Login.this, anasayfa.class);
                startActivity(anasayfa);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Keys");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getValue(String.class);
                    if(key != null){
                        list.add(key);}

                    //   System.out.println(key + bayikodu);
                    System.out.println(list);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uyumlukayit();
                if(uyumlukayit()){
                    sendDataToBLE("yu"+key);

                    database.getReference("Keys").child(key).removeValue();




                }}
        });
    }
    private boolean uyumlukayit(){
        daire = dairenumarasi.getText().toString().trim();
        blok = BlokAdi.getText().toString().trim();
        tamisim = isim.getText().toString().trim();

        key = anahtar.getText().toString();
        for(String dkey : list) {

            if(dkey.equals(key) ){
                System.out.println("Done");
                keyok = true;
                // Intent intent = new Intent(adminlog.this, adminpage.class);
                // startActivity(intent);
                //hata.setVisibility(View.INVISIBLE);
                //  return true;
            }}

            if(!keyok){
                anahtar.setError("anahtar hatalidir");
                anahtar.requestFocus();
                return false;
            }

         if(tamisim.isEmpty()){
            isim.setError("Adı / SoyAdı Boş Bırakılmaz");
            isim.requestFocus();
            return false;
        }

        if(daire.isEmpty()){
            dairenumarasi.setError("daire numarası boş bırakılmaz");
            dairenumarasi.requestFocus();
            return false;
        }
        if(blok.isEmpty()){
            BlokAdi.setError("Blok Adı Boş Bırakılmaz");
            BlokAdi.requestFocus();
            return false;
        }   else {

            keyok = false;
            return true;
        }
    }
    void sendDataToBLE(String str) {
       // Log.d(TAG, "Sending result=" + str);
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
          //  Log.d(TAG, "Connect request result=" + result);
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
    }}