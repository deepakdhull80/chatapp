package com.mywork.chatapp.Repository;

import com.mywork.chatapp.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select u from User u where u.userName=?1")
    public User findByUserName(String userName);
}
