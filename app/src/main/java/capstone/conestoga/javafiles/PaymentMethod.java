package capstone.conestoga.javafiles;

public class PaymentMethod {
    protected int payment_id;
    protected String payment_type;
    protected String total_price;

    public PaymentMethod(int payment_id, String payment_type, String total_price) {
        super();
        this.payment_id = payment_id;
        this.payment_type = payment_type;
        this.total_price = total_price;
    }

    public PaymentMethod(String payment_type, String total_price) {
        super();
        this.payment_type = payment_type;
        this.total_price = total_price;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
