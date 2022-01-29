package com.revature.ocean.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User model used to create User objects.
 * Lombok is used to reduce boilerplate code.
 * Hibernate annotations are used to create the database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "aboutMe")
    private String aboutMe;

    @Column(name = "bday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy") //Jackson to format our birthday
    private Date bday;

    @Column(name = "proPicUrl")
    private String proPicUrl;

    @Column(name = "user_last_notification")
    private Long lastNotification;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> bookmarks;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> user_following;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> followers;

    //constructor for login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Modified Constructor
    public User(String username, String password, String email, String firstName, String lastName, Date bday, String proPicUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bday = bday;
        this.proPicUrl = proPicUrl;
       /* this.postList = postList;
        this.commentList = commentList;*/
    }

    //Modified Constructor
    public User(String username, String password, String email, String firstName, String lastName, Date bday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bday = bday;
    }
}
