package com.example.instantmessanger.instantmessangeruksw;

/**
 * Created by Kamil Siwak on 2016-12-11.
 */
public class FriendDTO {
    private String friendID;
    private String friendName;

    public FriendDTO (String friendID, String friendName){
        this.friendID=friendID;
        this.friendName=friendName;
    }

    public String getFriendID(){
        return this.friendID;
    }

    public String getFriendName(){
        return this.friendName;
    }

    public void setFriendID(String friendID){
        this.friendID=friendID;
    }

    public void setFriendName(String friendName){
        this.friendName=friendName;
    }
}
