package com.mywork.chatapp.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "group_name")
    String groupName;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "groups")
    Set<User> users;

    @OneToMany(mappedBy = "groupN",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Message> messages;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
