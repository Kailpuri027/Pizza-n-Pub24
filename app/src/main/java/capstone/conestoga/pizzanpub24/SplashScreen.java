package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread mythread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                }
                catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally {
                    Intent mainPage = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainPage);
                    finish();
                }
            }
        };
        mythread.start();
    }
}
