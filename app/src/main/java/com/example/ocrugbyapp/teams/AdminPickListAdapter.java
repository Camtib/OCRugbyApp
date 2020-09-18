package com.example.ocrugbyapp.teams;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.MensFixtureCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.Document;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminPickListAdapter extends RecyclerView.Adapter<AdminPickListAdapter.PlayersVH> {

    private Context mContext;
    List<TeamPlayer> playerList;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userID;
    StorageReference mStorageRef;
    Uri profilePic;

    public AdminPickListAdapter(Context mContext, List<TeamPlayer> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;

    }

    @NonNull
    @Override
    public AdminPickListAdapter.PlayersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_members, parent, false);
        return new PlayersVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPickListAdapter.PlayersVH holder, int position) {

        TeamPlayer player = playerList.get(position);
        userID = player.getUserID();

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        DocumentReference documentReference = mStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    holder.name.setText(documentSnapshot.get("Name").toString());
                    holder.position.setText(documentSnapshot.get("PreferredPosition").toString());
                }
            }
        });

        StorageReference storageReference = mStorageRef.child("users/"+userID+"/profile.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profilePic = uri;
                Picasso.get().load(profilePic).into(holder.profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayersVH extends RecyclerView.ViewHolder {

        TextView name, position;
        ImageView profileImage;

        public PlayersVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            position = itemView.findViewById(R.id.textViewNickname);
            profileImage = itemView.findViewById(R.id.profilePics);
        }
    }
}
