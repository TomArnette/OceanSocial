package com.revature.ocean.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Zimi Li
 * Notification model used to create Notification objects.
 * Lombok is used to reduce boilerplate code.
 * Hibernate annotations are used to create the database table.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    @JsonIgnore
    private Integer id;

    @Column(name = "notification_type", nullable = false)
    private String type;

    @Column(name = "notification_timestamp", nullable = false)
    private Long timestamp;

    @ManyToOne(cascade = CascadeType.MERGE)
    //@JsonIgnore
    private User userBelongTo;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User userFrom;

    @Column(name = "notification_feedid")
    private Integer feedId;

    @Transient
    private Response response;

    @Transient
    private UserResponse userResponse;
}
