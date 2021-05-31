package com.mywork.chatapp.Service;

import com.mywork.chatapp.Model.Message;
import com.mywork.chatapp.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    public Message getMessage(int messageId){
        return messageRepository.findById(messageId).get();
    }

    public Iterable<Message> getMessageByGroupId(int groupId){
        return messageRepository.findAllByGroupN(groupId);
    }

    public void deleteMessage(int messageId){
        messageRepository.deleteById(messageId);
    }


}
