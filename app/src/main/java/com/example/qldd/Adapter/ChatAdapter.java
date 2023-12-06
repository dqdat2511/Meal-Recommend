package com.example.qldd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldd.Object.Nutritous;
import com.example.qldd.Object.NutritousOnMaterial;
import com.example.qldd.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private List<NutritousOnMaterial> nutritousOnMaterialsList = new ArrayList<>();
    private Context mContext;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference maADRef = database.getReference("Material");
    DatabaseReference NOMREF = database.getReference("NutritousOnMaterial");
    DatabaseReference nuRef = database.getReference("Nutritous");

    public ChatAdapter(List<NutritousOnMaterial> nutritousOnMaterialsList, Context mContext) {
        this.nutritousOnMaterialsList = nutritousOnMaterialsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chitietdinhduong_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        NutritousOnMaterial item = nutritousOnMaterialsList.get(position);
        //holder.tvTenChat.setText(item.getIdMaterial());
        String idNU = item.getIdNutritous();
        nuRef.child(idNU).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Nutritous nutritous = snapshot.getValue(Nutritous.class);
                holder.tvTenChat.setText(nutritous.getName());
                holder.tvGiaTri.setText(item.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (nutritousOnMaterialsList != null){
            return nutritousOnMaterialsList.size();
        }
        return 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenChat, tvGiaTri;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenChat = itemView.findViewById(R.id.txtTenChat);
            tvGiaTri = itemView.findViewById(R.id.txtGiatriDinhDuong);
        }
    }
}
