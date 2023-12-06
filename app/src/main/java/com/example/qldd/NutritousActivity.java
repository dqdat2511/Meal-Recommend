package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.qldd.Object.Nutritous;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NutritousActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference nuRef = database.getReference("Nutritous");
    DatabaseReference NomRef= database.getReference("NutritousOnMaterial");
    ArrayAdapter<String> adapter;
    List<String> NutritousList = new ArrayList<>();
    List<String> SelectedNutritous = new ArrayList<>();
    Button saveBtn;
    EditText txtNamechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritous);
        init();
        UpNutritous();



    }


    private void UpNutritous(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Nutritous nutritous = new Nutritous();
                        nutritous.setName(txtNamechat.getText().toString().trim());
                        String newid = nuRef.push().getKey();
                        nutritous.setId(newid);
                        nuRef.child(newid).setValue(nutritous);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }


    private  void init(){
        saveBtn = (Button) findViewById(R.id.btnSave);
        txtNamechat=findViewById(R.id.txttenChat);

    }
}