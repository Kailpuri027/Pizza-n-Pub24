package capstone.conestoga.javafiles;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.conestoga.pizzanpub24.R;

/**
 * Created by Kailpuri on 8/5/2016.
 */
public class CustomListAdapter extends ArrayAdapter{

    public CustomListAdapter(Context context, ArrayList<CartItem> resource) {
        super(context, R.layout.list_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_row, parent, false);

        CartItem item = (CartItem)getItem(position);

        TextView itemName = (TextView) customView.findViewById(R.id.title);
        TextView price = (TextView) customView.findViewById(R.id.price);
        ImageView iv = (ImageView) customView.findViewById(R.id.list_image);

        itemName.setText(item.getItem());
        price.setText("$"+item.getPrice());
        iv.setImageResource(R.drawable.pizzabg);

        return customView;
    }
}
