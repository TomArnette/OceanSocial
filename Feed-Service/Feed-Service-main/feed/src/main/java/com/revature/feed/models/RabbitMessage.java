package com.revature.feed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 *
 * This RabbitMessage class was used to create the RabbitMessage object that is going
 * to used in the communication between the Feed service and the User service. The RabbitMessage
 * object will contain the following data:
 *  -userIdFrom
 *  -postId
 *  -userIdTo
 *
 * Lombok is used to reduce repetitive code. The lombok annotations used in this class are the following:
 *  -@AllArgsConstructor
 *  -@NoArgsConstructor
 *  -@Data
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RabbitMessage {
    private int userIdFrom;
    private int postId;
    private int userIdTo;
}
