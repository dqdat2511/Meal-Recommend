package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qldd.Object.Material;
import com.example.qldd.Object.Nutritous;
import com.example.qldd.Object.NutritousOnMaterial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MaterialAdminActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference maADRef = database.getReference("Material");
    DatabaseReference NOMREF = database.getReference("NutritousOnMaterial");
    DatabaseReference nuRef = database.getReference("Nutritous");
    EditText txtTenDinhDuong,  txtSoLuong;
    TextView txtTen;
    Button btnPush, btnTim, btnluu;
    ImageView imgHinh;
    Spinner spinnerNL;
    List<Material> materialList = new ArrayList<>();
    List<String> listspinner = new ArrayList<>();
    ArrayAdapter<String> spinneradapter ;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> NutritousList = new ArrayList<>();
    List<NutritousOnMaterial> nomlist = new ArrayList<>();
    List<String> SelectedNutritous = new ArrayList<>();
    String idMaterial;
    String idNutritous;
    HashSet<String> hashSet = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_admin2);
        init();
        spinneradapter= new ArrayAdapter<String>(MaterialAdminActivity.this , android.R.layout.simple_spinner_dropdown_item,listspinner);
        AddNguyenLieu();
        TimNguyenLieu();
        ItemDD();
        spinnerNL.setAdapter(spinneradapter);
        Showspinner();
        LuuDD();
        //Layve();





    imgHinh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }
});

    }

    private void ThemDinhDuong() {
        maADRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Material material = dataSnapshot.getValue(Material.class);
                    if (material.getTenNguyenLieu().matches(spinnerNL.getSelectedItem().toString())) {

                        //maADRef.child(material.getNguyenLieu_id()).child("tenNguyenLieu").setValue(txtTenDinhDuong.getText().toString().trim());


                        idMaterial = material.getNguyenLieu_id();


                    }
                }


                nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Nutritous nutritous = dataSnapshot.getValue(Nutritous.class);

                            if(SelectedNutritous.contains(nutritous.getName())) {
                                idNutritous = nutritous.getId();
                            }

                        }

                        NutritousOnMaterial nom = new  NutritousOnMaterial();
                        nom.setIdMaterial(idMaterial);
                        nom.setIdNutritous(idNutritous);
                        nom.setValue(txtSoLuong.getText().toString());
                        String newNom = NOMREF.push().getKey();
                        nom.setId(newNom);
                        NOMREF.child(newNom).setValue(nom);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void Layve() {
//        NOMREF.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    NutritousOnMaterial nom = dataSnapshot.getValue(NutritousOnMaterial.class);
//                    String idMaterial = nom.getIdMaterial();
//                    List<Material> listnl = new ArrayList<>();
//                    maADRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
//                                Material material = dataSnapshot1.getValue(Material.class);
//                                String idNL = material.getNguyenLieu_id();
//                                if(idNL.matches(idMaterial)){
//                                        listnl.add(material);
//                                }
//                            }
//                            hashSet.addAll(listnl);
//                            listnl.clear();
//                            listnl.addAll(hashSet);
//                            System.out.println(listnl);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        NOMREF.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    NutritousOnMaterial nutritous = dataSnapshot.getValue(NutritousOnMaterial.class);
//                    String idNU  = nutritous.getIdNutritous().toString().trim();
//                    String giaTri = nutritous.getValue().toString();
//                    String rs = idNU.replace("[","").replace("]","").trim();
//
//                    nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
//                                Nutritous nutritous1 = dataSnapshot1.getValue(Nutritous.class);
//                                String idNutrition = nutritous1.getId().trim();
//                                if(idNutrition.equals(rs)){
//                                    System.out.println(nutritous1 + giaTri);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        maADRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Material material = dataSnapshot.getValue(Material.class);
                    String tenNLCanTim = material.getTenNguyenLieu();
                    if (tenNLCanTim.matches(spinnerNL.getSelectedItem().toString())) {
                        idMaterial = material.getNguyenLieu_id();
                        NOMREF.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    NutritousOnMaterial nom = dataSnapshot.getValue(NutritousOnMaterial.class);
                                    String MLID = nom.getIdMaterial();
                                    String tenNl;
                                    if(MLID.matches(idMaterial)){
                                        String idNutritous = nom.getIdNutritous();
                                        String giatri = nom.getValue();
                                        tenNl= material.getTenNguyenLieu();
                                        nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                                    Nutritous nutritous1 = dataSnapshot1.getValue(Nutritous.class);
                                                    String idNutrition = nutritous1.getId().trim();
                                                    if(idNutrition.equals(idNutritous)){
                                                        txtTen.setText(tenNl);
                                                        System.out.println(nutritous1.getName() + ": "+ giatri );
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void LuuDD() {
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int position = 0; position < listView.getCount(); position++) {
                    if(listView.isItemChecked(position)) {
                        SelectedNutritous.add(listView.getItemAtPosition(position).toString());
                    }
                }
              //  System.out.println(SelectedNutritous);
            }
        });
    }

    private void ItemDD() {
        nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Nutritous nutritous = dataSnapshot.getValue(Nutritous.class);
                    String nameNutritous = nutritous.getName();
                    NutritousList.add(nameNutritous);
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,NutritousList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void Showspinner(){
        maADRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Material material = dataSnapshot.getValue(Material.class);
                        String tenNguyenLieulist = material.getTenNguyenLieu().toString().trim();
                        listspinner.add(tenNguyenLieulist);

                    }
                    spinneradapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data !=null){
            imageUri = data.getData();
            imgHinh.setImageURI(imageUri);
        }

    }
    //Up hinh
    private void Uploadtofirebase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                            maADRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                        {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                Material material = dataSnapshot.getValue(Material.class);
                                                if (material.getTenNguyenLieu().equals(spinnerNL.getSelectedItem().toString())) {
                                                    maADRef.child(material.getNguyenLieu_id()).child("hinhNguyenLieu").setValue(uri.toString());
                                                }
                                            }

