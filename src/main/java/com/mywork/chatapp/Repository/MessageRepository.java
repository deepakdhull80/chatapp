package com.mywork.chatapp.Repository;

import com.mywork.chatapp.Model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message,Integer> {

    public Iterable<Message> findAllByGroupN(int groupId);

}
