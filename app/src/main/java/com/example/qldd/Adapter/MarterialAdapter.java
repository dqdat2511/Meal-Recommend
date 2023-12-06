package com.example.qldd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qldd.ChiTietDinhDuongActivity;
import com.example.qldd.Object.Material;
import com.example.qldd.Object.NutritousOnMaterial;
import com.example.qldd.Object.PhieuDiCho;
import com.example.qldd.Object.User;
import com.example.qldd.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarterialAdapter extends RecyclerView.Adapter<MarterialAdapter.MarterialViewHolder>{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference NOMREF = database.getReference("NutritousOnMaterial");
    DatabaseReference accRef = database.getReference("Account");
    DatabaseReference marketRef;
    DatabaseReference DichoRef = database.getReference("PhieuDiCho");
    GoogleSignInAccount signInAccount;

    GoogleSignInClient mGoogleSignInClient;


   private List<Material> materialList = new ArrayList<>();
    private Context mContext;


//    signInAccount = GoogleSignIn.getLastSignedInAccount(this);
    public MarterialAdapter(List<Material> materialList, Context mContext) {
        this.materialList = materialList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MarterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(mContext.getApplicationContext(), gso);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marterial_item, parent, false);
        return new MarterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarterialViewHolder holder, int position) {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(mContext.getApplicationContext());
        marketRef = database.getReference("Account/" + signInAccount.getId() +"/PhieuDiCho");
//        NutritousOnMaterial item = nomList.get(position);
//        if(item.getIdNutritous().matches("-N4XYPdfxcFoMqMGMh4M")){
//            String idNL = item.getIdMaterial();
//            holder.tvGiaTriDinhDuong.setText(item.getValue());
//            maADRef.child(idNL).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if(snapshot.exists()){
//                        Material material = snapshot.getValue(Material.class);
//                        holder.tvTenNguyenLieu.setText(material.getTenNguyenLieu());
//                        holder.tvXemChiTiet.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(mContext, ChiTietDinhDuongActivity.class);
//                                intent.putExtra("id-NL", item.getIdMaterial());
//                                mContext.startActivity(intent);
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

        Material item = materialList.get(position);
        holder.tvTenNguyenLieu.setText("Tên nguyên liệu: " + item.getTenNguyenLieu());
        Glide.with(mContext).load(materialList.get(position).getHinhNguyenLieu()).into(holder.imgNguyenLieu);
        String idMT = item.getNguyenLieu_id();
        NOMREF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    NutritousOnMaterial numo = dataSnapshot.getValue(NutritousOnMaterial.class);
                    String idNl = numo.getIdMaterial();
                    if(idNl.matches(idMT)){
                        if(numo.getIdNutritous().matches("-N4XYPdfxcFoMqMGMh4M")){
                            holder.tvGiaTriDinhDuong.setText("Năng lượng: "+ numo.getValue());
                            holder.tvXemChiTiet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ChiTietDinhDuongActivity.class);
                                intent.putExtra("id-NL", item.getNguyenLieu_id());
                                mContext.startActivity(intent);
                            }
                        });
                            holder.btnThemvaoHang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    PhieuDiCho phieuDiCho = new PhieuDiCho();
                                    phieuDiCho.setId_nguyenLieu(item.getNguyenLieu_id());
                                    phieuDiCho.setId_user(signInAccount.getId());
                                    String id = DichoRef.push().getKey();
                                    phieuDiCho.setId_Phieu(id);
                                    marketRef.child(id).setValue(phieuDiCho);



                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public int getItemCount() {
        if (materialList != null){
            return materialList.size();
        }
        return 0;
    }

    public class MarterialViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgNguyenLieu, btnThemvaoHang;
        private TextView tvTenNguyenLieu, tvGiaTriDinhDuong, tvXemChiTiet;

        public MarterialViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNguyenLieu = itemView.findViewById(R.id.ivNguyenLieu);
            tvTenNguyenLieu = itemView.findViewById(R.id.txtTenChat);
            tvGiaTriDinhDuong = itemView.findViewById(R.id.txtGiatriDinhDuong);
            tvXemChiTiet= itemView.findViewById(R.id.tvXemThem);
            btnThemvaoHang=itemView.findViewById(R.id.btnGioHang);
        }
    }
}
