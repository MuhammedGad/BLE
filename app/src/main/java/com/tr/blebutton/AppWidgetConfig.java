package com.tr.blebutton;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.tr.blebutton.DeviceList.KAPI_2;
import static com.tr.blebutton.DeviceList.KAPI_3;
import static com.tr.blebutton.DeviceList.KAPI_4;
import static com.tr.blebutton.DeviceList.KAPI_5;


public class AppWidgetConfig extends AppCompatActivity {
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    String ADDR;
    String PASS;
    public static final String SHARED_PREFS = "prefs";
    public static final String KAPI_1 = "keyButtonText";
    public static final String PASSWORD_1 = "pass1";
    public static final String PASSWORD_2 = "pass2";
    public static final String PASSWORD_3 = "pass3";
    public static final String PASSWORD_4 = "pass4";
    public static final String PASSWORD_5 = "pass5";
    public static final String PASSWORD_SAVED = "passsaved";
    public static final String ADDRESS_SAVED = "addresssaved";
    public static final String AUTO_CONNECT = "AUTO";



    DBhandeler DB;
    EditText AutoPass;
    Button editTextButton;
    EditText edit_text_button;
    Button isimkayt;
    String selected;
    TextView text;
    String ADDRe;
    String PASSE;
    PendingIntent pendingIntent;
    ArrayList<String> secimList;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_config);
        //AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        DB = new DBhandeler(this, null, null, 1);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String birincikapi = sharedPreferences.getString(KAPI_1, "Boş");
        String ikincikapi = sharedPreferences.getString(KAPI_2, "Boş");
        String ucuncukapi = sharedPreferences.getString(KAPI_3, "Boş");
        String dorduncukapi = sharedPreferences.getString(KAPI_4, "Boş");
        String besincikapi = sharedPreferences.getString(KAPI_5, "Boş");
        ADDRe = sharedPreferences.getString(ADDRESS_SAVED, "ADDR");
        PASSE = sharedPreferences.getString(PASSWORD_SAVED, "PASS");
        secimList = new ArrayList<>();
        secimList.add(birincikapi);
        secimList.add(ikincikapi);
        secimList.add(ucuncukapi);
        secimList.add(dorduncukapi);
        secimList.add(besincikapi);
        text = (TextView)findViewById(R.id.test);
        final Spinner secimSpiner = (Spinner)findViewById(R.id.secim_spinner);
        ArrayAdapter<String> secimadaptor = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, secimList );
        secimSpiner.setAdapter(secimadaptor);
        // isimkayt = (Button)findViewById(R.id.isim_kayet);
        editTextButton = (Button) findViewById(R.id.auto_button);
        edit_text_button = (EditText)findViewById(R.id.edit_text_button);
        AutoPass = (EditText)findViewById(R.id.auto_pass);
        secimSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        editTextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try
                {

                    DB.adjustconnection("TRUE");
                    String autopass = AutoPass.getText().toString();
                    if(autopass.length() > 4){
                        DB.addAutopass(autopass);}
                    //     text.setText("ok");
                    oto();
                    finish();

                } catch (Exception e) {e.printStackTrace(); }
            }
        });
        try {
            int ido = DB.NameTostring().length();

            String id = DB.NameTostring().substring(0,ido-1);
            appWidgetId = Integer.parseInt(id);

        }catch (Exception e){}

        //      text.setText(ido.toString());

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }


    }

    @SuppressLint("SetTextI18n")
    public void confirmConfiguration(View v) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.open_widget);
        Intent ActivityIntent = new Intent(this, AppWidgetConfig.class);
        PendingIntent ActivityIntentConfig = PendingIntent.getActivity(this,0,ActivityIntent,0);
        views.setOnClickPendingIntent(R.id.edit_text_button, ActivityIntentConfig);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AUTO_CONNECT, "FALSE");
        editor.apply();
        //  text.setText(ADDRe + PASSE + selected);
        DB.adjustconnection("FALSE");
        //    text.setText(DB.NameTostring());

        switch (selected.substring(0,1)){
            case "1": {
                String buttonText = edit_text_button.getText().toString();
                //  SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                // SharedPreferences.Editor editor = prefs.edit();
                if(buttonText.length() > 4){
                    DB.addpass(buttonText);}
                DB.addUsedadresses(DB.databaseToString());
                DB.addUsedpass(DB.databaseToStringPass());
         //       views.setCharSequence(R.id.idget_text, "setText", selected);
                break;
            }  case "2": {
                String buttonText = edit_text_button.getText().toString();
                //  SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                //    SharedPreferences.Editor editor = prefs.edit();
                if(buttonText.length() > 4){

                    DB.addSecondPass(buttonText);}
                DB.addUsedadresses(DB.SeconddatabaseToString());
                DB.addUsedpass(DB.SecondPasstoString());
        //        views.setCharSequence(R.id.idget_text, "setText", selected);

                break;
            }
            case "3": {
                String buttonText = edit_text_button.getText().toString();
                //   SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                //   SharedPreferences.Editor editor = prefs.edit();
                if(buttonText.length() > 4){

                DB.addThirdPass(buttonText);}
                DB.addUsedadresses(DB.ThirddatabaseToString());
                DB.addUsedpass(DB.ThirdPasstoString());
              //  views.setCharSequence(R.id.idget_text, "setText", selected);

                break;
            } case "4": {
                String buttonText = edit_text_button.getText().toString();
                //   SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                //   SharedPreferences.Editor editor = prefs.edit();
                if(buttonText.length() > 4){

                DB.addForthPass(buttonText);}
                DB.addUsedadresses(DB.ForthdatabaseToString());
                DB.addUsedpass(DB.ForthPasstoString());
            //    views.setCharSequence(R.id.idget_text, "setText", selected);

                break;
            }case "5": {
                String buttonText = edit_text_button.getText().toString();
                //   SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                //       SharedPreferences.Editor editor = prefs.edit();
                if(buttonText.length() > 4){

                    DB.addFifthPass(buttonText);}
                DB.addUsedadresses(DB.FifthdatabaseToString());
                DB.addUsedpass(DB.FifthPasstoString());
            //    views.setCharSequence(R.id.idget_text, "setText", selected);


                break;
            }



        }
        // String buttonText = editTextButton.getText().toString();

//finish();
        appWidgetManager.updateAppWidget(appWidgetId, views);
        finish();

    }
    public void oto(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.open_widget);
        String oto = "Otomatik Seçim";
      //  views.setCharSequence(R.id.idget_text, "setText", oto);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}