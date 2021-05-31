package com.mywork.chatapp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "user",uniqueConstraints = @UniqueConstraint(columnNames = {"user_name"}))
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int id;
    @Column(name = "user_name")
    String userName;
    @Column(name = "password")
    String password;
    @Column(name = "img_src")
    String imageSrc;

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(name = "user_group",
                        joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    Set<Group> groups;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    Set<Message> messages;


    public User(String userName, String password, String imageSrc) {
        this.userName = userName;
        this.password = password;
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
