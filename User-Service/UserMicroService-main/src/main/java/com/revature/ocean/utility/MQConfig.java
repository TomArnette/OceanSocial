package com.revature.ocean.utility;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String POST ="post_queue";
    public static final String LIKE ="like_queue";
    public static final String USER = "user_queue";
    public static final String FOLLOWINGS = "follow_queue";
    public static final String ROUTING_KEY = "routing_key";
    public static final String EXCHANGE = "exchange";

    @Bean
    public Queue post() {return new Queue(POST);}

    @Bean
    public Queue like() {return new Queue(LIKE);}

    @Bean
    public Queue user() {return new Queue(USER);}

    @Bean
    public Queue following(){return new Queue(FOLLOWINGS);}

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
    public Binding followingBinding(){
        return BindingBuilder
                .bind(following())
                .to(exchange())
                .with(FOLLOWINGS);
    }

    @Bean
    public MessageConverter messageConverter(){return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
