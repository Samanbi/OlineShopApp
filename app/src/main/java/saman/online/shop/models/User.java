package saman.online.shop.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

import saman.online.shop.enums.UserRole;

public class User implements Serializable {

    public static String kay_id = "id";
    public static String kay_firstname = "first_name";
    public static String kay_lastname = "last_name";
    public static String kay_username = "username";
    public static String kay_token = "token";
    public static String kay_customerId = "customerId";

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String newPassword;
    private String email;
    private UserRole role;
    private boolean enable;
    private String token;
    private long customerId;
    private Customer customer;

    public User() {
    }

    public User(Cursor cursor) {
        setId(cursor.getLong(0));
        setCustomerId(cursor.getLong(1));
        setUsername(cursor.getString(2));
        setFirstName(cursor.getString(3));
        setLastName(cursor.getString(4));
        setToken(cursor.getString(5));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(User.kay_id, getId());
        values.put(User.kay_customerId, getCustomerId());
        values.put(User.kay_firstname, getFirstName());
        values.put(User.kay_lastname, getLastName());
        values.put(User.kay_username, getUsername());
        values.put(User.kay_token, getToken());
        return values;
    }
}
