package capstone.conestoga.pizzanpub24;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import capstone.conestoga.javafiles.Address;
import capstone.conestoga.javafiles.Users;

public class DeliveryOrder extends AppCompatActivity {

    private EditText inaddress, inzip;
    private Button btnselectpizza, btnselectaddress;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef, addressRef;
    private TextView viewAddress;

    String address, zip;
    ArrayList<String> addressArray = new ArrayList<String>();
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DeliveryOrder.this, LoginActivity.class));
                    finish();
                }
            }
        };

        auth = FirebaseAuth.getInstance();
        //Get Firebase Database reference
        mRootRef = FirebaseDatabase.getInstance().getReference();
        addressRef = mRootRef.child("allAddresses");

        inaddress = (EditText) findViewById(R.id.daddress);
        inzip = (EditText) findViewById(R.id.dzip);

        viewAddress = (TextView) findViewById(R.id.viewAddress);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnselectpizza = (Button) findViewById(R.id.btn_select_pizza);
        btnselectaddress = (Button) findViewById(R.id.btn_select_address);

        btnselectpizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = inaddress.getText().toString();
                zip = inzip.getText().toString().trim();

                if(viewAddress.getText().toString().equals("")) {
                    if (TextUtils.isEmpty(address)) {
                        inaddress.setError("Enter address!");
                        return;
                    }

                    if (TextUtils.isEmpty(zip)) {
                        inzip.setError("Enter zip code!");
                        return;
                    }

                    if (zip.length() < 6) {
                        inzip.setError("Postal code must have 6 digits!");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    String addressId = UUID.randomUUID().toString();

                    DatabaseReference addressRef = mRootRef.child("allAddresses");
                    addressRef.child(addressId).setValue(new Address(auth.getCurrentUser().getUid(), address, zip));
                }

                Intent i = new Intent(DeliveryOrder.this,PizzaActivity.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        });

        btnselectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress();
            }
        });
    }

    private void selectAddress() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeliveryOrder.this);

        final String[] addresses = addressArray.toArray(new String[addressArray.size()]);
        dialogBuilder.setTitle("Address");
        dialogBuilder.setSingleChoiceItems(addresses, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                address = addresses[which];
                viewAddress.setText(address);
                dialog.cancel();
            }
        });

        AlertDialog dialogSize = dialogBuilder.create();
        dialogSize.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authListener);

        if(auth.getCurrentUser()!=null) {
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
    }
}
