package capstone.conestoga.pizzanpub24;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import capstone.conestoga.dbfiles.DatabaseHandler;
import capstone.conestoga.javafiles.Users;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    EditText emailID;
    EditText pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailID = (EditText) findViewById(R.id.email1);
        pswd = (EditText) findViewById(R.id.password);
    }

    public void login(View v) {
        String _id = emailID.getText().toString();
        String _pswd = pswd.getText().toString();

        try {
            DatabaseHandler db = new DatabaseHandler(this);
            Users u = db.onLogin(_id,_pswd);

            if(u.getUser_email().equals(_id)) {
                if(u.getUser_password().equals(_pswd)) {
                    int rowId = u.getUser_id();
                    Intent i = new Intent(this,ProfileActivity.class);
                    i.putExtra("User id", rowId);
                    startActivity(i);
                }
                else {
                    Toast t = Toast.makeText(this, "Incorrect id or password", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
            else {
                Toast t = Toast.makeText(this, "Incorrect id or password", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        } catch(Exception e){
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
    }

    public void createProfileActivity (View v) {
        Intent i = new Intent(this,CreateProfile.class);
        startActivity(i);
    }
}

