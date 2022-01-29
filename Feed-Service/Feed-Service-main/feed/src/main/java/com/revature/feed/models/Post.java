package com.revature.feed.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * The post model is used to create the Post object. The post object will contain the following data:
 *  -postId
 *  -postParentId
 *  -postPicUrl
 *  -postText
 *
 * Lombok is used to reduce repetitive code. The lombok annotations used in this class are the following:
 *  -@Data
 *  -@NoArgsConstructor
 *  -@AllArgsConstructor
 *
 * Hibernate annotations are used to create the database table (posts table). The hibernate annotations used are the following:
 *  -@Entity
 *  -@Table
 *  -@Id
 *  -@Column
 *  -@GeneratedValue
 *  -@CreationTimestamp
 *
 * The @JsonFormat annotation from the Jackson library was used to give the format need to the postTime variable.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts")
public class Post {

    @Id
    @Column(name="postId")
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer postId;

    @Column(name="postParentId")
    private Integer postParentId;

    @Column(name="postPicUrl")
    private String postPicUrl;

    @Column(name="postText", nullable=false)
    private String postText;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date postTime;

    @Column(name="postYouUrl")
    private String postYouUrl;

    @Column(name="userId", nullable = false)
    private Integer userId;

}