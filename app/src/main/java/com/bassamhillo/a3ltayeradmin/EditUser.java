package com.bassamhillo.a3ltayeradmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUser extends AppCompatActivity {

    private String uid;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase, usingRef;
    private FirebaseAuth mAuth;

    private TextView motorType, motorColor, plateNumber;
    private ImageView captainImage;
    private Button confirm,delete;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        uid=getIntent().getStringExtra("uid");

        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //////////////////////////////////////////////////////////////////////////////////////////////
        motorType = (TextView) findViewById(R.id.CaptainMotorName);
        motorColor = (TextView) findViewById(R.id.CaptainMotorColorList);
        plateNumber = (TextView) findViewById(R.id.CaptainPlateNumber);
        captainImage = (ImageView) findViewById(R.id.CaptainImage);

        confirm = (Button) findViewById(R.id.CaptainConfirm);
        delete = (Button) findViewById(R.id.CaptainDelete);

        if(getIntent().getStringExtra("type").equals("Captains"))
        {
            confirm.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }

        dialog = ProgressDialog.show(EditUser.this, "", "Loading , Please wait ....", true);
        usingRef=database.getReference(uid.replaceAll("newCaptain","Captains"));

        getData();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usingRef.child("AcceptedFromAdmin").setValue(true);
                startActivity(new Intent(getApplicationContext(),UsersActivity.class).putExtra("type","newCaptain"));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usingRef.removeValue();
                startActivity(new Intent(getApplicationContext(),UsersActivity.class).putExtra("type","newCaptain"));

            }
        });


    }

    public void getData() {
        usingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String motorTypeDB,motorColorDB,motorPlateDB;

                motorTypeDB=dataSnapshot.child("MotorDetails").child("MotorType").getValue(String.class);
                motorColorDB=dataSnapshot.child("MotorDetails").child("MotorColor").getValue(String.class);
                motorPlateDB=dataSnapshot.child("MotorDetails").child("PlateNumber").getValue(String.class);

                motorType.setText(motorTypeDB);
                motorColor.setText(motorColorDB);
                plateNumber.setText(motorPlateDB);

              Glide.with(EditUser.this).load(dataSnapshot.child("image").getValue(String.class)).into(captainImage);
              captainImage.setBackgroundColor(Color.WHITE);


                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
