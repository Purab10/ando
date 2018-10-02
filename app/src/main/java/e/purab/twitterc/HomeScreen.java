package e.purab.twitterc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private RecyclerView users;
    private UsersAdapter usersAdapter;
    private List<User> usersList;
    private DatabaseReference messagesDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDbReference;
    private SharedPreferences pref;

    public static final String  PREF_NAME = "OGIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        users = findViewById(R.id.users);
        usersList = new ArrayList<>();
        usersAdapter = new UsersAdapter(usersList, new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isTweet) {
                if(isTweet){
                    showTweetUI(usersList.get(position).getUsername());
                } else {
                    Intent intent = new Intent(getApplicationContext(), Preview.class);
                    intent.putExtra("username", usersList.get(position).getUsername());
                    startActivity(intent);
                }
            }
        });
        users.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        users.setAdapter(usersAdapter);
        setUsers();
        checkUserExists();
    }

    private void checkUserExists(){
        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String user = pref.getString("username", null);
        if(user==null){
            askUsername();
        }
    }

    private void askUsername(){
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_user, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        final AlertDialog alertDialog = dialogBuilder.setCancelable(true).setView(dialogView).create();
        alertDialog.show();
        final EditText editText = dialogView.findViewById(R.id.username);
        final Button send = dialogView.findViewById(R.id.save);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.edit().putString("username", editText.getText().toString().trim()).apply();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                alertDialog.hide();
            }
        });
    }

    private void showTweetUI(final String receiver){
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tweet, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        final AlertDialog alertDialog = dialogBuilder.setCancelable(true).setView(dialogView).create();
        alertDialog.show();
        final EditText editText = dialogView.findViewById(R.id.tweet);
        final Button send = dialogView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssg = editText.getText().toString().trim();
                if(mssg.length() > 0) {
                    Message message = new Message(mssg, pref.getString("username", "null"), receiver);
                    messagesDatabaseReference.push().setValue(message);
                    alertDialog.hide();
                    Toast.makeText(getApplicationContext(), "Tweeted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUsers(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDbReference = firebaseDatabase.getReference().child("users");
        messagesDatabaseReference = firebaseDatabase.getReference().child("messages");
        usersDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    usersList.add(new User(""+item_snapshot.getKey(), ""+item_snapshot.getValue()));
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
