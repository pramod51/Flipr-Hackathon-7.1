package com.example.payo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private TextView name,address,phone,email;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.name);
        progressBar=view.findViewById(R.id.progress_horizontal);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        email=view.findViewById(R.id.email);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        progressBar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();
        ref.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null)
                {
                    String s=dataSnapshot.child("Name").getValue(String.class);
                    name.setText(s);
                    address.setText(dataSnapshot.child("Address").getValue(String.class));
                    phone.setText(dataSnapshot.child("Phone").getValue(String.class));
                    email.setText(dataSnapshot.child("Email").getValue(String.class));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
