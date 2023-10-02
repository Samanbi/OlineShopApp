package saman.online.shop.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import saman.online.shop.models.CardItem;
import saman.online.shop.models.User;

public class UserDBHandler extends BaseDBHandler<CardItem> {
    public UserDBHandler(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return "USER ";
    }

    @Override
    protected String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() +
                "(" + User.kay_id + " INTEGER PRIMARY KEY," +
                User.kay_customerId + " INTEGER," +
                User.kay_username + " TEXT," +
                User.kay_firstname + " TEXT," +
                User.kay_lastname + " TEXT," +
                User.kay_token + " TEXT)";
    }

    @Override
    protected String getDropTableQuery() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    public User getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String allQuery = "SELECT * FROM " + getTableName()+ " order by " + User.kay_id + " desc ";
        Cursor cursor = db.rawQuery(allQuery, null);

        if (cursor != null) {
            cursor.moveToNext();
        }
        if (cursor.getCount() == 0)
            return null;

        return new User(cursor);
    }
    public void deleteAllUsers(){
        String query = "delete from " + getTableName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

}
