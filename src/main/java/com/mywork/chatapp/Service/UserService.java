package com.mywork.chatapp.Service;

import com.mywork.chatapp.Exception.UserNotFoundException;
import com.mywork.chatapp.Model.Group;
import com.mywork.chatapp.Model.User;
import com.mywork.chatapp.Repository.UserRepository;
import com.mywork.chatapp.Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) throws NoSuchAlgorithmException, IOException {
        // encrpyt password here
        user.setPassword(PasswordUtil.EncrpytPassword(user.getPassword()));

        User tmp = userRepository.findByUserName(user.getUserName());
        if(tmp != null){
            return null;
        }
        return userRepository.save(user);
    }

    public User getUser(int userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User with "+userId +" not Exist in DB.");
        }
        return user.get();
    }

    public User updateUser(User user) throws UserNotFoundException {
        Optional<User> user1 = userRepository.findById(user.getId());
        if(!user1.isPresent()){
            throw new UserNotFoundException("User with "+user.getId()+" not Exist in DB.");
        }

        return userRepository.save(user);
    }

    public void deleteUser(int userid){
        userRepository.deleteById(userid);
    }


    public User validateUser(String username , String password) throws NoSuchAlgorithmException, IOException {
        User user = userRepository.findByUserName(username);
        if(user!=null && PasswordUtil.EncrpytPassword(password).equals(user.getPassword())){
            return user;
        }

        return null;
    }


}
