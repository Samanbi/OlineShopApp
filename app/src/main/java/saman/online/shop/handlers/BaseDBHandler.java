package saman.online.shop.handlers;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDBHandler<T> extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "OnlineShopDB";

    public BaseDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateTableQuery());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDropTableQuery());
        onCreate(db);
    }

    public void addData(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(getTableName(), null, values);
        db.close();
    }

    public void checkAndCreateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    protected abstract String getTableName();

    protected abstract String getCreateTableQuery();

    protected abstract String getDropTableQuery();

}
