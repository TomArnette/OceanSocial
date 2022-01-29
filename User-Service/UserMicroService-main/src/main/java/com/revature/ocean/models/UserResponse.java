package com.revature.ocean.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zimi Li
 *
 * UserResponse model used to create UserResponse objects.
 * Lombok is used to reduce boilerplate code.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Integer id;
     private String name;
     private String profileImage;
     private Long lastNotification;
}
