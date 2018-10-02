package e.purab.twitterc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Preview extends AppCompatActivity {
    private RecyclerView preview;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;
    private DatabaseReference messagesDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        preview = findViewById(R.id.preview);
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(tweets);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        preview.setLayoutManager(manager);
        preview.setAdapter(adapter);
        username = getIntent().getStringExtra("username");
        Log.d("OGIL", username);
        populate();
    }

    private void populate(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("messages");
        messagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    Message m = item_snapshot.getValue(Message.class);
                    if(m.getReciever() != null && m.getReciever().equals(username)){
                        tweets.add(new Tweet(m.getMessage(), m.getAuthor()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
