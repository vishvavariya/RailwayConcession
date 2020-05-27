package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class ResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CGPA CALCULATOR");

        EditText sgpa, percentage;

        sgpa = (EditText) findViewById(R.id.sgpa);
        percentage = (EditText) findViewById(R.id.percentage);
        TextView t = (TextView) findViewById(R.id.t);
        TextView t1 = (TextView) findViewById(R.id.t3);


        try {

            Bundle b = getIntent().getExtras();
            float final_sgpa = b.getFloat("final_sgpa");
            int flag = b.getInt("flag");
            float final_perc = b.getFloat("final_perc");

            if (flag == 0) {
                t.setText("Your CGPA is ");
                percentage.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
            }

            BigDecimal bd = new BigDecimal(final_sgpa).setScale(2, RoundingMode.HALF_EVEN);
            final_sgpa = bd.floatValue();


            BigDecimal bd1 = new BigDecimal(final_perc).setScale(2, RoundingMode.HALF_EVEN);
            final_perc = bd1.floatValue();

            sgpa.setText(String.valueOf(final_sgpa));
            percentage.setText(String.valueOf(final_perc + "%"));
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Exception Occured", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), CgpaCalculator.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}