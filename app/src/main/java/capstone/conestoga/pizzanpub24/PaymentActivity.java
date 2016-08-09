package capstone.conestoga.pizzanpub24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    protected TextView cost, tax, total;
    protected EditText cnumber, cexp,csec;
    protected Button mpayment;
    protected double cp, tx, tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cost = (TextView) findViewById(R.id.fbcost);
        tax = (TextView) findViewById(R.id.taxcost);
        total = (TextView) findViewById(R.id.totalcost);

        cnumber = (EditText) findViewById(R.id.cardnumber);
        cexp = (EditText) findViewById(R.id.expdate);
        csec = (EditText) findViewById(R.id.secno);

        mpayment = (Button) findViewById(R.id.btn_makepayment);

        cp = getIntent().getExtras().getDouble("amount");
        tx = (10*cp)/100;
        tc = cp + tx;

        cost.setText("$"+cp);
        tax.setText("$"+tx);
        total.setText("$"+tc);
        Log.i("Total PA", "$" + cp);

        mpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnum = cnumber.getText().toString().trim();
                String cxdate = cexp.getText().toString().trim();
                String csecno = csec.getText().toString().trim();

                if(TextUtils.isEmpty(cnum)){
                    cnumber.setError("Enter card number");
                    return;
                }

                if(cnum.length() < 16) {
                    cnumber.setError("Card number must have 16 digits");
                    return;
                }

                if(TextUtils.isEmpty(cxdate)) {
                    cexp.setError("Enter exp. date");
                    return;
                }

                if(TextUtils.isEmpty(csecno)) {
                    csec.setError("Enter security number");
                    return;
                }


            }
        });
    }
}
