package saman.online.shop.models;

import android.database.Cursor;

public class CardItem {
    private long id;
    private Product product;
    private Color color;
    private Size size;
    private int quantity;


    public static String kay_id = "id";
    public static String kay_product = "product_id";
    public static String kay_color = "color_id";
    public static String kay_size = "size_id";
    public static String kay_quantity = "qty";
    public static String kay_product_image = "product_image";
    public static String kay_product_name = "product_name";
    public static String kay_product_price = "product_price";
    public static String kay_color_name = "color_name";
    public static String kay_color_value = "color_value";
    public static String kay_size_title = "size_title";


    public CardItem() {
    }

    public CardItem(Cursor cursor) {
        this.setId(Integer.parseInt(cursor.getString(0)));
        this.setQuantity(Integer.parseInt(cursor.getString(4)));
        Product product = new Product();
        product.setId(Long.parseLong(cursor.getString(1)));
        product.setTitle(cursor.getString(8));
        product.setImage(cursor.getString(7));
        product.setPrice(Long.parseLong(cursor.getString(9)));
        this.setProduct(product);
        Size size = new Size();
        size.setTitle(cursor.getString(10));
        size.setId(Long.parseLong(cursor.getString(2)));
        this.setSize(size);
        Color color = new Color();
        color.setId(Long.parseLong(cursor.getString(3)));
        color.setName(cursor.getString(5));
        color.setValue(cursor.getString(6));
        this.setColor(color);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
