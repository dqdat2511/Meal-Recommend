package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qldd.Adapter.ChatAdapter;
import com.example.qldd.Adapter.MarterialAdapter;
import com.example.qldd.Object.Material;
import com.example.qldd.Object.NutritousOnMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDinhDuongActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference maADRef = database.getReference("Material");
    DatabaseReference NOMREF = database.getReference("NutritousOnMaterial");
    DatabaseReference nuRef = database.getReference("Nutritous");
    RecyclerView ChatRv;
    ChatAdapter chatAdapter;
    List<NutritousOnMaterial> NomList = new ArrayList<>();
    String idNl;
    TextView txtTen;
    ImageView imgHinh;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dinh_duong);
        init();

//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Khai báo thông tin");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent main = new Intent(ChiTietDinhDuongActivity.this, MaterialActivity.class);
//                startActivityForResult(main, 1);
//                finish();
//            }
//        });
        ChatRv = findViewById(R.id.rvChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatAdapter = new ChatAdapter(NomList , this);
        ChatRv.setAdapter(chatAdapter);
        ChatRv.setLayoutManager(linearLayoutManager);
        if(getIntent().getStringExtra("id-NL") != null) {
            idNl = getIntent().getStringExtra("id-NL").trim();
        }

        NOMREF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //NomList.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        NutritousOnMaterial nutritousOM = dataSnapshot.getValue(NutritousOnMaterial.class);
                        if(nutritousOM.getIdMaterial().equals(idNl)){
                            NomList.add(nutritousOM);
                            maADRef.child(idNl).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Material material = snapshot.getValue(Material.class);
                                    txtTen.setText(material.getTenNguyenLieu());
                                    Glide.with(getApplicationContext()).load(material.getHinhNguyenLieu()).into(imgHinh);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void init(){
        txtTen = findViewById(R.id.txtNameNguyenLieu);
        imgHinh =findViewById(R.id.imgHinhNguyenLieuCT);

    }
    protected void onResume() {
        super.onResume();
        chatAdapter.notifyDataSetChanged();
    }
}