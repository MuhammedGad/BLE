package com.tr.blebutton;

import androidx.annotation.NonNull;
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
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.blebutton.admin.AdminLogin;
import com.tr.blebutton.admin.AdminUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AdminRegister extends AppCompatActivity {
    private final static String TAG = AdminRegister.class.getSimpleName();
    DBhandeler DB;
    EditText email,  sifre ,sifreonay ,anahtar;
    private StringBuilder sb = new StringBuilder();
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Button kaytolbt, BACK;
    public String mail;
    public String pass;
    public String key;
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
            Toast.makeText(AdminRegister.this,"ok", Toast.LENGTH_SHORT).show();

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

                Toast.makeText(AdminRegister.this,"connected", Toast.LENGTH_SHORT).show();
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
                newAdmin  = new AdminUser();
                newAdmin.setEmail(email.getText().toString());
                newAdmin.setPass(sifre.getText().toString());
                newAdmin.setKey(anahtar.getText().toString());
                DB.AddUid(readMessage.substring(4,12));
                DB.AddIndex("4");
                DB.Addkey(newAdmin.getKey());
                mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminRegister.this, readMessage.substring(4), Toast.LENGTH_SHORT);
                            FirebaseDatabase.getInstance().getReference("Cihazlar").child("yoneticiler").child("Uid:"+readMessage.substring(4,12))
                                    .setValue(newAdmin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        DB.Addkey(newAdmin.getKey());
                                        Toast.makeText(AdminRegister.this,"Kayt işlemi başarıyla tamamlanmıştır", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(AdminRegister.this,"Kayt işlemi başarıyla tamamlanmıştır", Toast.LENGTH_SHORT).show();
                                        System.out.println(DB.UidToString());
                                    }else{
                                        Toast.makeText(AdminRegister.this,"Kayt yapilamamis", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            } ); } }});

            }
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        kaytolbt = (Button) findViewById(R.id.adminRegister);
        email = (EditText)findViewById(R.id.EmailR);
        sifre = (EditText)findViewById(R.id.PasswordR);
        sifreonay = (EditText)findViewById(R.id.PasswordRonay);
        anahtar = (EditText)findViewById(R.id.anahtarR);
        BACK = (Button)findViewById(R.id.adminRback);
        final Intent intent = getIntent();
        DB = new DBhandeler(this, null, null, 1);
       // mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        Intent gattServiceIntent = new Intent(this, BluetoothConnection.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        Toast.makeText(AdminRegister.this,mDeviceAddress, Toast.LENGTH_SHORT).show();

        try{
            String getAdk = DB.keyToString();
            Toast.makeText(AdminRegister.this,getAdk, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(AdminRegister.this,"YOK", Toast.LENGTH_SHORT).show();

        }

        mAuth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
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

        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(AdminRegister.this, AdminLogin.class);
                startActivity(main);
            }
        });
        kaytolbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

// && readMessage != null
                if(uyumlukayit()){
                    String data = "yy";
                    data+= anahtar.getText().toString().replace("-", "");
                    //   data = "*8912-9613-1516-2862A";
                    Toast.makeText(AdminRegister.this,data, Toast.LENGTH_SHORT).show();
                    sendDataToBLE(data);

                        database.getReference("Keys").child(anahtar.getText().toString().trim()).removeValue();



                }
            }
        });

    }
    public boolean uyumlukayit(){
        pass = sifre.getText().toString().trim();
        String passconfirm = sifreonay.getText().toString().trim();
        mail = email.getText().toString().trim();
         key = anahtar.getText().toString();
        for(String dkey : list) {

            if(dkey.equals(key) ){
                System.out.println("Done");
                keyok = true;
                // Intent intent = new Intent(adminlog.this, adminpage.class);
                // startActivity(intent);
                //hata.setVisibility(View.INVISIBLE);
               // return true;
            }

        }
        if(!keyok){
            anahtar.setError("anahtar hatalidir");
            anahtar.requestFocus();
            return false;
        }
        if(mail.isEmpty()){
            email.setError("Email boş bırakılmaz");
            email.requestFocus();
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Lütfen Doğru Email Yazınız");
            email.requestFocus();
            return false;
        }
        if(pass.isEmpty()){
            sifre.setError("Şifre Boş Bırakılmaz");
            sifre.requestFocus();
            return false;
        } else if(pass.length() > 10){
            sifre.setError("Şifre 5 hane den fazla olmaz");
            sifre.requestFocus();
            return false;
        }else if(!passconfirm.equals(pass)){
            sifreonay.setError("Şifre Eşit değildir");
            sifreonay.requestFocus();
            return false;
        }else {

            keyok = false;

            return true;
        }
    }
    void sendDataToBLE(String str) {
        Log.d(TAG, "Sending result=" + str);
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
            Log.d(TAG, "Connect request result=" + result);
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