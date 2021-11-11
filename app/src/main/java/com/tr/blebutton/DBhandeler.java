package com.tr.blebutton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBhandeler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Button.db";
    public static final String TABLE_NAME = "NAME";
    public static final String TABLE_KEY = "AdminKey";
    public static final String TABLE_INDEX = "INDEXT";
    public static final String TABLE_UID = "UID";

    public static final String TABLE_FIRSTADDRESS = "Address";
    public static final String TABLE_SECONDADDRESS = "SecondAddress";
    public static final String TABLE_THIRDADDRESS = "ThirdAddress";
    public static final String TABLE_FORTHADDRESS = "ForthAddress";
    public static final String TABLE_FIFTHADDRESS = "FifthAddress";
    public static final String TABLE_USEDADDRESSES = "UsedAddress";

    public static final String TABLE_CONNECTION = "Connections";

    public static final String TABLE_PASS = "Pass";
    public static final String TABLE_SECONDPASS = "SecondPass";
    public static final String TABLE_THIRDDPASS = "ThirdPass";
    public static final String TABLE_FORTHDPASS = "ForthPass";
    public static final String TABLE_FIFTHDPASS = "FifthPass";
    public static final String TABLE_USEDPASS = "UsedPass";

    public static final String TABLE_AUTOPASS = "AutoPass";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTADDRESSES = "savdeAddr";
    public static final String COLUMN_SECONDADDRESSES = "SecondsavdeAddr";
    public static final String COLUMN_THIRDADDRESSES = "ThirdsavdeAddr";
    public static final String COLUMN_FORTHADDRESSES = "ForthsavdeAddr";
    public static final String COLUMN_FIFTHADDRESSES = "FifthsavdeAddr";
    public static final String COLUMN_USEDADDRESES = "savedUsedAddress";
    public static final String COLUMN_NAME = "savedNAME";
    public static final String COLUMN_KEY = "AdminKeyC";
    public static final String COLUMN_INDEX = "INDEXC";
    public static final String COLUMN_UID = "UIDC";

    public static final String COLUMN_CONNECTIONS = "ConnectionTypes";

    public static final String COLUMN_PASSES = "savedPass";
    public static final String COLUMN_SECONDPASSES = "savedSecondPass";
    public static final String COLUMN_THIRDPASSES = "savedThirdPass";
    public static final String COLUMN_FORTHPASSES = "savedForthPass";
    public static final String COLUMN_FIFTHPASSES = "savedFifthPass";
    public static final String COLUMN_USEDPASSES = "savedUsedPass";

    public static final String COLUMN_AUTOPASSES = "savedAutoPass";


    public DBhandeler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String KEY = "CREATE TABLE " + TABLE_KEY  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEY+ " TEXT "+ ");";
        String Addresses = "CREATE TABLE " + TABLE_FIRSTADDRESS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRSTADDRESSES+ " TEXT "+ ");";
        String SecondAddresses = "CREATE TABLE " + TABLE_SECONDADDRESS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SECONDADDRESSES+ " TEXT "+ ");";
        String ThirdAddresses = "CREATE TABLE " + TABLE_THIRDADDRESS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_THIRDADDRESSES+ " TEXT "+ ");";
        String ForthAddresses = "CREATE TABLE " + TABLE_FORTHADDRESS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FORTHADDRESSES+ " TEXT "+ ");";
        String FifthAddresses = "CREATE TABLE " + TABLE_FIFTHADDRESS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIFTHADDRESSES+ " TEXT "+ ");";
        String Passes = "CREATE TABLE " + TABLE_PASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PASSES+ " TEXT "+ ");";
        String Connection = "CREATE TABLE " + TABLE_CONNECTION  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONNECTIONS+ " TEXT "+ ");";
        String AutoPass = "CREATE TABLE " + TABLE_AUTOPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AUTOPASSES+ " TEXT "+ ");";
        String SecondPass = "CREATE TABLE " + TABLE_SECONDPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SECONDPASSES+ " TEXT "+ ");";
        String ThirdPass = "CREATE TABLE " + TABLE_THIRDDPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_THIRDPASSES+ " TEXT "+ ");";
        String ForthPass = "CREATE TABLE " + TABLE_FORTHDPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FORTHPASSES+ " TEXT "+ ");";
        String FifthPass = "CREATE TABLE " + TABLE_FIFTHDPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIFTHPASSES+ " TEXT "+ ");";
        String UsedPass = "CREATE TABLE " + TABLE_USEDPASS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USEDPASSES+ " TEXT "+ ");";
        String UsedAddress = "CREATE TABLE " + TABLE_USEDADDRESSES  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USEDADDRESES+ " TEXT "+ ");";
        String SavedName = "CREATE TABLE " + TABLE_NAME  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME+ " TEXT "+ ");";
        String KEYINDEX = "CREATE TABLE " + TABLE_INDEX  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INDEX+ " TEXT "+ ");";
        String UID = "CREATE TABLE " + TABLE_UID  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UID+ " TEXT "+ ");";
        db.execSQL(Addresses);
        db.execSQL(SecondAddresses);
        db.execSQL(ThirdAddresses);
        db.execSQL(ForthAddresses);
        db.execSQL(FifthAddresses);
        db.execSQL(UsedAddress);
        db.execSQL(SavedName);

        db.execSQL(KEY);
        db.execSQL(KEYINDEX);
        db.execSQL(UID);

        db.execSQL(Passes);
        db.execSQL(SecondPass);
        db.execSQL(ThirdPass);
        db.execSQL(ForthPass);
        db.execSQL(FifthPass);
        db.execSQL(UsedPass);

        db.execSQL(AutoPass);
        db.execSQL(Connection);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIRSTADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECONDADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THIRDADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORTHADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIFTHADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USEDADDRESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEY);


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECONDPASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THIRDDPASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORTHDPASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIFTHDPASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USEDPASS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTOPASS);

        onCreate(db);
    }
    public void addName(int names){
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, names);
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
            db.close();}catch (Exception e){}
    }
    public void addadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FIRSTADDRESS, null, values);
        db.close();
    }
    public void addUsedadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USEDADDRESES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USEDADDRESSES, null, values);
        db.close();
    }
    public void Addkey(String KEY){
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, KEY);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_KEY, null, values);
        db.close();
    }
    public void AddIndex(String Index){
        ContentValues values = new ContentValues();
        values.put(COLUMN_INDEX, Index);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INDEX, null, values);
        db.close();
    }
    public void AddUid(String Uid){
        ContentValues values = new ContentValues();
        values.put(COLUMN_UID, Uid);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_UID, null, values);
        db.close();
    }
    public void addThirdadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_THIRDADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_THIRDADDRESS, null, values);
        db.close();
    }
    public void addForthadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FORTHADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FORTHADDRESS, null, values);
        db.close();
    }
    public void addSecondadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SECONDADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SECONDADDRESS, null, values);
        db.close();
    }
    public void addFifthadresses(String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIFTHADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FIFTHADDRESS, null, values);
        db.close();
    }
    public void adjustconnection(String connection){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONNECTIONS, connection);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONNECTION, null, values);
        db.close();
    }
    public void UpdatAdresses(String _id, String addresses){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTADDRESSES, addresses);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_FIRSTADDRESS,  values,"_id=?", new String[]{_id});
        db.close();
    }
    public void addAutopass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTOPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_AUTOPASS, null, values);
        db.close();
    }
    public void addUsedpass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USEDPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USEDPASS, null, values);
        db.close();
    }
    public void addpass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PASS, null, values);
        db.close();
    }

    public void addSecondPass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SECONDPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SECONDPASS, null, values);
        db.close();
    }
    public void addThirdPass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_THIRDPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_THIRDDPASS, null, values);
        db.close();
    }
    public void addForthPass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FORTHPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FORTHDPASS, null, values);
        db.close();
    }
    public void addFifthPass(String passes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIFTHPASSES, passes);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FIFTHDPASS, null, values);
        db.close();
    }

    public void deletAddresses(String addr){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELET FROM " + TABLE_FIRSTADDRESS + " WHERE " + COLUMN_FIRSTADDRESSES + "=\"" + addr + "\";");
    }

    public String NameTostring(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        try {

            String addr = "SELECT * FROM " + TABLE_NAME + " WHERE 1";
            Cursor c = db.rawQuery(addr, null);
            c.moveToLast();

            if(c.getString(c.getColumnIndex("savedNAME"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedNAME")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String ConnectionTostring(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_CONNECTION + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("ConnectionTypes"))!=null){
                dbString.append(c.getString(c.getColumnIndex("ConnectionTypes")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }

    public String AutoPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_AUTOPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedAutoPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedAutoPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String UsedPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_USEDPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedUsedPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedUsedPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String keyToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_KEY + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("AdminKeyC"))!=null){
                dbString.append(c.getString(c.getColumnIndex("AdminKeyC")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String IndexToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_INDEX + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("INDEXC"))!=null){
                dbString.append(c.getString(c.getColumnIndex("INDEXC")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String UidToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_UID + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("UIDC"))!=null){
                dbString.append(c.getString(c.getColumnIndex("UIDC")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String SecondPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_SECONDPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedSecondPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedSecondPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String ThirdPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_THIRDDPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedThirdPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedThirdPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String ForthPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_FORTHDPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedForthPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedForthPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String FifthPasstoString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_FIFTHDPASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedFifthPass"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedFifthPass")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String UseddatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_USEDADDRESSES + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savedUsedAddress"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savedUsedAddress")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String databaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_FIRSTADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("savdeAddr"))!=null){
                dbString.append(c.getString(c.getColumnIndex("savdeAddr")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }

    public String SeconddatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_SECONDADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("SecondsavdeAddr"))!=null){
                dbString.append(c.getString(c.getColumnIndex("SecondsavdeAddr")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }


    public String ThirddatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_THIRDADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("ThirdsavdeAddr"))!=null){
                dbString.append(c.getString(c.getColumnIndex("ThirdsavdeAddr")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String ForthdatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_FORTHADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("ForthsavdeAddr"))!=null){
                dbString.append(c.getString(c.getColumnIndex("ForthsavdeAddr")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String FifthdatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_FIFTHADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();
        try {
            if(c.getString(c.getColumnIndex("FifthsavdeAddr"))!=null){
                dbString.append(c.getString(c.getColumnIndex("FifthsavdeAddr")));
                dbString.append("\n");
            }}catch (Exception E){}

        db.close();
        return dbString.toString();
    }
    public String databaseToStringPass(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String addr = "SELECT * FROM " + TABLE_PASS + " WHERE 1";
        Cursor c = db.rawQuery(addr, null);
        c.moveToLast();



        if(c.getString(c.getColumnIndex("savedPass"))!=null){
            dbString.append(c.getString(c.getColumnIndex("savedPass")));
            dbString.append("\n");

        }




        db.close();
        return dbString.toString();
    }
    public String yenidatabaseToString(){
        StringBuilder dbString = new StringBuilder();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FIRSTADDRESS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();


        if(c.getString(c.getColumnIndex("savdeAddr"))!=null){
            dbString.append(c.getString(c.getColumnIndex("savdeAddr")));
            dbString.append("\n");

        }




        db.close();
        return dbString.toString();
    }
}
