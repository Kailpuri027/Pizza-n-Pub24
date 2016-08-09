package capstone.conestoga.pizzanpub24;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import capstone.conestoga.javafiles.TableReservation;
import capstone.conestoga.javafiles.Users;

public class TableReservationActivity extends AppCompatActivity {

    private EditText name, email, phone, npersons;
    private Button btn_pdate, btn_ptime, btn_mreservation;
    private TextView sname, semail, sphn, snpersons, sdate, stime;

    public FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference mRootRef;
    private DatabaseReference userRef;

    private ProgressBar progressBar;

    private DatePickerDialog selectDate;
    private TimePickerDialog selectTime;
    private SimpleDateFormat dateFormatter, timeFormatter;

    private Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        name = (EditText) findViewById(R.id.personname);
        email = (EditText) findViewById(R.id.personemail);
        phone = (EditText) findViewById(R.id.personphn);
        npersons = (EditText) findViewById(R.id.numpersons);

        sname = (TextView) findViewById(R.id.showname);
        semail = (TextView) findViewById(R.id.showemail);
        sphn = (TextView) findViewById(R.id.showphnnumber);
        snpersons = (TextView) findViewById(R.id.shownumpersons);
        sdate = (TextView) findViewById(R.id.showdate);
        stime = (TextView)findViewById(R.id.showtime);

        btn_pdate = (Button) findViewById(R.id.btn_date);
        btn_ptime = (Button) findViewById(R.id.btn_time);
        btn_mreservation = (Button) findViewById(R.id.btn_makeReservation);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.CANADA);
        timeFormatter = new SimpleDateFormat("hh:mm", Locale.CANADA);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get firebase database references
        mRootRef = FirebaseDatabase.getInstance().getReference();

        if (auth.getCurrentUser() != null) {
            fab.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            //get current user
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userRef = mRootRef.child("allUsers").child(user.getUid());

            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user1 = firebaseAuth.getCurrentUser();
                    if (user1 == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(TableReservationActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };
        }
        else {
            fab.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TableReservationActivity.this, LoginActivity.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_pdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCalendar = Calendar.getInstance();

                selectDate = new DatePickerDialog(TableReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        sdate.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                selectDate.show();
            }
        });

        btn_ptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newCalendar = Calendar.getInstance();
                selectTime = new TimePickerDialog(TableReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newTime = Calendar.getInstance();
                        newTime.set(Calendar.HOUR,hourOfDay);
                        newTime.set(Calendar.MINUTE,minute);
                        stime.setText(timeFormatter.format(newTime.getTime()));
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

                selectTime.show();
            }
        });

        btn_mreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference usersRef;

                final String numprsns = npersons.getText().toString().trim();
                String userId;

                if (auth.getCurrentUser() == null) {

                    final String mname = name.getText().toString();
                    final String memail = email.getText().toString().trim();
                    final String mphn = phone.getText().toString().trim();

                    if (TextUtils.isEmpty(mname)) {
                        name.setError("Enter name!");
                        return;
                    }

                    if (TextUtils.isEmpty(memail)) {
                        email.setError("Enter email!");
                        return;
                    }

                    if (TextUtils.isEmpty(mphn)) {
                        phone.setError("Enter phone number!");
                        return;
                    }

                    if (mphn.length() < 10) {
                        phone.setError("Must have 10 digits!");
                        return;
                    }

                    if(sdate.getText().toString().trim().equals("")) {
                        Toast.makeText(TableReservationActivity.this,"Select date!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(stime.getText().toString().trim().equals("")) {
                        Toast.makeText(TableReservationActivity.this,"Select time!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    sname.setText(mname);
                    semail.setText(memail);
                    sphn.setText(mphn);

                    userId = UUID.randomUUID().toString();

                    usersRef = mRootRef.child("anonymousUsers");
                    usersRef.child(userId).setValue(new Users(mname, "", mphn, memail));
                }

                else {
                    userId = auth.getCurrentUser().getUid();
                }

                if (numprsns.equals("0") || numprsns.equals("00")) {
                    npersons.setError("Must have atleast 1 person!");
                    return;
                }

                if (TextUtils.isEmpty(numprsns)) {
                    npersons.setError("Enter number of persons!");
                    return;
                }

                snpersons.setText(numprsns);

                progressBar.setVisibility(View.VISIBLE);

                String reservationId = UUID.randomUUID().toString();

                DatabaseReference reservationRef = mRootRef.child("allTableReservations");
                reservationRef.child(reservationId).setValue(new TableReservation(userId,numprsns,sdate.getText().toString(),stime.getText().toString()));

                progressBar.setVisibility(View.GONE);

                Toast.makeText(TableReservationActivity.this,"Reservation done for " + numprsns + "!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        clear();

        if (auth.getCurrentUser() != null) {
            auth.addAuthStateListener(authListener);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("user_fname").getValue(String.class) + " "
                            + dataSnapshot.child("user_lname").getValue(String.class);
                    String email = dataSnapshot.child("user_email").getValue(String.class);
                    String phn = dataSnapshot.child("user_phone").getValue(String.class);

                    sname.setText(name);
                    semail.setText(email);
                    sphn.setText(phn);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    protected void clear() {
        name.setText("");
        sname.setText("");
        email.setText("");
        semail.setText("");
        phone.setText("");
        sphn.setText("");
        npersons.setText("");
        snpersons.setText("");
        sdate.setText("");
        stime.setText("");

        progressBar.setVisibility(View.GONE);
    }
}
