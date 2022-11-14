package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Adapter.MessageAdapter;
import com.example.myapplication.Model.Message;
import com.example.myapplication.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Consult extends Fragment {
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    MessageAdapter messageAdapter;
    Users users;
    List<Message> mMessage;
    private RecyclerView chatView;
    private EditText chatText;
    private ImageButton sendBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consult, container, false);

        chatView = view.findViewById(R.id.chatView);
        chatText = view.findViewById(R.id.chatText);
        sendBtn = view.findViewById(R.id.sendBtn);

        init();

        return view;

    }

    public void init(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        users = new Users();
        mMessage = new ArrayList<>();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(chatText.getText().toString())){
                    Message message = new Message(chatText.getText().toString(), users.getNama(), users.getProfImage());
                    chatText.setText("");
                    reference.push().setValue(message);
                }else{
                    Toast.makeText(getContext(), "Message Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        users.setId(currentUser.getUid());
        users.setEmail(currentUser.getEmail());
        users.setNama(currentUser.getDisplayName());

        mDatabase.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = snapshot.getValue(Users.class);
                users.setId(currentUser.getUid());
                GlobalVariable.name = users.getNama();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = mDatabase.getReference("chatGroup");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                mMessage.add(message);
                displayMessage(mMessage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                List<Message> newMessage = new ArrayList<Message>();
                for(Message m : mMessage){
                    if(m.getKey().equals(message.getKey())){
                        newMessage.add(message);
                    }else{
                        newMessage.add(m);
                    }
                }
                mMessage = newMessage;
                displayMessage(mMessage);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                List<Message> newMessage = new ArrayList<Message>();
                for(Message m : mMessage){
                    if(!m.getKey().equals(message.getKey())){
                        newMessage.add(m);
                    }
                }
                mMessage = newMessage;
                displayMessage(mMessage);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void displayMessage(List<Message> message){
        chatView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(getContext(), message, reference);
        chatView.setAdapter(messageAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMessage = new ArrayList<>();
    }
}