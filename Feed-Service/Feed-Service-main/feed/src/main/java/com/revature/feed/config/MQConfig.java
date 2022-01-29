package com.revature.feed.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 */
@Configuration
public class MQConfig {

    public static final String POST ="post_queue";
    public static final String LIKE ="like_queue";
    public static final String USER = "user_queue";
    public static final String FOLLOWINGS = "follow_queue";
    public static final String EXCHANGE = "exchange";

    @Bean
    public Queue post() {return new Queue(POST);}

    @Bean
    public Queue like() {return new Queue(LIKE);}

    @Bean
    public Queue user() {return new Queue(USER);}

    @Bean
    public Queue followings() {return new Queue(FOLLOWINGS);}

    @Bean
    public TopicExchange exchange(){return new TopicExchange(EXCHANGE);}

    @Bean
    public Binding postBinding(){
        return BindingBuilder
                .bind(post())
                .to(exchange())
                .with(POST);
    }

    @Bean
    public Binding likeBinding(){
        return BindingBuilder
                .bind(like())
                .to(exchange())
                .with(LIKE);
    }

    @Bean
    public Binding userBinding(){
        return BindingBuilder
                .bind(user())
                .to(exchange())
                .with(USER);
    }

    @Bean
    public Binding followingsBinding(){
        return BindingBuilder
                .bind(followings())
                .to(exchange())
                .with(FOLLOWINGS);
    }

    /**
     * This is used to convert message to be able to send in RabbitMQ
     */
    @Bean
    public MessageConverter messageConverter(){return new Jackson2JsonMessageConverter();
    }
    /**
     * Template used for sending the RabbitMQ messages
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
