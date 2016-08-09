package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button delivery;
    Button carryout;
    TextView user;

    private FirebaseAuth auth;
    private DatabaseReference mRootRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(auth.getCurrentUser()!=null) {
            navigationView.getMenu().getItem(0).setTitle("Profile");
            navigationView.getMenu().getItem(0).setIcon(R.drawable.ic_profile);
        }
        else{
            navigationView.getMenu().getItem(0).setTitle("Login");
            navigationView.getMenu().getItem(0).setIcon(R.drawable.ic_login);
        }

        delivery = (Button) findViewById(R.id.deliveryButton);
        carryout = (Button) findViewById(R.id.carryoutButton);
        user = (TextView) findViewById(R.id.textuser);

        if(auth.getCurrentUser() == null) {
            user.setText("Hello Guest!");
        } else {
            //get firebase database references
            mRootRef = FirebaseDatabase.getInstance().getReference();
            userRef = mRootRef.child("allUsers").child(auth.getCurrentUser().getUid());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(auth.getCurrentUser() == null) {
            menu.findItem(R.id.action_signout).setVisible(false);
            menu.findItem(R.id.action_cart).setVisible(false);
        } else {
            menu.findItem(R.id.action_signout).setVisible(true);
            menu.findItem(R.id.action_cart).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_signout) {
            signOut();
            return true;
        }
        else if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            signIn();
        } else if (id == R.id.nav_special) {
            Intent i = new Intent(this,PizzaActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_coupons) {

        } else if (id == R.id.nav_reservation) {
            Intent i = new Intent(this,TableReservationActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_menu) {
            openMenu();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void delivery(View v) {
        Intent i = new Intent(this,DeliveryOrder.class);
        startActivity(i);
    }

    public void signIn() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public void openMenu(){
        Intent i = new Intent(this,MenuActivity.class);
        startActivity(i);
    }

    public void signOut() {
        auth.signOut();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(userRef!=null){
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("user_fname").getValue(String.class);
                    user.setText("Hello "+ username +"!");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
