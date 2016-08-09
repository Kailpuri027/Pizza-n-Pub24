package capstone.conestoga.pizzanpub24;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import capstone.conestoga.javafiles.Address;
import capstone.conestoga.pizzanpub24.R;

public class ProfileActivity extends AppCompatActivity {

    Button mAddress, mSignout, mRemoveUser;
    TextView mUname, mEmail, mPhone;

    private ProgressBar progressBar;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef, userRef, addressRef;

    ArrayList<String> addressArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //get firebase database references
        mRootRef = FirebaseDatabase.getInstance().getReference();
        userRef = mRootRef.child("allUsers").child(user.getUid());
        addressRef = mRootRef.child("allAddresses");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        mAddress = (Button) findViewById(R.id.btn_address);
        mSignout = (Button) findViewById(R.id.btn_signout);
        mRemoveUser =(Button) findViewById(R.id.btn_delete);

        mUname = (TextView) findViewById(R.id.pUsername);
        mEmail = (TextView) findViewById(R.id.pEmail);
        mPhone = (TextView) findViewById(R.id.pPhone);

        progressBar = (ProgressBar) findViewById(R.id.pProgressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        mRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfileActivity.this, CreateProfile.class));
                            finish();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress();
            }
        });
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("user_fname").getValue(String.class) + " "
                        + dataSnapshot.child("user_lname").getValue(String.class);
                String email = dataSnapshot.child("user_email").getValue(String.class);
                String phn = dataSnapshot.child("user_phone").getValue(String.class);

                mUname.setText(username);
                mEmail.setText(email);
                mPhone.setText(phn);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addressRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot addressShot : dataSnapshot.getChildren()) {
                    if (addressShot.child("user_id").getValue().equals(auth.getCurrentUser().getUid())) {
                        String location;
                        Address address = addressShot.getValue(Address.class);
                        location = address.getPlace() + " " + address.getZip_code();
                        addressArray.add(location);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void selectAddress() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);

        final String[] addresses = addressArray.toArray(new String[addressArray.size()]);
        dialogBuilder.setTitle("Address");
        dialogBuilder.setSingleChoiceItems(addresses, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialogSize = dialogBuilder.create();
        dialogSize.show();
    }
}
