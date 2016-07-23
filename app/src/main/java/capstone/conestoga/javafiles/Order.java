package capstone.conestoga.javafiles;

public class Order {
    protected int order_id;
    protected int emp_id;
    protected int vehicle_id;
    protected int customer_id;
    protected int total_price;

    public Order(int order_id, int emp_id, int vehicle_id, int customer_id,
                 int total_price) {
        super();
        this.order_id = order_id;
        this.emp_id = emp_id;
        this.vehicle_id = vehicle_id;
        this.customer_id = customer_id;
        this.total_price = total_price;
    }
    public Order(int emp_id, int vehicle_id, int customer_id, int total_price) {
        super();
        this.emp_id = emp_id;
        this.vehicle_id = vehicle_id;
        this.customer_id = customer_id;
        this.total_price = total_price;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getEmp_id() {
        return emp_id;
    }
    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }
    public int getVehicle_id() {
        return vehicle_id;
    }
    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public int getTotal_price() {
        return total_price;
    }
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
