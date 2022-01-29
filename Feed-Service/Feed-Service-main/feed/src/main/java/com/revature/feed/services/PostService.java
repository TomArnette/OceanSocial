package com.revature.feed.services;

import com.revature.feed.models.Post;
import com.revature.feed.repository.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * The Post service handles all the operations that have to do with posts and comments. The service can create a post/comment, update a post/comment,
 * get a post/comment by id, as well as all post from a users "Favorite" list, delete a post/comment
 * A message will be sent to user service when a comment on a post has been created so they can notify the user of the comment.
 */
@Service("postService")
public class PostService {
    private PostDao postDao;

    @Autowired
    public PostService(PostDao postDao){this.postDao = postDao;}

    /**
     * Method that is called when a user creates a post or comment.
     * @param post  post object that is passed to the method from the post controller.
     * @return      returns the post object.
     */
    public Post createPost(Post post) {
        return this.postDao.save(post);
    }

    /**
     * Method that is called to return 20 posts at a time from the user's list of "Favorites".
     * "Favorite" list is the users that "this" user is following. The list of "Favorites" is retrieved from
     * the user service side via RabbitMQ message.
     * @param page  The page number being requested, pages only contain 20 posts at a time.
     * @param fave  This is a list of userIds passed from the user service.
     * @return      returns the 20 posts requested in order of newest to oldest until the page number and number of posts run out.
     */
    //Post for the favorite
    public List<Post> selectPostForFav(Integer page, List<Integer> fave ) {
        List<Post> fullListPost = new ArrayList<>();
        for (int i = 0; i < fave.size(); i++) {
            List<Post> userPost = this.postDao.getPostByUserId(fave.get(i));
            List<Post> filteredPosts = userPost.stream()
                    .filter(x -> x.getPostParentId() == null)
                    .collect(Collectors.toList());
            fullListPost.addAll(filteredPosts);
        }
        Collections.sort(fullListPost, Comparator.comparingInt(Post::getPostId).reversed());
        Double checkPages = Double.valueOf(fullListPost.size()) / 20;
        int num = (int) (Math.ceil(checkPages));
        if (page <= num) {
            Integer pageEnd = page *20 -1;
            Integer offset = page * 20 - 20;
            List<Post> pagePost = new ArrayList<>();
            for (int j = offset; j < pageEnd; j++) {
                if (fullListPost.size() == j) {
                    break;
                } else {
                    pagePost.add(fullListPost.get(j));
                }
            }
            return pagePost;
        }else{
            return null;
        }
    }

    /**
     * Method that is called to return all comments made on a post.
     * PostParentID is id of the original post that this comment is made on.
     * This will be called as well to return comments on a comment.
     * @param parentId  id of parent post that is passed to the method from the post controller.
     * @return      returns a list of comments made on that post/comment.
     */
    public List<Post> getAllParentId(Integer parentId){
        List<Post> commentList = this.postDao.getPostByParentId(parentId);
        Collections.sort(commentList, Comparator.comparingInt(Post::getPostId).reversed());
        return commentList;
    }

    /**
     * Method that is called to find a post/comment by its id.
     * @param postId  post id that is passed to the method from the post controller.
     * @return      returns the post object or null if its not found.
     */
    public Post getPostById(Integer postId) {
        return this.postDao.findById(postId).orElse(null);
    }

    /**
     * Method that is called to get all post by userId, for when you click on a particular user's feed to see all their posts.
     * This will return only parent posts and order them from newest to oldest.
     * @param userId  userId that is passed to the method from the post controller.
     * @param pageNumber page number requested from front end.
     * @return      returns the list of parent posts, 20 at a time, and order them newest to oldest.
     */
    public List<Post> getPostByUserId(Integer userId, Integer pageNumber) {
        List<Post> database = this.postDao.getPostByUserId(userId);
        List<Post> filteredPosts = database.stream()
                .filter(x -> x.getPostParentId() == null)
                .collect(Collectors.toList());

        Collections.sort(filteredPosts, Comparator.comparingInt(Post::getPostId).reversed());
        Double checkPages = Double.valueOf(filteredPosts.size()) / 20;
        int num = (int) (Math.ceil(checkPages));
        if (pageNumber <= num) {
            Integer pageEnd = pageNumber *20 -1;
            Integer offset = pageNumber * 20 - 20;
            List<Post> pagePost = new ArrayList<>();
            for (int j = offset; j < pageEnd; j++) {
                if (filteredPosts.size() == j) {
                    break;
                } else {
                    pagePost.add(filteredPosts.get(j));
                }
            }
            return pagePost;
        }else{
            return null;
        }
    }

    /**
     * Method that is called when a user updates a post or comment.
     * @param post  post object that is passed to the method from the post controller.
     * @return      returns the post object that was updated or null if it was not found in database.
     */
    public Post updatePost(Post post) {
        Post dataBasePost = getPostById(post.getPostId());
        if(dataBasePost != null){
            post.setPostId(dataBasePost.getPostId());
            post.setUserId(dataBasePost.getUserId());
            post.setPostParentId(dataBasePost.getPostParentId());
            this.postDao.save(post);
            return post;
        }
        return null;
    }
    /**
     * Method that is called when a user deletes a post or comment.
     * It will check to make sure its in the database first before deleting it.
     * It will also delete all child records affiliated with that post.
     * @param postId  postId that is passed to the method from the post controller.
     * @return      returns the post object that was deleted or null if there was an error deleting it..
     */
    public Post deletePost(Integer postId) {
        //checks to make sure post is in database
        Post checkDelete = this.postDao.findById(postId).orElse(null);
        //makes sure its not null
        if (checkDelete != null) {
            this.postDao.deleteByParentId(postId);
            this.postDao.deleteById(postId);
            return checkDelete;
        }
        return null;
    }

}
