package saman.online.shop.models;

import java.io.Serializable;
import java.util.List;


public class OrderItemProduct implements Serializable {
    private long id;
    private String title;
    private String description;
    private String image;
    private long price;
    private boolean enable;
    private boolean exists;
    private long categoryId;
    private long visitCount;
    private List<Color> colors;
    private List<Size> sizes;
    private List<Feature> features;
    private String addDateStr;

    public OrderItemProduct() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }

    public String getAddDateStr() {
        return addDateStr;
    }

    public void setAddDateStr(String addDateStr) {
        this.addDateStr = addDateStr;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Product convert(){
        Product product = new Product();
        product.setPrice(getPrice());
        product.setImage(getImage());
        product.setTitle(getTitle());
        product.setId(getId());
        product.setAddDateStr(getAddDateStr());
        product.setCategoryId(getCategoryId());
        product.setDescription(getDescription());
        product.setEnable(isEnable());
        product.setExists(isExists());
        product.setVisitCount(getVisitCount());
        return product;
    }
}
