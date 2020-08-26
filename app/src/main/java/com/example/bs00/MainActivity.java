package com.example.bs00;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Button btnproceed;
    private Button btn_mDriver,btn_mPassenger;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnproceed=(Button)findViewById(R.id.btnProcess);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, DriverMapsActivity.class);
                startActivity(i);
            }
        });
        btn_mDriver=(Button)findViewById(R.id.btn_Driver);
        btn_mPassenger=(Button)findViewById(R.id.btn_Passenger);

        btn_mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;


            }
        });
        btn_mPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomerLoginActivity.class);
                startActivity(intent);
                finish();
                return;


            }
        });
        String[] strings8 = { "คณะICT","จุดจอดรถตึก PKY", "ตึกคณะวิทยาศาสตร์",   "ตึกวิศวกรรมศาตร์", "ตึกศิลปศาสตร์",   "ตึกสงวนเสริมศรี","ตึกอธิการบดี", "ตึกอุบาลี",  "สมมติ" ,"สายหอใน","สายเลขคี่","สายเลขคู่","หน้ามหาวิทยาลัย","หอประชุมพระยางำเมือง", "หอในUP-Dorm"}; //ข้อความอะไรก็ได้
        for (int k=0 ;k<strings8.length;k++){
            mRootRef.child("Check_").child( strings8[k]).setValue(false);


        }


    }

    @Override
    public void onClick(View view) {
        if(view==btnproceed){
            finish();
        }

    }

}