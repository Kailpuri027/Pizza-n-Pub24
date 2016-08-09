package capstone.conestoga.pizzanpub24;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import capstone.conestoga.javafiles.Users;

public class CreateProfile extends AppCompatActivity {

    private EditText inputFname, inputLname, inputPhone, inputEmail, inputPassword, inputCode;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef;
    private AlertDialog.Builder builder;

    private String fname, lname, email, password, phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //Get Firebase Database reference
        mRootRef = FirebaseDatabase.getInstance().getReference();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);

        inputFname = (EditText) findViewById(R.id.cUserfname);
        inputLname = (EditText) findViewById(R.id.cUserlname);
        inputPhone = (EditText) findViewById(R.id.cUserphone);
        inputEmail = (EditText) findViewById(R.id.email2);
        inputPassword = (EditText) findViewById(R.id.pswd);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = inputFname.getText().toString().trim();
                lname = inputLname.getText().toString().trim();
                phn = inputPhone.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                final String vcode;

                if (TextUtils.isEmpty(fname)) {
                    inputFname.setError("Enter firstname!");
                    return;
                }

                if (TextUtils.isEmpty(lname)) {
                    inputLname.setError("Enter lastname!");
                    return;
                }

                if (TextUtils.isEmpty(phn)) {
                    inputPhone.setError("Enter phone number!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter email address!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter password!");
                    return;
                }

                if (password.length() < 6) {
                    inputPassword.setError("Password too short, enter minimum 6 characters!");
                    return;
                }

                if (phn.length() < 10) {
                    inputPhone.setError("Phone number must have 10 digits!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                createUser();
                /*vcode = generateCode();
                sendCode(vcode);

                builder = new AlertDialog.Builder(CreateProfile.this);
                inputCode = new EditText(CreateProfile.this);

                builder.setTitle("Code Verification");
                builder.setMessage("Please enter your verification code.");
                builder.setView(inputCode);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (vcode.equals(inputCode.getText().toString())) {
                            createUser();
                        } else {
                            Toast.makeText(CreateProfile.this, "Code not matching!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        progressBar.setVisibility(View.GONE);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected String generateCode() {
        String pin;
        Random r = new Random( System.currentTimeMillis() );
        pin = "" + ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        return pin;
    }

    protected void sendCode(String code) {
        try{
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, inputEmail.getText().toString());
            i.putExtra(Intent.EXTRA_SUBJECT, "Verify your email for Pizza 'n' Pub 24");
            i.putExtra(Intent.EXTRA_TEXT,"Hello " + inputFname.getText().toString() + ",\n" +
                    "Your verification code is "+ code +".\n" +
                    "If you didn’t ask to verify this address, you can ignore this email.\n" +
                    "Thanks,\n" +
                    "Your Pizza 'n' Pub 24 team");
            i.setType("message/rfc822");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void createUser() {
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateProfile.this, "Something went wrong!" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateProfile.this, "New user added!", Toast.LENGTH_SHORT).show();

                            DatabaseReference usersRef = mRootRef.child("allUsers");
                            usersRef.child(auth.getCurrentUser().getUid()).setValue(new Users(fname,lname,phn,email));

                            startActivity(new Intent(CreateProfile.this, ProfileActivity.class));
                            finish();
                        }
                    }
                });
    }
}
