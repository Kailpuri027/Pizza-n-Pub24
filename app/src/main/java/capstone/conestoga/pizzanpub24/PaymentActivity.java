package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    public void makePayment(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();

    }
}
