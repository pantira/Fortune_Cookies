package com.egco438.a13273;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import a13273.egco438.com.a13273.R;


public class MainActivity extends AppCompatActivity  {

    private DataSource dataSource;
    ArrayAdapter<Fortune> customArrayAdapter ;
    protected List<Fortune> values = new ArrayList<>();
    ListView listView;

    private String time="31-Oct-2016 5.30 p.m.";
    public static final int DETAIL_REQ_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);

        dataSource = new DataSource(this);
        dataSource.open();
        values = dataSource.getAllFortune();
        customArrayAdapter = new CustomArrayAdapter(this, 0, values);
        listView.setAdapter(customArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final Fortune object = (Fortune) listView.getItemAtPosition(position);
                dataSource.deleteFortune(object);

                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        customArrayAdapter.remove(object);
                        customArrayAdapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_REQ_CODE) {
            if(resultCode == RESULT_OK){
                String Message = data.getStringExtra(NewFortune.Message);
                String Picture = data.getStringExtra(NewFortune.Pic_name);
                String Time = data.getStringExtra(NewFortune.Time);
                customArrayAdapter.add(dataSource.createFortune(Message,Picture,Time));
            }
        }
        customArrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NewFortune.class);
        startActivityForResult(intent, DETAIL_REQ_CODE);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
