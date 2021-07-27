package com.example.proiect.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.Fragments.ProfileFragment;
import com.example.proiect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CarAdapter extends  RecyclerView.Adapter<CarAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mPosts;
    private List<String> carcount;
    private FirebaseUser firebaseUser;

    public CarAdapter(Context mContext, List<String> mPosts,List<String> carcount) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.carcount=carcount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.carmodel,parent,false);

        return new CarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        //asta
        holder.make.setText(mPosts.get(position));
        //asta am adugat dupaia
        holder.no_make.setText(carcount.get(position));

        holder.make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Make").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView make,no_make;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            make=itemView.findViewById(R.id.Make123);
            no_make=itemView.findViewById(R.id.Model123);
        }
    }
}
