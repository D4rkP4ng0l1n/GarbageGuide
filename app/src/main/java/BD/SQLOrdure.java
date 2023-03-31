package BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOrdure extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ordure.db";
    public static final int DATABASE_VERSION = 1;

    // Table Ordure
    public static final String ORDURE_TABLE_NAME = "Ordure";
    public static final String ORDURE_COLUMN_ID = "id";
    public static final String ORDURE_COLUMN_NOM = "nom";
    public static final String ORDURE_COLUMN_POUBELLE = "poubelle";
    public static final String ORDURE_COLUMN_TYPE = "type";

    public SQLOrdure(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ORDURE_TABLE_NAME + "(" +
                ORDURE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ORDURE_COLUMN_NOM + " TEXT, " +
                ORDURE_COLUMN_POUBELLE + " TEXT, " +
                ORDURE_COLUMN_TYPE + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ORDURE_TABLE_NAME);
        onCreate(db);
    }
}
