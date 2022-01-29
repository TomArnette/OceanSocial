package com.revature.feed.services;

import com.revature.feed.config.MQConfig;
import com.revature.feed.models.Like;
import com.revature.feed.models.Post;
import com.revature.feed.models.RabbitMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * The rabbit service handles the comunication between the Feed service and the User service. Depending on the action of
 * the user the rabbit service can either send a notification to the User service when a like has been created or
 * when a post has been commented on. The rabbit service also allows us to request from the user service the list of followers
 * of the current user that's login.
 *
 */
@Service("rabbitService")
public class RabbitService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private PostService postService;

    /**
     * Method that sends to the user service a message containing the following information from the like object:
     * the user id from the like object, the post id from the post that the like object contains and the user id that
     * liked the post that the like object has.
     *
     * @param like  like object is passed to the method from the controller.
     * @return      returns the String "Like notification success!" after the message is sent.
     */
    public String likeNotification(Like like){
        template.convertAndSend(
                MQConfig.EXCHANGE,
                MQConfig.LIKE,
                //Send RabbitMessage object to user-server with postId,
                new RabbitMessage(like.getUserId(), like.getPost().getPostId(), like.getPost().getUserId())

        );
        return "Like Notification success!";
    }

    /**
     * Method that sends a notification when a post was commented on to the user service containing the
     * following information from the post object: the user id from the post parent id that the post contains,
     * the post id and the user id that the post contains.
     *
     * @param post      post object that is passed to the method from the controller.
     * @return          returns the String "Post Notification success!" if the post parent id us not null
     * @return          returns the String "No notification needed its a post" if the post parent id equals null
     */
    public String postNotification(Post post){
        if(post.getPostParentId() != null ) {
            //is a post or comment
            template.convertAndSend(
                    MQConfig.EXCHANGE,
                    MQConfig.POST,
                    new RabbitMessage(post.getUserId(), post.getPostId(), postService.getPostById(post.getPostParentId()).getUserId())
            );
            return "Post Notification success!";
        }
        return "No notification needed its a post";
    }

    /**
     * Method that sends a request to the User service to get the list of followers from the user whose id that's passed
     * from the controller to this method and then send the list of posts from the list of followers.
     *
     * @param userId    user id of the user that we want to retrieve the list of followers from
     */
    public List<Integer> requestListOfFollowers(Integer userId){
        return template.convertSendAndReceiveAsType(
                MQConfig.EXCHANGE,
                MQConfig.USER,
                 userId,
                new ParameterizedTypeReference<List<Integer>>(){

                }
        );
    }



}

