package capstone.conestoga.javafiles;

/**
 * Created by Kailpuri on 8/5/2016.
 */
public class CartItem {
    private String userId;
    private String itemId;
    private String item;
    private double price;
    private boolean status;

    public CartItem() {

    }

    public CartItem(String userId, String itemId, String item, double price, boolean status) {
        this.userId = userId;
        this.itemId = itemId;
        this.item = item;
        this.price = price;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
