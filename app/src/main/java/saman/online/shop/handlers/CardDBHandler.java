package saman.online.shop.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import saman.online.shop.fragments.BasketFragment;
import saman.online.shop.models.CardItem;

public class CardDBHandler extends BaseDBHandler<CardItem> {
    public CardDBHandler(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return "CARDS ";
    }

    @Override
    protected String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() +
                "(" + CardItem.kay_id + " INTEGER PRIMARY KEY," +
                CardItem.kay_product + " INTEGER," + CardItem.kay_size + " INTEGER," +
                CardItem.kay_color + " INTEGER," + CardItem.kay_quantity + " INTEGER," +
                CardItem.kay_color_name + " TEXT," + CardItem.kay_color_value + " TEXT," +
                CardItem.kay_product_image + " TEXT," + CardItem.kay_product_name + " TEXT," +
                CardItem.kay_product_price + " INTEGER," + CardItem.kay_size_title + " TEXT)";
    }

    @Override
    protected String getDropTableQuery() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    public CardItem getDataByProductId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(getTableName(), new String[]{
                CardItem.kay_id, CardItem.kay_product, CardItem.kay_size,
                CardItem.kay_color, CardItem.kay_quantity,
                CardItem.kay_color_name, CardItem.kay_color_value,
                CardItem.kay_product_image, CardItem.kay_product_name,
                CardItem.kay_product_price, CardItem.kay_size_title
        }, CardItem.kay_product + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToNext();
        }
        return new CardItem(cursor);
    }


    public CardItem getDataByDetail(long productId, long sizeId, long colorId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(getTableName(), new String[]{
                        CardItem.kay_id, CardItem.kay_product, CardItem.kay_size,
                        CardItem.kay_color, CardItem.kay_quantity,
                        CardItem.kay_color_name, CardItem.kay_color_value,
                        CardItem.kay_product_image, CardItem.kay_product_name,
                        CardItem.kay_product_price, CardItem.kay_size_title
                    }, CardItem.kay_product + "=? AND " + CardItem.kay_size + "=? AND " + CardItem.kay_color + "=?",
                    new String[]{String.valueOf(productId), String.valueOf(sizeId), String.valueOf(colorId)}, null, null, null);

        if (cursor != null) {
            cursor.moveToNext();
        }
        if (cursor.getCount() == 0)
            return null;

        return new CardItem(cursor);
    }

    private int updateData(CardItem data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CardItem.kay_quantity, data.getQuantity());
        return db.update(getTableName(), values, CardItem.kay_id + "=? ", new String[]{String.valueOf(data.getId())});
    }

    public CardItem addToBasket(CardItem data) {
        CardItem oldData = getDataByDetail(data.getProduct().getId(), data.getSize().getId(), data.getColor().getId());

        if (oldData == null) {
            ContentValues values = new ContentValues();
            values.put(CardItem.kay_product, data.getProduct().getId());
            values.put(CardItem.kay_size, data.getSize().getId());
            values.put(CardItem.kay_color, data.getColor().getId());
            values.put(CardItem.kay_quantity, data.getQuantity());
            values.put(CardItem.kay_color_name, data.getColor().getName());
            values.put(CardItem.kay_color_value, data.getColor().getValue());
            values.put(CardItem.kay_size_title, data.getSize().getTitle());
            values.put(CardItem.kay_product_image, data.getProduct().getImage());
            values.put(CardItem.kay_product_name, data.getProduct().getTitle());
            values.put(CardItem.kay_product_price, data.getProduct().getPrice());
            addData(values);
            return data;
        }

        oldData.setQuantity(oldData.getQuantity() + 1);

        updateData(oldData);
        return oldData;

    }

    public int getAllBasketDataCount() {
        String countQuery = "SELECT * FROM " + getTableName();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<CardItem> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String allQuery = "SELECT * FROM " + getTableName();
        Cursor cursor = db.rawQuery(allQuery, null);

        if (cursor != null) {
            cursor.moveToNext();
        }
        if (cursor.getCount() == 0)
            return new ArrayList<>();

        List<CardItem> result = new ArrayList<>();

        do {
            result.add(new CardItem(cursor));
        } while (cursor.moveToNext());
        return result;
    }
    public void deleteAllBasket(){
        String query = "delete from " + getTableName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

}
