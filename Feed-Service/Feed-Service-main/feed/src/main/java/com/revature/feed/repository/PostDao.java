package com.revature.feed.repository;

import com.revature.feed.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("postDao")
@Transactional
public interface PostDao extends JpaRepository<Post, Integer> {

    //Custom Query to get the Post by the UserId
    @Query("from Post where userId = :userId")
    List<Post> getPostByUserId(@Param("userId") Integer userId);

    //Custom Query to get all posts by ParentId
    @Query("from Post where postParentId = :parentId")
    List<Post> getPostByParentId(Integer parentId);

    @Query("from Post where postParentId = :postId")
    void deleteByParentId(Integer postId);
}
