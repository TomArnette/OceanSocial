package com.revature.ocean.services;

import com.revature.ocean.models.Notification;
import com.revature.ocean.models.User;
import com.revature.ocean.models.UserResponse;
import com.revature.ocean.repository.NotificationDao;
import com.revature.ocean.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zimi Li
 *
 * The notification service sends data associated with a notification that a user would receive when using
 * the Ocean social networking app. Users will receive a notification when another user follows their account.
 */
@Service("NotificationService")
public class NotificationService {
    private NotificationDao notificationDao;
    private UserDao userDao;

    @Autowired
    public NotificationService(NotificationDao notificationDao, UserDao userDao) {
        this.notificationDao = notificationDao;
        this.userDao = userDao;
    }

    public Notification createNotification(Notification notification){
        return this.notificationDao.save(notification);
    }

    /**
     * Used to format the notification.
     *
     * @param notification  notification object passed to the method for formatting
     */
    protected static void format(Notification notification) {
        notification.setUserResponse(UserService.format(notification.getUserBelongTo()));
    }

    /**
     * Retrieves a list of the 25 most recent notifications for a userId
     *
     * @param userID    userId of the logged in user who's notifications are being retrieved
     * @return          returns a list of notifications
     */
    public List<Notification> getTop10NotificationByUserID(Integer userID) {
        User user = userDao.findById(userID).orElse(null);
        if (user == null) return null;
        List<Notification> notifications = notificationDao.findTop10ByUserBelongToOrderByTimestampDesc(user);
        notifications.forEach(NotificationService::format);
        user.setLastNotification(System.currentTimeMillis());
        userDao.save(user);
        return notifications;
    }

    /**
     * Retrieves a list of all notifications for a specific userId
     *
     * @param userID    userId of the logged in user who's notifications are being retrieved
     * @return          returns a list of notifications
     */
    public List<Notification> getNotificationByUserID(Integer userID) {
        User user = userDao.findById(userID).orElse(null);
        if (user == null) return null;
        List<Notification> notifications = notificationDao.findByUserBelongToOrderByTimestampDesc(user);
        notifications.forEach(NotificationService::format);
        user.setLastNotification(System.currentTimeMillis());
        userDao.save(user);
        return notifications;
    }
}
