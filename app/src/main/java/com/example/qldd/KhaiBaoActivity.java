package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class KhaiBaoActivity extends AppCompatActivity {
    EditText EdtDate,txtHoTen;
    RadioGroup RdnGioiTinh,rdnMucDo;
    RadioButton selectedSex, selectedMucDo,rdnNam,rdnNu;
    Button btnLamMoi,btnLuu,btnCapnhat;
    GoogleSignInAccount signInAccount;
    Toolbar toolbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        init();
        //setSupportActionBar(toolbar);
//        toolbar.setTitle("Khai báo thông tin");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent main = new Intent(KhaiBaoActivity.this, MainActivity.class);
//                startActivityForResult(main, 1);
//                finish();
//            }
//        });
        getProfile();
        EdtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    ChonNgay();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        RdnGioiTinh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                selectedSex = (RadioButton) findViewById(selectedId);
                //  System.out.println(selectedSex.getText());
            }
        });
        rdnMucDo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedMDId = radioGroup.getCheckedRadioButtonId();
                selectedMucDo =(RadioButton) findViewById(selectedMDId);
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdtDate.setText("");
                txtHoTen.setText("");
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RdnGioiTinh.getCheckedRadioButtonId() == -1 || rdnMucDo.getCheckedRadioButtonId() == -1){
                    Toast.makeText(KhaiBaoActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    Pushdata();}
                Intent main = new Intent( KhaiBaoActivity.this, MainActivity.class);
                Toast.makeText(KhaiBaoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                startActivityForResult(main , 1);
                finish();


            }
        });
    }
    private void Pushdata() {
        accRef.child(signInAccount.getId()).child("hoTen").setValue(txtHoTen.getText().toString());
        accRef.child(signInAccount.getId()).child("sex").setValue(selectedSex.getText().toString());
        accRef.child(signInAccount.getId()).child("date").setValue(EdtDate.getText().toString());
       // accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").setValue(selectedMucDo.getText());
        Toast.makeText(KhaiBaoActivity.this, "Update thành công", Toast.LENGTH_LONG).show();

    }
    private void getProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.getValue().toString();
                    txtHoTen.setText(name);
                }else
                {
                    txtHoTen.setText(signInAccount.getDisplayName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String date= snapshot.getValue().toString();
                    EdtDate.setText(date);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("sex").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    System.out.println(snapshot.getValue().toString());
                    if(snapshot.getValue().equals("Nữ")){
                        rdnNu.setChecked(true);
                    }else {
                        rdnNam.setChecked(true);}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang =calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(KhaiBaoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormate = new SimpleDateFormat("dd/MM/yyyy");

                EdtDate.setText(simpleDateFormate.format(calendar.getTime()));

            }
        }, nam,thang,ngay);
        datePickerDialog.show();
    }
    private void init(){
        EdtDate = (EditText) findViewById(R.id.edtDate);
        RdnGioiTinh=(RadioGroup) findViewById(R.id.rdnGioiTinh);
        rdnMucDo=(RadioGroup) findViewById(R.id.rdnMucDo);
        btnLamMoi=(Button) findViewById(R.id.btnlammoi);
        btnLuu=(Button) findViewById(R.id.btnLuu);
        txtHoTen =(EditText) findViewById(R.id.txtTen);
        btnCapnhat=(Button) findViewById(R.id.btnbenhly);
        rdnNam=(RadioButton) findViewById(R.id.rdnNam);
        rdnNu=(RadioButton) findViewById(R.id.RdnNu);

    }
}