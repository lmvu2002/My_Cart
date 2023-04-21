package edu.hanu.mycart.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import edu.hanu.mycart.models.CartItem;

public class CartRepository {
    public List<CartItem> cartItems;
    public static CartRepository getInstance(Context context) {
        if (instance == null){
            instance = new CartRepository(context);
        }
        return instance;
    }
    private static CartRepository instance;
    private DbHelper dbHelper;
    private Context context;
    SQLiteDatabase db;
    private CartRepository(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
    }



    public List<CartItem> all() {
        // load from db
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();
        String sql = " SELECT * FROM carts";
        Cursor cursor = db.rawQuery(sql, null);
        CartCursorWrapper CartCursorWrapper = new CartCursorWrapper(cursor);
        // get all Carts in db
        cartItems = CartCursorWrapper.getCarts();
        cursor.close();
        db.close();
        return cartItems;
    }

    public int getQuantity(long productId) {

        Cursor cursor = getData(productId);
        int quantity = cursor.getColumnIndex("quantity");
        return cursor.getInt(quantity);
    }

    private Cursor getData(long id) {
        SQLiteDatabase db = new DbHelper(this.context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM carts WHERE product_id=?", new String[]{String.valueOf(id)});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  void editCartQuantity(Long productId, int quantity) {
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("carts", values, "product_id = ?", new String[] {String.valueOf(productId)});
    }

    public void addCart(Long productId, String productName, String productCategory, int productPrice, String productThumbnail, int quantity) {
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("INSERT INTO carts (product_id, product_name, product_category, product_price, product_thumbnail, quantity) VALUES (?, ?, ?, ?, ?, ?)");
        statement.bindLong(1, productId);
        statement.bindString(2, productName);
        statement.bindString(3, productCategory);
        statement.bindLong(4, productPrice);
        statement.bindString(5, productThumbnail);
        statement.bindLong(6, quantity);
        statement.executeInsert();
        db.close();
    }

    public void deleteItem(Long cartId) {
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("DELETE FROM carts where cart_id = ?");
        statement.bindLong(1, cartId);
        statement.executeUpdateDelete();
        db.close();
    }
}