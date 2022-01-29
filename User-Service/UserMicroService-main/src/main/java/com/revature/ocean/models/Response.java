package com.revature.ocean.models;

import lombok.*;

/**
 * Response model used to create Response objects.
 * Lombok is used to reduce boilerplate code.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    Boolean success;
    String message;
    Object data;
}
