package com.example.instantmessanger.instantmessangeruksw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FriendsListActivity extends AppCompatActivity {

    private RecyclerView friendsContactsRecyclerView;
    private FriendsListAdapter friendsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        friendsContactsRecyclerView = (RecyclerView) findViewById(R.id.friends_list);
        friendsContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        FriendsContactsRepository repository = FriendsContactsRepository.get(getBaseContext());
        List<FriendDTO> contacts = repository.getFriendsContacts();

        friendsListAdapter = new FriendsListAdapter(contacts);
        friendsContactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friendsContactsRecyclerView.setAdapter(friendsListAdapter);


    }

    private class FriendContactHolder extends RecyclerView.ViewHolder
    {
        private TextView contactTextView;
        private FriendDTO friendContact;
        public FriendContactHolder ( View itemView)
        {
            super(itemView);

            contactTextView = (TextView) itemView.findViewById(R.id.friend_contact_name);

            contactTextView .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("FriendsListActivity", "contact clicked");
                    //TODO //IMPLEMENT SEND AND RECIVE ACTIVITY AND RUN IT
                }
            });
        }


        public void bindFriendContactToView( FriendDTO contact)
        {
            friendContact = contact;
            if (friendContact == null)
            {
                Log.d("FriendsListActivity","The contact to friend is null.");
                return;
            }
            contactTextView.setText(friendContact.getFriendName());

        }
    }


    private class FriendsListAdapter extends RecyclerView.Adapter<FriendContactHolder>
    {
        private List<FriendDTO> friendsContacts;

        public FriendsListAdapter( List<FriendDTO> friendsContacts)
        {
           this.friendsContacts = friendsContacts;
        }

        @Override
        public FriendContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.activity_friend_contact, parent,
                            false);
            return new  FriendContactHolder(view);
        }

        @Override
        public void onBindViewHolder(FriendContactHolder holder, int position) {
            FriendDTO contact = friendsContacts.get(position);
            holder.bindFriendContactToView(contact);

        }

        @Override
        public int getItemCount() {
            return friendsContacts.size();
        }
    }
}
