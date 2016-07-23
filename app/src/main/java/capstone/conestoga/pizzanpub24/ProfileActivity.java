package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import capstone.conestoga.dbfiles.DatabaseHandler;
import capstone.conestoga.javafiles.Users;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra("User id", 0);
        DatabaseHandler db = new DatabaseHandler(this);
        Users u = db.getUser(id);

        TextView name = (TextView) findViewById(R.id.username);
        TextView e_mail = (TextView) findViewById(R.id.emailid);
        TextView tel = (TextView) findViewById(R.id.phnno);

        name.setText(u.getUser_fname() + " " + u.getUser_lname());
        e_mail.setText(u.getUser_email().toString());
        tel.setText(u.getUser_phone().toString());

    }

    public void signOut(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
