package capstone.conestoga.dbfiles;

public class VehicleDB {
    private static String KEY_ID = "Id";
    private static String KEY_REG_NO = "Reg_No";

    private static String[] ALL_KEYS = new String[] {KEY_ID, KEY_REG_NO};

    private static String TABLE_NAME = "Vehicles";

    public static String VEHICLE_CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_REG_NO + " TEXT NOT NULL "
            + " );";

    public static String VEHICLE_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
