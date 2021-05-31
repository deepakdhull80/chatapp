package com.mywork.chatapp.Exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(String msg){
        super(msg);
    }

}
