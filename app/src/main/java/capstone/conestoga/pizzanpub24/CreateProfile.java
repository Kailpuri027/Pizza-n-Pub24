package capstone.conestoga.pizzanpub24;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import capstone.conestoga.dbfiles.DatabaseHandler;

public class CreateProfile extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText phn;
    EditText e_id;
    EditText passwd;

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        phn = (EditText) findViewById(R.id.phone);
        e_id = (EditText) findViewById(R.id.email2);
        passwd = (EditText) findViewById(R.id.pswd);

        aSwitch = (Switch) findViewById(R.id.switch1);
    }

    public void createProfile(View v){
        String fn = fname.getText().toString();
        String ln = lname.getText().toString();
        String phone = phn.getText().toString();
        String eid = e_id.getText().toString();
        String pd = passwd.getText().toString();

        boolean b = aSwitch.isChecked();

        try{
            DatabaseHandler db = new DatabaseHandler(this);
            Long id = db.createAccount(fn, ln, phone, eid, pd, b);
            Toast.makeText(this,"ID = "+id, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this,"Please try again!", Toast.LENGTH_LONG).show();
        }
    }
}
