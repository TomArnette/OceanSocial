package com.revature.ocean.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMessage {
    private int userIdFrom;
    private int postId;
    private int userIdTo;
}
