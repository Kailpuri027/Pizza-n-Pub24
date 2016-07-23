package capstone.conestoga.dbfiles;

public class OrderDB {
    private static String KEY_ID = "Id";
    private static String KEY_EMP_ID =  "Emp_Id";
    private static String KEY_VEHICLE_ID = "Vehicle_Id";
    private static String KEY_CUS_ID = "Cus_Id";
    private static String KEY_TOTAL = "Total_Amount";

    private static String[] ALL_KEYS = new String[] {KEY_ID, KEY_EMP_ID, KEY_VEHICLE_ID,
            KEY_CUS_ID, KEY_TOTAL};

    private static String TABLE_NAME = "Orders";

    public static String ORDER_CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY ASC, "
            + KEY_EMP_ID + " TEXT, "
            + KEY_VEHICLE_ID + " TEXT, "
            + KEY_CUS_ID + " TEXT, "
            + KEY_TOTAL + " TEXT "
            + " );";

    public static String ORDER_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
