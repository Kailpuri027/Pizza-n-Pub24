package capstone.conestoga.dbfiles;

public class PaymentDB {
    private static String KEY_ID = "Id";
    private static String KEY_TYPE = "Type";
    private static String KEY_PRICE = "Total";

    private static String[] ALL_KEYS = new String[] {KEY_ID, KEY_TYPE, KEY_PRICE};

    private static String TABLE_NAME = "PaymentType";

    public static String PAYMENT_CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_TYPE + " TEXT NOT NULL, "
            + KEY_PRICE + " TEXT NOT NULL "
            + " );";

    public static String PAYMENT_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
