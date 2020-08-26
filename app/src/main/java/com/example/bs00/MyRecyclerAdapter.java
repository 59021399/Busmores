package com.example.bs00;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Holder> {
    private List<Item> mDataSet;
    private String[] getmDataSet;
    private List<Button> dataButtons;
    private Integer[] Waiter;
    private int Waiterint;
    private boolean check = false;
    Query ref;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Item>().setQuery(mRootRef, Item.class).build();
    private Context mContext;


    //private ArrayList<Waiternum> mydataset;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_driver_item, viewGroup, false);
        Holder holder = new Holder(view);

        String[] strings8 = { "คณะICT","จุดจอดรถตึก PKY", "ตึกคณะวิทยาศาสตร์",   "ตึกวิศวกรรมศาตร์", "ตึกศิลปศาสตร์",   "ตึกสงวนเสริมศรี","ตึกอธิการบดี", "ตึกอุบาลี",  "สมมติ" ,"สายหอใน","สายเลขคี่","สายเลขคู่","หน้ามหาวิทยาลัย","หอประชุมพระยางำเมือง", "หอในUP-Dorm"}; //ข้อความอะไรก็ได้
        for (int k=0 ;k<strings8.length;k++){
            mRootRef.child("Check_").child( strings8[k]).setValue(false);


        }

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        final Item item = mDataSet.get(i);
        holder.setItem(i);
        holder.textDescription.setText(item.getWaiter());
        holder.textTitle.setText(item.getDesc());


    }

    @Override
    public int getItemCount() {


        return mDataSet.size();
    }

    public MyRecyclerAdapter(List<Item> dataSet) {
        mDataSet = dataSet;
    }

    class Holder extends RecyclerView.ViewHolder {
        View mView;
        TextView textTitle;
        TextView textDescription;
        Button actionbtn;

        public Holder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.Busstop_name);
            actionbtn = itemView.findViewById(R.id.buttonapply);
            textDescription = itemView.findViewById(R.id.Numberhold);
            mView = itemView;

        }


        public void setItem(final int position) {
            ref = mRootRef.child("Waiter");
            final String[] strings = {"หน้ามหาวิทยาลัย", "คณะICT", "ตึกวิศวกรรมศาตร์", "ตึกคณะวิทยาศาสตร์", "หอประชุมพระยางำเมือง", "ตึกอธิการบดี", "ตึกศิลปศาสตร์", "ตึกอุบาลี", "หอในUP-Dorm", "ตึกสงวนเสริมศรี", "จุดจอดรถตึก PKY", "สมมติ"}; //ข้อความอะไรก็ได้
            final int finalWaiter;

            final int ps = position;

            actionbtn.setText("ยืนยัน");
            actionbtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    mRootRef.child("Check_").child("" + strings[position]).setValue(true);
                    mRootRef.child("Check_").child("" + strings[position]).setValue(false);
                    mRootRef.child("Waiter").child("" + position).setValue("0");
                    mRootRef.child("Noice").setValue(strings[position]);
                    check = true;
                    mRootRef.child("Check_State").setValue(check);


                }
            });


        }

       /* public void Check() {
            final Integer[] strings = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; //ข้อความอะไรก็ได้
            for (int i = 0; i < strings.length; i++) {

                final int finalI = i;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int Waiter = Integer.parseInt(dataSnapshot.child("" + strings[finalI]).getValue().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }*/
    }
}

