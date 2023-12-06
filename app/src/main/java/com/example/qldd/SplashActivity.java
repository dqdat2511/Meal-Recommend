package com.example.qldd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.qldd.Adapter.MarterialAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    GoogleSignInAccount signInAccount;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    DatabaseReference ageRef = database.getReference("Ages");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        if( user == null){
            Intent intent = new Intent (this, SignInActivity.class);
            startActivity(intent);

        }else {
            accRef.child(signInAccount.getId()).child("date").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            Intent khaibao = new Intent(getApplicationContext(), KhaiBaoActivity.class);
                            startActivityForResult(khaibao , 0);
                        }
                        else {
                            Intent NL = new Intent(getApplicationContext(), MainActivity.class);
                            startActivityForResult(NL, 0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }
    }
}