package capstone.conestoga.dbfiles;

public class AddressDB {
    private static String KEY_ID = "Id";
    private static String KEY_PLACE = "Place";
    private static String KEY_ZIP = "Zip";

    private static String[] ALL_KEYS = new String[] {KEY_ID, KEY_PLACE, KEY_ZIP};

    private static String TABLE_NAME = "Address";

    public static String ADDRESS_CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_PLACE + " TEXT NOT NULL, "
            + KEY_ZIP + " TEXT NOT NULL "
            + " );";

    public static String ADDRESS_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
