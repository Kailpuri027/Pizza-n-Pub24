package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import capstone.conestoga.javafiles.CartItem;
import capstone.conestoga.javafiles.CustomListAdapter;

public class CartActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference mRootRef, itemRef;
    private ArrayList<CartItem> itemsList;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(CartActivity.this, PaymentActivity.class);
                    i.putExtra("amount", price);
                    startActivity(i);
                    finish();
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        final TextView t = (TextView) findViewById(R.id.cartmsg);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        itemRef = mRootRef.child("allItems");
        itemsList = new ArrayList<CartItem>();
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                price = 0;
                for (DataSnapshot itemsShot : dataSnapshot.getChildren()) {
                    if (itemsShot.child("userId").getValue().equals(user.getUid())) {
                        if (itemsShot.child("status").getValue().equals(false)) {
                            CartItem item = itemsShot.getValue(CartItem.class);
                            itemsList.add(item);
                            price += item.getPrice();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter adapter = new CustomListAdapter(this, itemsList);
        final ListView lv = (ListView) findViewById(R.id.listitems);
        lv.setAdapter(adapter);

        if(itemsList.size() == 0) {
            t.setVisibility(View.GONE);
        }
        else
        {
            t.setVisibility(View.VISIBLE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(CartActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.cart_menu, popup.getMenu());
                popup.show();
            }
        });
    }
}
