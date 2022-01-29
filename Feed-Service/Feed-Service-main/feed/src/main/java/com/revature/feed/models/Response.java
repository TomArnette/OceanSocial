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
 * The Response model is used to create the response object. The response object is used to send a response
 * back to the frontend when an endpoint is hit. The response model makes sure that the data is standarized
 * before sending the data to the frontend. The response object will contain the following data:
 *  -success
 *  -message
 *  -data
 *
 * Lombok is used to reduce repetitive code. The lombok annotations used in this class are the following:
 *  -@Data
 *  -@NoArgsConstructor
 *  -@AllArgsConstructor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    Boolean success;
    String message;
    Object data;

}
