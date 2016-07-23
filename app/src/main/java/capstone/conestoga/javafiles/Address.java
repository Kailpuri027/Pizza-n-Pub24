package capstone.conestoga.javafiles;

public class Address {
    protected int address_id;
    protected String place;
    protected String zip_code;

    public Address(int address_id, String place, String zip_code) {
        super();
        this.address_id = address_id;
        this.place = place;
        this.zip_code = zip_code;
    }

    public Address(String place, String zip_code) {
        super();
        this.place = place;
        this.zip_code = zip_code;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
