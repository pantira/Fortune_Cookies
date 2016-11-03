package com.egco438.a13273;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a13273.egco438.com.a13273.R;

/**
 * Created by Benz on 10/31/2016.
 */
public class CustomArrayAdapter extends ArrayAdapter<Fortune>{
    Context context;
    List<Fortune> objects;

    public CustomArrayAdapter(Context context,int resource,List <Fortune> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fortune fortune = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datalist, null);
        view.setBackgroundColor(Color.WHITE);

        TextView txt = (TextView)view.findViewById(R.id.msgText);
        txt.setText(fortune.getMessage());
        if(txt.getText().equals("Don't Panic")){
            txt.setTextColor(ContextCompat.getColor(context, R.color.badMessage));
        }
        else{
            txt.setTextColor(ContextCompat.getColor(context, R.color.goodMessage));
        }

        TextView time = (TextView)view.findViewById(R.id.timestamp);
        time.setText(fortune.getTimestamp());

        ImageView image = (ImageView)view.findViewById(R.id.imageView);
        int res = context.getResources().getIdentifier(fortune.getPicName(),"drawable",context.getPackageName());
        image.setImageResource(res);
        return view;

    }

}
