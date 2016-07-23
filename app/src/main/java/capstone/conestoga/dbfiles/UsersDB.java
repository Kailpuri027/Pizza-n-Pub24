package capstone.conestoga.dbfiles;

public class UsersDB {
    static String KEY_ID = "Id";
    static String KEY_FNAME = "FName";
    static String KEY_LNAME = "LName";
    static String KEY_ADDRESS_ID = "Ad_Id";
    static String KEY_PHONE = "Phone";
    static String KEY_EMAIL = "Email";
    static String KEY_PASSWORD = "Password";
    static String KEY_USER_TYPE = "User_Type";

    public static String[] ALL_KEYS = new String[] {KEY_ID, KEY_FNAME, KEY_LNAME,
            KEY_ADDRESS_ID, KEY_PHONE, KEY_EMAIL, KEY_PASSWORD, KEY_USER_TYPE};

    public static String TABLE_NAME = "Users";

    public static String USERS_CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_FNAME + " TEXT NOT NULL, "
            + KEY_LNAME + " TEXT NOT NULL, "
            + KEY_ADDRESS_ID + " INTEGER, "
            + KEY_PHONE + " TEXT, "
            + KEY_EMAIL + " TEXT NOT NULL, "
            + KEY_PASSWORD + " TEXT NOT NULL, "
            + KEY_USER_TYPE + " TEXT NOT NULL "
            + " );";

    public static String USERS_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
