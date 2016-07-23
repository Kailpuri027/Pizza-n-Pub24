package capstone.conestoga.dbfiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import capstone.conestoga.javafiles.Users;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pizza'n'Pub24";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersDB.USERS_CREATE_SQL);
        db.execSQL(AddressDB.ADDRESS_CREATE_SQL);
        db.execSQL(OrderDB.ORDER_CREATE_SQL);
        db.execSQL(PaymentDB.PAYMENT_CREATE_SQL);
        db.execSQL(VehicleDB.VEHICLE_CREATE_SQL);
    }

    public void onDestroy(SQLiteDatabase db) {
        db.execSQL(UsersDB.USERS_DROP_SQL );
        db.execSQL(AddressDB.ADDRESS_DROP_SQL );
        db.execSQL(OrderDB.ORDER_DROP_SQL);
        db.execSQL(PaymentDB.PAYMENT_DROP_SQL);
        db.execSQL(VehicleDB.VEHICLE_DROP_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w(TAG, "Upgrading application's database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data!");

        // Destroy old database:
        onDestroy(db);

        // Recreate new database:
        onCreate(db);
    }

    public Users onLogin(String email, String pswd) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UsersDB.TABLE_NAME, UsersDB.ALL_KEYS, UsersDB.KEY_EMAIL + "=? AND " + UsersDB.KEY_PASSWORD + "=?" ,
                new String[] { String.valueOf(email), String.valueOf(pswd) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7));
        db.close();
        cursor.close();
        return user;
    }

    public long createAccount(String fname, String lname, String phone, String email, String password, boolean uType) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(UsersDB.KEY_FNAME, fname);
        cValues.put(UsersDB.KEY_LNAME, lname);
        cValues.put(UsersDB.KEY_PHONE, phone);
        cValues.put(UsersDB.KEY_EMAIL, email);
        cValues.put(UsersDB.KEY_PASSWORD, password);
        if(uType)
            cValues.put(UsersDB.KEY_USER_TYPE, "Employee");
        else
            cValues.put(UsersDB.KEY_USER_TYPE, "Customer");

        return db.insert(UsersDB.TABLE_NAME, null, cValues);
    }

    public Users getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UsersDB.TABLE_NAME, UsersDB.ALL_KEYS, UsersDB.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7));
        db.close();
        cursor.close();

        return user;
    }
}
