package com.mywork.chatapp.Exception;

public class GroupNotFoundException extends Exception{
    public GroupNotFoundException(){
        super();
    }
    public GroupNotFoundException(String msg){
        super(msg);
    }
}
