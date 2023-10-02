package saman.online.shop.models;

import java.io.Serializable;
import java.security.SecureRandom;

public class Feature implements Serializable {
    private String id;
    private String kay;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKay() {
        return kay;
    }

    public void setKay(String kay) {
        this.kay = kay;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
