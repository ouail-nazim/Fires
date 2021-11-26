package net.smallacademy.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListAdapter extends ArrayAdapter<String> {
    Context context;
    String rTitle[];
    String rDescription[];
    int rStatus[];

    ListAdapter(Context c, String title[], int status[], String description[]) {
        super(c, R.layout.fire_item_row, R.id.title, title);
        this.context = c;
        this.rTitle = title;
        this.rStatus = status;
        this.rDescription = description;

    }

    @SuppressLint({"ResourceAsColor", "Range"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.fire_item_row, parent, false);

        TextView myTitle = row.findViewById(R.id.title);
        TextView myState = row.findViewById(R.id.state);
        TextView myDescription = row.findViewById(R.id.desc);

        myTitle.setText(rTitle[position]);
        myDescription.setText(rDescription[position]);
        switch (rStatus[position]) {
            case 1:
                myState.setText("Danger");
                myState.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 2:
                myState.setText("medium");
                myState.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA833")));
                break;
            case 3:
                myState.setText("Low");
                myState.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9FD012")));
                break;

        }


        return row;
    }


}
