package com.mywork.chatapp.Controller;

import com.mywork.chatapp.Repository.MessageRepository;
import com.mywork.chatapp.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @Autowired
    MessageRepository messageRepository;


    /*
    *   @Param data :  message id
    *   @Return : null
    *   It is using message id to delete message.
    *
    * */
    @Transactional
    @PostMapping("/message/delete")
    void deleteMessage(@Payload Integer data){
        System.out.println(data);
        try {

            messageRepository.deleteMessageById(data);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
