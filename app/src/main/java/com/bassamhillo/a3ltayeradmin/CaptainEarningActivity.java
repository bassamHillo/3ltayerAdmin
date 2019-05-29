package com.bassamhillo.a3ltayeradmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class CaptainEarningActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference itemRef;
    private TextView nameTV, countryTV, cityTV, sexTV, mobileTV;
    private EditText search;
    private FirebaseAuth mAuth;
    private List<CaptainEarning> list = new ArrayList<CaptainEarning>();
    private TableRow.LayoutParams layoutParams;

    float scale;

    private TableRow oneUser;


    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_captain_earning);

        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        // oneUser.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        layoutParams.height = (int) (50 * scale + 0.5f);
        layoutParams.setMargins(1, 0, 0, 1);


        table = (TableLayout) findViewById(R.id.captain_earning_tabel);
        search = (EditText) findViewById(R.id.captains_earning_search);

        database = FirebaseDatabase.getInstance();
        itemRef = database.getReference("Captains");
        mAuth = FirebaseAuth.getInstance();

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.equals("")) {
                    table.removeAllViews();
                    oneUser = new TableRow(CaptainEarningActivity.this);
                    oneUser.setLayoutParams(layoutParams);

                    initTV(nameTV, "Name", 0);
                    initTV(countryTV, "Country", 1);
                    initTV(cityTV, "City", 2);
                    initTV(mobileTV, "Mobile#", 3);
                    initTV(sexTV, "Earning", 4);

                    table.addView(oneUser);
                    for (int i = 0; i < list.size(); i++) {

                        oneUser = new TableRow(CaptainEarningActivity.this);
                        oneUser.setLayoutParams(layoutParams);

                        initTV(nameTV, list.get(i).getName(), 0);
                        initTV(countryTV, list.get(i).getCountry(), 1);
                        initTV(cityTV, list.get(i).getCity(), 2);
                        initTV(mobileTV, list.get(i).getMobile(), 3);
                        initTV(sexTV, list.get(i).getEarning() + "", 4);

                        table.addView(oneUser);

                        final int finalI = i;
                        oneUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                captainClick(list.get(finalI).getUid(), list.get(finalI).getEarning());
                            }
                        });


                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                table.removeAllViews();
                oneUser = new TableRow(CaptainEarningActivity.this);
                oneUser.setLayoutParams(layoutParams);

                initTV(nameTV, "Name", 0);
                initTV(countryTV, "Country", 1);
                initTV(cityTV, "City", 2);
                initTV(mobileTV, "Mobile#", 3);
                initTV(sexTV, "Earning", 4);

                table.addView(oneUser);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().indexOf(s.toString()) > -1) {

                        oneUser = new TableRow(CaptainEarningActivity.this);
                        oneUser.setLayoutParams(layoutParams);

                        initTV(nameTV, list.get(i).getName(), 0);
                        initTV(countryTV, list.get(i).getCountry(), 1);
                        initTV(cityTV, list.get(i).getCity(), 2);
                        initTV(mobileTV, list.get(i).getMobile(), 3);
                        initTV(sexTV, list.get(i).getEarning() + "", 4);

                        table.addView(oneUser);

                        final int finalI = i;
                        oneUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                captainClick(list.get(finalI).getUid(), list.get(finalI).getEarning());
                            }
                        });

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initUsers();
    }

    public void initUsers() {
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                list.clear();
                table.removeAllViews();
                oneUser = new TableRow(CaptainEarningActivity.this);
                oneUser.setLayoutParams(layoutParams);

                initTV(nameTV, "Name", 0);
                initTV(countryTV, "Country", 1);
                initTV(cityTV, "City", 2);
                initTV(mobileTV, "Mobile#", 3);
                initTV(sexTV, "Earning", 4);

                table.addView(oneUser);
                for (final DataSnapshot user : dataSnapshot.getChildren()) {
                    oneUser = new TableRow(CaptainEarningActivity.this);
                    oneUser.setLayoutParams(layoutParams);

                    String name, country, city, mobile, email;
                    final int earning;
                    if (user.child("AcceptedFromAdmin").exists() && !user.child("AcceptedFromAdmin").getValue(boolean.class)) {
                        continue;
                    }
                    if (!user.child("earnings").exists())
                        continue;


                    name = user.child("Name").getValue(String.class);
                    country = user.child("Country").getValue(String.class);
                    city = user.child("City").getValue(String.class);
                    mobile = user.child("PhoneNumber").getValue(String.class);
                    earning = user.child("earnings").getValue(Integer.class);

                    if (earning <= 0)
                        continue;

                    list.add(new CaptainEarning(name, country, city, mobile, earning, user.getKey()));

                    initTV(nameTV, name, 0);
                    initTV(countryTV, country, 1);
                    initTV(cityTV, city, 2);
                    initTV(mobileTV, mobile, 3);
                    initTV(sexTV, earning + "", 4);


                    oneUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            captainClick(user.getKey(), earning);
                        }
                    });

                    if (!search.getText().toString().equals(""))
                        return;

                    table.addView(oneUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void initTV(TextView TV, String value, int pos) {
        TV = new TextView(CaptainEarningActivity.this);
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

    public void captainClick(final String uid, final int earn) {
        final EditText input = new EditText(this);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Tax=" + (Double) (earn * 2.0) / 100.0);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText().toString().equals(""))
                    return;
                int value = Integer.parseInt(input.getText().toString().trim());
                itemRef.child(uid).child("earnings").setValue(earn - (value * 100) / 2);
                search.setText("");
            }
        });
        alert.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}
