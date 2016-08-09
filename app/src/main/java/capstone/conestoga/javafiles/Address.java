package capstone.conestoga.javafiles;

public class Address {
    protected String user_id;
    protected String place;
    protected String zip_code;

    public Address(){

    }

    public Address(String user_id, String place, String zip_code) {
        super();
        this.user_id = user_id;
        this.place = place;
        this.zip_code = zip_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
