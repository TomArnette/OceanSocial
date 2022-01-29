package com.revature.feed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * The like model is used to create the Like object. The like object will contain the following data:
 *  -likeId
 *  -userId
 *  -postId
 *
 * Lombok is used to reduce repetitive code. The lombok annotations used in this class are the following:
 *  -@AllArgsConstructor
 *  -@NoArgsConstructor
 *  -@Data
 *
 * Hibernate annotations are used to create the database table (likes table). The hibernate annotations used are the following:
 *  -@Entity
 *  -@Table
 *  -@Id
 *  -@Column
 *  -@GeneratedValue
 *  -@ManyToOne
 *  -@JoinColumn
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="likes")
public class Like {

    @Id
    @Column(name="likeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @Column(name="userId", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name="postId", nullable = false)
    private Post post;
}
