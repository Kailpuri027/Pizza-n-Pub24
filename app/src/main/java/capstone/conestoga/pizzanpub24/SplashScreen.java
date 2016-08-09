package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import capstone.conestoga.javafiles.ConnectivityReceiver;
import capstone.conestoga.javafiles.PizzaNPubApp;

public class SplashScreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    protected TextView netStatus;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        netStatus = (TextView) findViewById(R.id.internetStatus);

        // Manually checking internet connection
        checkConnection();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showStatus(isConnected);
    }

    // Showing the status in Snackbar
    private void showStatus(boolean isConnected) {
        String message="";
        int color=0;
        if (isConnected) {
            Intent mainPage = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mainPage);
            finish();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        netStatus.setText(message);
        netStatus.setTextColor(color);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        PizzaNPubApp.getInstance().setConnectivityListener(SplashScreen.this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showStatus(isConnected);
    }
}