//                                            nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                                        Nutritous nutritous = dataSnapshot.getValue(Nutritous.class);
//
//                                                        if(SelectedNutritous.contains(nutritous.getName())) {
//                                                            idNutritous = nutritous.getId();
//                                                        }
//
//                                                    }
//
//                                                    NutritousOnMaterial nom = new  NutritousOnMaterial();
//                                                    nom.setIdMaterial(idMaterial);
//                                                    nom.setIdNutritous(idNutritous);
//                                                    nom.setValue(txtSoLuong.getText().toString());
//                                                    String newNom = NOMREF.push().getKey();
//                                                    nom.setId(newNom);
//                                                    NOMREF.child(newNom).setValue(nom);
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                }
//                                            });
                                        }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MaterialAdminActivity.this, "Up hinh that bai", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void AddNguyenLieu(){

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThemDinhDuong();
//                if(imageUri != null){
//
//                    Uploadtofirebase(imageUri);
////                    listspinner.clear();
////                    spinneradapter.notifyDataSetChanged();
////                    spinnerNL.setAdapter(spinneradapter);
////                    Showspinner();
//                }
//                else {
//                    Toast.makeText(MaterialAdminActivity.this, "Chua chon hinh kia", Toast.LENGTH_SHORT).show();
//                }

                ////Dư thêm nguyên liệu mới
//                Material material = new Material();
//                material.setTenNguyenLieu(txtTenDinhDuong.getText().toString().trim());
//                String newid = maADRef.push().getKey();
//                material.setNguyenLieu_id(newid);
//                maADRef.child(newid).setValue(material);
//                nuRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            Nutritous nutritous = dataSnapshot.getValue(Nutritous.class);
//                            if(SelectedNutritous.contains(nutritous.getName())) {
//                                idNutritous = nutritous.getId();
//                            }
//                        }
//                        NutritousOnMaterial nom = new  NutritousOnMaterial();
//                        nom.setIdMaterial(material.getNguyenLieu_id());
//                        nom.setIdNutritous(idNutritous);
//                        nom.setValue(txtSoLuong.getText().toString());
//                        String newNom = NOMREF.push().getKey();
//                        nom.setId(newNom);
//                        NOMREF.child(newNom).setValue(nom);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
     });

    }

    private void TimNguyenLieu(){
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Layve();
               // System.out.println(spinnerNL.getSelectedItem().toString());

//                maADRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            materialList.clear();
//                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                Material material= dataSnapshot.getValue(Material.class);
//                                String tenNguyeLieu= material.getTenNguyenLieu().toString();
//                                if (tenNguyeLieu.trim().matches(spinnerNL.getSelectedItem().toString())){
//                                    materialList.add(dataSnapshot.getValue(Material.class));
//                                    txtTenDinhDuong.setText(material.getTenNguyenLieu());
//
//                                //    System.out.println(materialList);
//
//                                }
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


            }
        });
    }

    private void init(){

        txtTenDinhDuong = (EditText)findViewById(R.id.txttenNguyenLieu);

        txtSoLuong=(EditText) findViewById(R.id.txtSoLuong);
        txtTen= findViewById(R.id.txtTest);
        btnPush=findViewById(R.id.btnpushMaterial);
        btnTim=findViewById(R.id.btnTimMaterial);
        btnluu=findViewById(R.id.btnLuuid);
       imgHinh=findViewById(R.id.imgHinhNguyenLieu);
        listView = (ListView) findViewById(R.id.listiD);
       spinnerNL=findViewById(R.id.spnList);
    }
}