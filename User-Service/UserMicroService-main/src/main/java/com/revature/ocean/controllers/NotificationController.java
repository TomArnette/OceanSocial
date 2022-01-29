package com.revature.ocean.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.ocean.models.Response;
import com.revature.ocean.models.Notification;
import com.revature.ocean.models.User;
import com.revature.ocean.services.UserService;
import com.revature.ocean.utility.JwtUtility;
import com.revature.ocean.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Zimi Li
 * NotificationController used to manage the endpoints associated with Notification methods.
 */
@RestController("NotificationController")
public class NotificationController {
    private NotificationService notificationService;
    private UserService userService;
    private JwtUtility jwtUtility;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService, JwtUtility jwtUtility) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    /**
     * Creates the API endpoint to get all notifications for the logged in user.
     *
     * @param
     * @return      returns a response indicating success (true/false), the message, and data returned
     */
    @GetMapping("notification/{userId}")
    public Response getAllNotification(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {

        Response response;
        DecodedJWT decoded = jwtUtility.verify(headers.get("authorization"));

        if (decoded == null) {
            response = new Response(false, "Invalid Token, try again.", null);
        } else {
            if (decoded.getClaims().get("userId").asInt() == userId) {
                List<Notification> notifications = notificationService.getTop10NotificationByUserID(userId);
                if (notifications == null) {
                    response = new Response(false, "User ID not found", null);
                }
                else {
                    response = new Response(true, "retrieved notification", notifications);
                }
            } else {
                response = new Response(false, "Invalid Token, try again.", null);
            }
        }
        return response;
    }
}
