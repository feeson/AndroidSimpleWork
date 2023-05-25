package cloud.dicsfeesono.androidsimplework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        System.out.println(dbPath);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据表的SQL语句
        String createTableQuery = "CREATE TABLE setting (id INTEGER PRIMARY KEY, setting TEXT)";

        // 执行创建表的SQL语句
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
