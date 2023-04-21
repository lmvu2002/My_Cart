package edu.hanu.mycart.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;
import edu.hanu.mycart.models.CartItem;
import edu.hanu.mycart.models.Product;

public class CartCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CartCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CartItem getCart() {
        Cursor cursor = getWrappedCursor();
        int cartId = cursor.getColumnIndex("cart_id");
        int productId = cursor.getColumnIndex("product_id");
        int productName = cursor.getColumnIndex("product_name");
        int productCategory = cursor.getColumnIndex("product_category");
        int productPrice = cursor.getColumnIndex("product_price");
        int productThumbnail = cursor.getColumnIndex("product_thumbnail");
        int quantity = cursor.getColumnIndex("quantity");
        cursor.moveToNext();
        long cartIdVal = cursor.getInt(cartId);
        Long productIdVal = cursor.getLong(productId);
        String productNameVal = cursor.getString(productName);
        String productCategoryVal = cursor.getString(productCategory);
        int productPriceVal = cursor.getInt(productPrice);
        String productThumbnailVal = cursor.getString(productThumbnail);
        int quantityVal = cursor.getInt(quantity);
        Product product = new Product(productIdVal, productNameVal, productCategoryVal, productPriceVal, productThumbnailVal);
        return new CartItem(cartIdVal, product, quantityVal);
    }

    public List<CartItem> getCarts() {
        List<CartItem> Carts = new ArrayList<>();
        Cursor cursor = getWrappedCursor();
        int cartId = cursor.getColumnIndex("cart_id");
        int productId = cursor.getColumnIndex("product_id");
        int productName = cursor.getColumnIndex("product_name");
        int productCategory = cursor.getColumnIndex("product_category");
        int productPrice = cursor.getColumnIndex("product_price");
        int productThumbnail = cursor.getColumnIndex("product_thumbnail");
        int quantity = cursor.getColumnIndex("quantity");

        while(cursor.moveToNext()) {
            long cartIdVal = cursor.getInt(cartId);
            Long productIdVal = cursor.getLong(productId);
            String productNameVal = cursor.getString(productName);
            String productCategoryVal = cursor.getString(productCategory);
            int productPriceVal = cursor.getInt(productPrice);
            String productThumbnailVal = cursor.getString(productThumbnail);
            int quantityVal = cursor.getInt(quantity);
            Product product = new Product(productIdVal, productNameVal, productCategoryVal, productPriceVal, productThumbnailVal);
            CartItem cart = new CartItem(cartIdVal, product, quantityVal);
            Carts.add(cart);
        }
        return Carts;
    }
}
