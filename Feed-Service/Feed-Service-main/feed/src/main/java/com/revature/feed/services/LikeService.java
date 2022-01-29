package com.revature.feed.services;

import com.revature.feed.models.Like;
import com.revature.feed.repository.LikeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * The like service handles all the operations that have to do with likes. The service can create a like, delete a like
 * and send to the frontend the list of likes that a post has.
 */

@Service("likeService")
public class LikeService {
    private LikeDao likeDao;

    @Autowired
    public LikeService(LikeDao likeDao){this.likeDao = likeDao;}

    @Autowired
    private PostService postService;

    /**
     * Method that is called when a user clicks on the like button and creates a new like associated with that post.
     * @param like  like object that is passed to the method from the like controller.
     * @return      returns the like object.
     */
    public Like createLike(Like like) {
        like.setPost(this.postService.getPostById(like.getPost().getPostId()));
        return this.likeDao.save(like);
    }

    /**
     * Method that retrieves the list of all likes from a specific post when a postId is passed to the method.
     * @param postId    post id of the post that we want to retrieve the likes from.
     * @return          returns the list of likes of that specific post.
     */
    public List<Like> getLikeByPostId(Integer postId) {
        return this.likeDao.getLikesByPostId(postId);
    }


    /**
     * Method that deletes the like when past the like id.
     * @param likeId    likeId of the like that we want to delete.
     * @return true     returns true if the like was successfully deleted.
     * @return false    returns false if the like was not deleted
     */
    public Boolean deleteLike(Integer likeId) {
        boolean checkDelete = this.likeDao.existsById(likeId);
        if (checkDelete){
            this.likeDao.deleteById(likeId);
            return true;
        }
        return false;
    }
}