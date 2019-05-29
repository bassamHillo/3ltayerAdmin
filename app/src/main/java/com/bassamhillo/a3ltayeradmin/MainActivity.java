package com.bassamhillo.a3ltayeradmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button users,captains,markets,newCaptain,tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users=(Button)findViewById(R.id.users);
        captains=(Button)findViewById(R.id.captans);
        markets=(Button)findViewById(R.id.markets);
        newCaptain=(Button)findViewById(R.id.new_captain);
        tax=(Button)findViewById(R.id.new_earning);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UsersActivity.class).putExtra("type","users"));
            }
        });
        captains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UsersActivity.class).putExtra("type","Captains"));
            }
        });
        markets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MarketsActivity.class));
            }
        });
        newCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UsersActivity.class).putExtra("type","newCaptain"));
            }
        });
        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CaptainEarningActivity.class));
            }
        });
    }
}
