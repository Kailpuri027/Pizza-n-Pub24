package capstone.conestoga.pizzanpub24;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeliveryOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order);
    }

    public void makePizza(View v){
        Intent i = new Intent(this,MakePizzaActivity.class);
        startActivity(i);
    }

    public void signInn(View v){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
