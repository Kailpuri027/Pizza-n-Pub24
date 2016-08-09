package capstone.conestoga.javafiles;

public class Users {
    private String user_fname;
    private String user_lname;
    private String user_phone;
    private String user_email;

    public Users(String user_fname, String user_lname, String user_phone, String user_email) {
        super();
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_phone = user_phone;
        this.user_email = user_email;
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
}
