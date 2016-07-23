package capstone.conestoga.javafiles;

public class Users {
    private int user_id;
    private String user_fname;
    private String user_lname;
    private String user_address_id;
    private String user_phone;
    private String user_email;
    private String user_password;
    private String user_type;

    public Users(int user_id, String user_fname, String user_lname,
                 String user_address_id, String user_phone,
                 String user_email, String user_password, String user_type) {
        super();
        this.user_id = user_id;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_address_id = user_address_id;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_type = user_type;
    }

    public Users(String user_fname, String user_lname, String user_address_id,
                    String user_phone, String user_email, String user_password, String user_type) {
        super();
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_address_id = user_address_id;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
