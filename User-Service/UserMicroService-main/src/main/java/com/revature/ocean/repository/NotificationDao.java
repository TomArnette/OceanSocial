package com.revature.ocean.repository;

import com.revature.ocean.models.Notification;
import com.revature.ocean.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Zimi Li
 * Dao layer to communicate with the database and perform CRUD operations
 */
@Repository("NotificationDao")
@Transactional
public interface NotificationDao extends JpaRepository<Notification, Integer> {
    /**
     * Queries the database for the 10 most recent notifications ordered in descending order
     * given the specific user object.
     *
     * @param user  user object whose notifications are being returned
     * @return      returns a list of notifications
     */
    List<Notification> findTop10ByUserBelongToOrderByTimestampDesc(User user);

    /**
     * Queries the database for all notifications associated with a specific user object.
     *
     * @param user  user object whose notifications are being returned
     * @return      returns a list of notifications
     */
    List<Notification> findByUserBelongToOrderByTimestampDesc(User user);
}
