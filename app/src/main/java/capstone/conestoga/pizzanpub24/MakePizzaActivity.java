package capstone.conestoga.pizzanpub24;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import capstone.conestoga.javafiles.Address;
import capstone.conestoga.javafiles.CartItem;
import capstone.conestoga.javafiles.Pizza;

public class MakePizzaActivity extends AppCompatActivity{

    protected Button btn_size, btn_crust, btn_toppings, btn_makePizza;
    protected AlertDialog.Builder dialogBuilder;
    protected TextView err;
    protected String crust = "", size = "", tops = "", additional = "";
    protected double price = 0;
    protected ArrayList toppings;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef, pizzaRef, itemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pizza);

        auth = FirebaseAuth.getInstance();
        //Get Firebase Database reference
        mRootRef = FirebaseDatabase.getInstance().getReference();
        pizzaRef = mRootRef.child("allPizza");
        itemRef = mRootRef.child("allItems");

        btn_crust = (Button) findViewById(R.id.btn_addCrust);
        btn_size = (Button) findViewById(R.id.btn_selectSize);
        btn_toppings = (Button) findViewById(R.id.btn_addToppings);
        btn_makePizza = (Button) findViewById(R.id.btn_makePizza);

        err = (TextView) findViewById(R.id.error);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MakePizzaActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btn_crust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                err.setVisibility(View.GONE);
                selectCrust();
            }
        });

        btn_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                err.setVisibility(View.GONE);
                selectSize();
            }
        });

        btn_toppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                err.setVisibility(View.GONE);
                selectToppings();
            }
        });

        btn_makePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crust.equals("")) {
                    err.setVisibility(View.VISIBLE);
                    err.setText("Select Crust!");
                    return;
                }
                if (size.equals("")) {
                    err.setVisibility(View.VISIBLE);
                    err.setText("Select Size");
                    return;
                }
                if (tops.equals("")) {
                    err.setVisibility(View.VISIBLE);
                    err.setText("Select Toppings");
                    return;
                }
                err.setVisibility(View.GONE);

                price = calculatePrice(crust, size, toppings.size());

                String pizzaId = UUID.randomUUID().toString();
                pizzaRef.child(pizzaId).setValue(new Pizza(crust, size, tops, additional));

                String itemId = UUID.randomUUID().toString();
                itemRef.child(itemId).setValue(new CartItem(auth.getCurrentUser().getUid(), pizzaId,"Pizza",price,false));

                Intent i = new Intent(MakePizzaActivity.this, CartActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void selectCrust() {
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] crustArray = {"Orignal Hand Tossed", "Hand Tossed Thin", "Crunchy Thin Crust", "Handmade Pan", "Gluten Free Crust"};

        dialogBuilder.setTitle("Crusts");
        dialogBuilder.setSingleChoiceItems(crustArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crust = crustArray[which];
                Toast.makeText(MakePizzaActivity.this, crust, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        AlertDialog dialogCrust = dialogBuilder.create();
        dialogCrust.show();
    }

    private void selectSize() {
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] sizeArray = {"Small (10\" - 6 Slices)", "Medium (12\" - 8 Slices)","Large (14\" - 10 Slices)", "ExtraLarge (16\" - 12 Slices)"};

        dialogBuilder.setTitle("Sizes");
        dialogBuilder.setSingleChoiceItems(sizeArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                size = sizeArray[which];
                Toast.makeText(MakePizzaActivity.this, size, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        AlertDialog dialogSize = dialogBuilder.create();
        dialogSize.show();
    }

    private void selectToppings() {
        toppings = new ArrayList();
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] toppingsArray = {"Pepperoni", "Sausage", "Beef", "Ham", "Bacon", "Chicken",
                "Cheese", "Hot Banana Peppers", "Black Olives", "Green Olives", "Green Pepper",
                "Mushroom", "Pineapple", "Onion", "Tomatoes"};

        dialogBuilder.setTitle("Toppings");
        dialogBuilder.setMultiChoiceItems(toppingsArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    toppings.add(which);
                } else if (toppings.contains(which)) {
                    toppings.remove(Integer.valueOf(which));
                }
            }
        });
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < toppings.size(); i++) {
                    tops += toppingsArray[(Integer) toppings.get(i)] + ", ";
                }
                Toast.makeText(MakePizzaActivity.this, tops, Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialogTops = dialogBuilder.create();
        dialogTops.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    private double calculatePrice(String crust, String size, int tops) {
        int crustPrice = 0;
        int sizePrice = 0;
        double topsPrice = 0;
        double total = 0;

        switch(crust){
            case "Orignal Hand Tossed":
                crustPrice = 2;
                break;
            case "Hand Tossed Thin":
                crustPrice = 3;
                break;
            case "Crunchy Thin Crust":
                crustPrice = 3;
                break;
            case "Handmade Pan":
                crustPrice = 4;
                break;
            case "Gluten Free Crust":
                crustPrice = 4;
                break;
        }

        switch(size) {
            case "Small (10\" - 6 Slices)":
                sizePrice = 4;
                break;
            case "Medium (12\" - 8 Slices)":
                sizePrice = 5;
                break;
            case "Large (14\" - 10 Slices)":
                sizePrice = 6;
                break;
            case "ExtraLarge (16\" - 12 Slices)":
                sizePrice = 7;
                break;
        }

        if(tops < 1)
            topsPrice = 0;
        else
            topsPrice = tops - 1;

        Log.i(crust,"$"+crustPrice);
        Log.i(size,"$"+sizePrice);
        Log.i(""+tops,"$"+topsPrice);

        total = crustPrice + sizePrice + topsPrice;

        return total;
    }
}
