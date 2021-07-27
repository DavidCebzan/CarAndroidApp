package com.example.proiect.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.Fragments.ProfileFragment;
import com.example.proiect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.*;
import static android.widget.Toast.makeText;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);

        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

      final Post post= mPosts.get(position);

        Picasso.get().load(post.getImageurl()).into(holder.post_image);
        holder.description.setText(post.getDescription());
        holder.make.setText(post.getMake());
        holder.model.setText(post.getModel());
        holder.price.setText(post.getPrice());
        holder.year.setText(post.getYear());
        holder.color.setText(post.getColor());

        FirebaseDatabase.getInstance().getReference().child("Users").child(post.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user.getImageurl().equals("default")) {
                    holder.imageProfile.setImageResource(R.drawable.ic_pentruregister_foreground);
                } else {
                    Picasso.get().load(user.getImageurl()).placeholder(R.drawable.ic_pentruregister_foreground).into(holder.imageProfile);
                }
                holder.username.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        isSaved(post.getPostid(),holder.save);
        //pt save
        holder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                        .edit().putString("profileId", post.getPublisher()).apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                        .edit().putString("profileId", post.getPublisher()).apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostid()).setValue(true);

                }else{
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostid()).removeValue();

                }
            }
        });

        if(post.getPublisher().equals(firebaseUser.getUid())){
            holder.more.setVisibility(View.VISIBLE);
        }else{
            holder.more.setVisibility(View.GONE);
        }

        final String[] postId = new String[1];

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteCar(post.getPostid());
            }
        });

    }

    private void deleteCar(String postId){
        DatabaseReference postCar = FirebaseDatabase.getInstance().getReference("Posts").child(postId);
        postCar.removeValue();

        makeText(mContext,"Post deleted", LENGTH_SHORT).show();
    }



    private void isSaved(final String postId, final ImageView image) {
        FirebaseDatabase.getInstance().getReference().child("Saves").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postId).exists()){
                    image.setImageResource(R.drawable.ic_save_black_foreground);
                    image.setTag("saved");
                }else {
                    image.setImageResource(R.drawable.ic_save_foreground);
                    image.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageProfile,post_image,save,more;

        public TextView username,Make_text;

        SocialTextView description,make,model,color,year,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile=itemView.findViewById(R.id.profile_image);
            post_image=itemView.findViewById(R.id.post_image);
            save=itemView.findViewById(R.id.save);
            more=itemView.findViewById(R.id.more);
            username=itemView.findViewById(R.id.username);

            description=itemView.findViewById(R.id.Description11);
            make=itemView.findViewById(R.id.make);
            model=itemView.findViewById(R.id.model);
            color=itemView.findViewById(R.id.color);
            year=itemView.findViewById(R.id.year);
            price=itemView.findViewById(R.id.price);
            Make_text=itemView.findViewById(R.id.Text_make);






        }


    }
}
