package com.mywork.chatapp.Repository;

import com.mywork.chatapp.Model.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends CrudRepository<Message,Integer> {

    public Iterable<Message> findAllByGroupN(int groupId);

    @Transactional
    @Query(value = "delete from message where id=?1",nativeQuery = true)
    @Modifying
    public void deleteMessageById(int messageId);

}
