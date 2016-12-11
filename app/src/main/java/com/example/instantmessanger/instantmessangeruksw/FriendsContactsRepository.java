package com.example.instantmessanger.instantmessangeruksw;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil Siwak on 2016-12-11.
 */
public class FriendsContactsRepository {
    private static FriendsContactsRepository friendsContactRepository;
    private List<FriendDTO> contacts;

    public static FriendsContactsRepository get(Context context)
    {
        if(friendsContactRepository == null)
        {
            friendsContactRepository = new FriendsContactsRepository(context);
        }
        return  friendsContactRepository;
    }

    private FriendsContactsRepository(Context context)
    {
        contacts = new ArrayList<>();
        createFriendsContactsListOnInitial(context);

    }

    private void createFriendsContactsListOnInitial(Context context) {
        //Create the Foods and add them to the list;


        FriendDTO contact1 = new FriendDTO("uksw_client1@xmpp.jp", "Client1");
        contacts.add(contact1);
        FriendDTO contact2 = new FriendDTO("uksw_client2@xmpp.jp", "Client2");
        contacts.add(contact2);
    }

    public List<FriendDTO> getFriendsContacts()
    {
        return contacts;
    }

}
