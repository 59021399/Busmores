package com.example.bs00;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    private List<Item> NumberHolder = new ArrayList<>();
    private List<Item> hard;
    DatabaseReference ref;
    Button Switch;

    private MyRecyclerAdapter myRecyclerAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    String[] strings1 = {"sdf", "d", "f", "f", "f", "f", "", "", "", "", "",""};
    int getDear = 1;
    private String[] item;
    int dear;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(NumberHolder);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        View root;

        Switch =findViewById(R.id.switch_btn);
        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DriverActivity.this, SwitchActivity.class);
                startActivity(i);
            }
        });
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
//ตั้งค่า Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//ตั้งค่า Adapter
        final String []stringdear= new String[11];
        String[] strings8 = { "คณะICT","จุดจอดรถตึก PKY", "ตึกคณะวิทยาศาสตร์",   "ตึกวิศวกรรมศาตร์", "ตึกศิลปศาสตร์",   "ตึกสงวนเสริมศรี","ตึกอธิการบดี", "ตึกอุบาลี",  "สมมติ" ,"สายหอใน","สายเลขคี่","สายเลขคู่","หน้ามหาวิทยาลัย","หอประชุมพระยางำเมือง", "หอในUP-Dorm"}; //ข้อความอะไรก็ได้
        for (int k=0 ;k<strings8.length;k++){
            mRootRef.child("Check_").child( strings8[k]).setValue(false);


        }

        ref = FirebaseDatabase.getInstance().getReference().child("Waiter");
        ref.addValueEventListener(new ValueEventListener() {
            String[] strings = {"หน้ามหาวิทยาลัย", "คณะICT", "ตึกวิศวกรรมศาตร์", "ตึกคณะวิทยาศาสตร์", "หอประชุมพระยางำเมือง", "ตึกอธิการบดี", "ตึกศิลปศาสตร์", "ตึกอุบาลี", "หอในUP-Dorm", "ตึกสงวนเสริมศรี", "จุดจอดรถตึก PKY","สมมิต"}; //ข้อความอะไรก็ได้

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NumberHolder.clear();
                hard = (List<Item>) dataSnapshot.getValue();
                for (int i = 0; i <= 11; i++) {


                     strings1[i] = String.valueOf(hard.get(i));

                    Item item = new Item(strings1[i], this.strings[i]);
                    NumberHolder.add(item);


                }

                recyclerView.setAdapter(adapter);

                Log.i("dearINloop no 5", "" + stringdear[5]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        }

        );



}


    @Override
    protected void onStop() {


        super.onStop();
    }


    }







