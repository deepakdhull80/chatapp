package com.mywork.chatapp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Message {

    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String message;
    Date messageDate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    Group groupN;


}
