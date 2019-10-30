package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;

    EditText name , age , phone;
    Button upload , delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference("Database").child("‘Users");

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);
        upload = (Button) findViewById(R.id.UploadBU);
        delete = (Button) findViewById(R.id.UpdeleteBU);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText().toString())){
                    Model model = new Model(name.getText().toString(),age.getText().toString(),phone.getText().toString());
                    reference.push().setValue(model);

                }else {
                    Toast.makeText(MainActivity.this,"please enter name",Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryDeleteName();
            }
        });

       getSingleData();
       getMultiData();
       queryGetName();

    }

    public void getSingleData(){
        FirebaseDatabase.getInstance().getReference().child("Database").child("text").child("names")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.v("single", dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getMultiData(){
        FirebaseDatabase.getInstance().getReference().child("Database").child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                Model mod = dataSnapshot.getValue(Model.class);
                                if (mod.getName()!=null) {
                                    Log.v("multi mody ", mod.getName());
                                }
                            }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void queryGetName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Database").child("‘Users");

        Query query = ref.orderByChild("name").equalTo("Hossam");
        //System.out.println("queryy "+query);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //System.out.println("snapp "+dataSnapshot.getChildren());
                if(dataSnapshot.exists()){
                    System.out.println("yess");
                    Toast.makeText(MainActivity.this,"yes here",Toast.LENGTH_LONG).show();
                    for(DataSnapshot snap: dataSnapshot.getChildren()) {
                        Model mod = snap.getValue(Model.class);

                        Log.v("hossam phone ",mod.getPhone());
                    }

                }
                Toast.makeText(MainActivity.this,"No here",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void queryDeleteName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Database").child("‘Users");

        Query query = ref.orderByChild("name").equalTo("Hossam");
        //System.out.println("queryy "+query);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //System.out.println("snapp "+dataSnapshot.getChildren());
                if(dataSnapshot.exists()){
                    System.out.println("yess");
                    Toast.makeText(MainActivity.this,"yes here",Toast.LENGTH_LONG).show();
                    for(DataSnapshot snap: dataSnapshot.getChildren()) {
                        Model mod = snap.getValue(Model.class);

                        snap.getRef().removeValue();
                    }

                }
                Toast.makeText(MainActivity.this,"No here",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





}
