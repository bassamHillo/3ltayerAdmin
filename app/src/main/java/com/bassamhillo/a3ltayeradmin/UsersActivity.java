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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.graphics.Color.BLACK;

public class UsersActivity extends AppCompatActivity {

    private String type;
    private FirebaseDatabase database;
    private DatabaseReference itemRef;
    private TextView nameTV, countryTV, cityTV, sexTV, mobileTV;
    private FirebaseAuth mAuth;
    private TableRow.LayoutParams layoutParams;



    float scale;

    private TableRow oneUser;


    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_users);

        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        // oneUser.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        layoutParams.height = (int) (50 * scale + 0.5f);
        layoutParams.setMargins(1, 0, 0, 1);

        type = getIntent().getStringExtra("type");

        table = (TableLayout) findViewById(R.id.users_tabel);

        database = FirebaseDatabase.getInstance();
        itemRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        if (type.equals("users")) {
            itemRef = database.getReference("users");
        } else {
            itemRef = database.getReference("Captains");
        }
        initUsers();
    }

    public void initUsers() {
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot user : dataSnapshot.getChildren()) {
                    oneUser = new TableRow(UsersActivity.this);
                    oneUser.setLayoutParams(layoutParams);

                    String name, country, city, sex, mobile, email;
                    if (user.child("AcceptedFromAdmin").exists() && !user.child("AcceptedFromAdmin").getValue(boolean.class) && type.equals("Captains")) {
                        continue;
                    } else if(user.child("AcceptedFromAdmin").exists() && user.child("AcceptedFromAdmin").getValue(boolean.class) && type.equals("newCaptain")) {
                        continue;
                    }
                    name = user.child("Name").getValue(String.class);
                    country = user.child("Country").getValue(String.class);
                    city = user.child("City").getValue(String.class);
                    mobile = user.child("PhoneNumber").getValue(String.class);
                    sex = user.child("Sex").getValue(String.class);


                    initTV(nameTV, name, 0);
                    initTV(countryTV, country, 1);
                    initTV(cityTV, city, 2);
                    initTV(mobileTV, mobile, 3);
                    initTV(sexTV, sex, 4);


                    oneUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!type.equals("users"))
                            startActivity(new Intent(UsersActivity.this, EditUser.class).putExtra("uid", type + "/" + user.getKey()).putExtra("type",type));
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

    public void initTV(TextView TV, String value, int pos) {
        TV = new TextView(UsersActivity.this);
        TV.setText(value);
        TV.setHeight((int) (30 * scale + 0.5f));
        TV.setWidth((int) (75 * scale + 0.5f));
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TV.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loginbut));
        } else {
            TV.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.loginbut));
        }
        TV.setGravity(EditText.TEXT_ALIGNMENT_CENTER);
        TV.setGravity(Gravity.CENTER_HORIZONTAL);

        TV.setTextSize(10);
        TV.setTextColor(Color.BLACK);
        oneUser.addView(TV, pos);

    }
}
