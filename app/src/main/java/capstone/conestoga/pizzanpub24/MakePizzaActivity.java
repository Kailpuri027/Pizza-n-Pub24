package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MakePizzaActivity extends AppCompatActivity implements OnItemSelectedListener{

    String tops = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pizza);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Orignal Hand Tossed");
        categories.add("Hand Tossed Thin");
        categories.add("Crunchy Thin Crust");
        categories.add("Handmade Pan");
        categories.add("Gluten Free Crust");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // storing string resources into Array
        String[] toppings = getResources().getStringArray(R.array.toppings);

        // Binding resources Array to ListAdapter
        ListView myListView = (ListView)findViewById(R.id.toppings_list);
        ListAdapter myListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.topping_list_item, toppings);
        myListView.setAdapter(myListAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tops += parent.getItemAtPosition(position).toString() + ",";
                Toast.makeText(MakePizzaActivity.this, tops, Toast.LENGTH_LONG).show();
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void payment(View v){
        Intent i = new Intent(this, PaymentActivity.class);
        startActivity(i);
    }
}
