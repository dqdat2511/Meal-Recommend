package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.qldd.Adapter.MarterialAdapter;
import com.example.qldd.Object.Material;
import com.example.qldd.Object.NutritousOnMaterial;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MaterialActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference maRef = database.getReference("Material");
    DatabaseReference NOMREF = database.getReference("NutritousOnMaterial");
    RecyclerView nguyenLieuRv;
    Toolbar toolbar;
    MarterialAdapter nguyenLieuApdater;
    List<Material> materialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        Drawable drawable= getResources().getDrawable(R.drawable.ic_angle_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        init();

        nguyenLieuRv =(RecyclerView) findViewById(R.id.materialRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        nguyenLieuApdater = new MarterialAdapter(materialList, this);
        nguyenLieuRv.setAdapter(nguyenLieuApdater);
        nguyenLieuRv.setLayoutManager(linearLayoutManager);



        maRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Material nom = dataSnapshot.getValue(Material.class);
                        materialList.add(nom);

                    }
                }
                nguyenLieuApdater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){

    }
    @Override
    protected void onResume() {
        super.onResume();
        nguyenLieuApdater.notifyDataSetChanged();
    }
}