package capstone.conestoga.javafiles;

/**
 * Created by Kailpuri on 8/1/2016.
 */
public class TableReservation {
    protected String user_id;
    protected String num_persons;
    protected String date;
    protected String time;

    public TableReservation(String user_id, String num_persons, String date, String time) {
        this.user_id = user_id;
        this.num_persons = num_persons;
        this.date = date;
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNum_persons() {
        return num_persons;
    }

    public void setNum_persons(String num_persons) {
        this.num_persons = num_persons;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
