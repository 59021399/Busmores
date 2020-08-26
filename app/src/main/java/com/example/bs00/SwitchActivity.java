package com.example.bs00;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class SwitchActivity extends AppCompatActivity {
    Button Button1, Button2, Button3;
    TextView t1, t2, t3;
    TextView s1, s2, s3;
    int sum1 = 0;
    List<Integer> Listodd = new ArrayList<>();
    List<Integer> Listeven = new ArrayList<>();
    List<Integer> Listup = new ArrayList<>();
    int[] routeodd = {0, 1, 3, 4, 5, 6, 10, 11};
    int[] routeven = {0, 2, 3, 4, 5, 6, 10, 11};
    int[] routeup = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    String [] Class ={"สายเลขคี่","สายเลขคู่","สายหอใน"};
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Query ref;
    DatabaseReference referenceD;
    List<Integer> vowelsList = new ArrayList<>(8);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        Button1 = findViewById(R.id.button1);
        Button2 = findViewById(R.id.button2);
        Button3 = findViewById(R.id.button3);
        s1 = findViewById(R.id.numodd);
        s2 = findViewById(R.id.numeven);
        s3 = findViewById(R.id.numup);
        //odd\

        ref = reference.child("Waiter");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Setodd();
                Listodd.clear();
                Seteven();
                Listeven.clear();
                Setup();
                Listup.clear();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Check_").child("สายเลขคี่").setValue(true);
                reference.child("Check_").child("สายเลขคี่").setValue(false);
                for (int i = 0; i < routeodd.length; i++) {
                    reference.child("Waiter").child("" + routeodd[i]).setValue("0");

                    reference.child("Noice").setValue("สายเลขคี่");

                }
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Check_").child("สายเลขคู่").setValue(true);
                reference.child("Check_").child("สายเลขคู่").setValue(false);
                for (int i = 0; i < routeven.length; i++) {
                    reference.child("Waiter").child("" + routeven[i]).setValue("0");

                    reference.child("Noice").setValue("สายเลขคู่");
                }
            }
        });

        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Check_").child("สายหอใน").setValue(true);
                reference.child("Check_").child("สายหอใน").setValue(false);
                for (int i = 0; i < routeup.length; i++) {
                    reference.child("Waiter").child("" + routeup[i]).setValue("0");

                    reference.child("Noice").setValue("สายหอใน");
                }
            }
        });
    }

    public void Setup(){
        for (int i = 0; i < routeup.length; i++) {
            ref = reference.child("Waiter").child("" + routeup[i]);
            final int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int sum = 0;
                    String Value = (String) dataSnapshot.getValue();
                    int Valueint = Integer.parseInt(Value);

                    Listup.add(Valueint);

                    if (finalI == 10) {
                        Log.i("Logdear", "" + Listup);
                        for (int i : Listup) {
                            sum += i;
                            s3.setText("" + sum);
                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }

    public void Seteven() {

        for (int i = 0; i < routeven.length; i++) {
            ref = reference.child("Waiter").child("" + routeven[i]);
            final int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int sum = 0;
                    String Value = (String) dataSnapshot.getValue();
                    int Valueint = Integer.parseInt(Value);

                    Listeven.add(Valueint);

                    if (finalI == 7) {
                        Log.i("Logdear", "" + Listeven);
                        for (int i : Listeven) {
                            sum += i;
                            s2.setText("" + sum);
                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }

    public void Setodd() {

        for (int i = 0; i < routeodd.length; i++) {
            ref = reference.child("Waiter").child("" + routeodd[i]);
            final int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int sum = 0;
                    String Value = (String) dataSnapshot.getValue();
                    int Valueint = Integer.parseInt(Value);

                    Listodd.add(Valueint);

                    if (finalI == 7) {
                        Log.i("Logdear", "" + Listodd);
                        for (int i : Listodd) {
                            sum += i;
                            s1.setText("" + sum);
                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }
}
