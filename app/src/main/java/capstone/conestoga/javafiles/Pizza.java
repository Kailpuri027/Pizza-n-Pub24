package capstone.conestoga.javafiles;

/**
 * Created by Kailpuri on 8/5/2016.
 */
public class Pizza {

    protected String crust;
    protected String size;
    protected String toppings;
    protected String description;

    public Pizza() {

    }

    public Pizza(String crust, String size, String toppings, String description) {
        this.crust = crust;
        this.size = size;
        this.toppings = toppings;
        this.description = description;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
