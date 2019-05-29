package com.bassamhillo.a3ltayeradmin;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MarketsActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference itemRef;
    private TextView nameTV,countryTV,cityTV,sexTV,mobileTV;
    private FirebaseAuth mAuth;


    float scale;
    private TableRow oneUser;
    private TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markets);

        scale= getApplicationContext().getResources().getDisplayMetrics().density;

        table=(TableLayout)findViewById(R.id.markets_tabel);

        database = FirebaseDatabase.getInstance();
        itemRef = database.getReference("market");
        mAuth=FirebaseAuth.getInstance();


        initUsers();

    }

    public void initUsers() {
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for(final DataSnapshot user:dataSnapshot.getChildren())
                {
                    oneUser=new TableRow(MarketsActivity.this);
                    // oneUser.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight=1;
                    layoutParams.height=(int) (50 * scale + 0.5f);
                    layoutParams.setMargins(1,0,0,1);
                    oneUser.setLayoutParams(layoutParams);

                    String name,type,city,sex,sType,phone;
                    name=user.child("market_name").getValue(String.class);
                    city=user.child("city").getValue(String.class);
                    type=user.child("market_type").getValue(String.class);
                    sType=user.child("SpecificType").getValue(String.class);
                    phone=user.child("phone").getValue(String.class);


                    initTV(nameTV,name,0);
                    initTV(countryTV,type,1);
                    initTV(sexTV,sType,2);
                    initTV(cityTV,city,3);
                    initTV(mobileTV,phone,4);

                    oneUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });

                    table.addView(oneUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void initTV(TextView TV,String value,int pos){
        TV=new TextView(MarketsActivity.this);
        TV.setText(value);
        TV.setHeight((int) (30 * scale + 0.5f));
        TV.setWidth((int) (75 * scale + 0.5f));
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TV.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loginbut) );
        } else {
            TV.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loginbut));
        }
        TV.setGravity(EditText.TEXT_ALIGNMENT_CENTER);
        TV.setGravity(Gravity.CENTER_HORIZONTAL);
        TV.setTextSize(11);
        TV.setTextColor(Color.BLACK);
        oneUser.addView(TV,pos);

    }
}
